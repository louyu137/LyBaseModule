package cn.louyu.lymvpframework.scancode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.view.View;

import android.util.Log;
import android.view.TextureView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import cn.louyu.lymvpframework.R;
import cn.louyu.lymvpframework.base.BaseActivity;
import cn.louyu.lymvpframework.interfaces.IScanListener;
import cn.louyu.lymvpframework.interfaces.IView;
import cn.louyu.lymvpframework.utils.manager.ActivityPageManager;
import cn.simonlee.xcodescanner.core.CameraScanner;
import cn.simonlee.xcodescanner.core.GraphicDecoder;
import cn.simonlee.xcodescanner.core.NewCameraScanner;
import cn.simonlee.xcodescanner.core.OldCameraScanner;
import cn.simonlee.xcodescanner.core.ZBarDecoder;
import cn.simonlee.xcodescanner.view.AdjustTextureView;

/**
 * Created by sdj003 on 2018/10/20.
 */

public class CaptureActivity extends BaseActivity
        implements IView,CameraScanner.CameraListener,
        TextureView.SurfaceTextureListener,
        GraphicDecoder.DecodeListener,
        View.OnClickListener{

    private AdjustTextureView mTextureView;
    private View mScannerFrameView;

    private CameraScanner mCameraScanner;
    protected GraphicDecoder mGraphicDecoder;

    protected String TAG = "XCodeScanner";
    private Button mButton_Flash;
    private int[] mCodeType;

    IScanListener scanListener;

    public final void setScanListener(IScanListener scanListener) {
        this.scanListener = scanListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    public void initOperation() {
        mTextureView = findViewById(R.id.textureview);
        mTextureView.setSurfaceTextureListener(this);

        mScannerFrameView = findViewById(R.id.scannerframe);

        mButton_Flash = findViewById(R.id.btnFlash);
        mButton_Flash.setOnClickListener(this);

        Intent intent = getIntent();
        mCodeType = intent.getIntArrayExtra("codeType");

        /*
        * 注意，SDK21的设备是可以使用NewCameraScanner的，但是可能存在对新API支持不够的情况，比如红米Note3（双网通Android5.0.2）
        * 开发者可自行配置使用规则，比如针对某设备型号过滤，或者针对某SDK版本过滤
        * */
        if (intent.getBooleanExtra("newAPI", false) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraScanner = new NewCameraScanner(this);
        } else {
            mCameraScanner = new OldCameraScanner(this);
        }
    }

    @Override
    protected void onRestart() {
        if (mTextureView.isAvailable()) {
            //部分机型转到后台不会走onSurfaceTextureDestroyed()，因此isAvailable()一直为true，转到前台后不会再调用onSurfaceTextureAvailable()
            //因此需要手动开启相机
            mCameraScanner.setPreviewTexture(mTextureView.getSurfaceTexture());
            mCameraScanner.setPreviewSize(mTextureView.getWidth(), mTextureView.getHeight());
            mCameraScanner.openCamera(this.getApplicationContext());
        }
        super.onRestart();
    }

    @Override
    protected void onPause() {
        if(mCameraScanner!=null)
            mCameraScanner.closeCamera();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mCameraScanner.setGraphicDecoder(null);
        if (mGraphicDecoder != null) {
            mGraphicDecoder.setDecodeListener(null);
            mGraphicDecoder.detach();
        }
        mCameraScanner.detach();
        closeMediaPlay();
        super.onDestroy();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureAvailable() width = " + width + " , height = " + height);
        mCameraScanner.setPreviewTexture(surface);
        mCameraScanner.setPreviewSize(width, height);
        mCameraScanner.openCamera(this);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureSizeChanged() width = " + width + " , height = " + height);
        // TODO 当View大小发生变化时，要进行调整。
//        mTextureView.setImageFrameMatrix();
//        mCameraScanner.setPreviewSize(width, height);
//        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureDestroyed()");
        return true;
    }

    @Override// 每有一帧画面，都会回调一次此方法
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void openCameraSuccess(int frameWidth, int frameHeight, int frameDegree) {
        Log.e(TAG, getClass().getName() + ".openCameraSuccess() frameWidth = " + frameWidth + " , frameHeight = " + frameHeight + " , frameDegree = " + frameDegree);
        mTextureView.setImageFrameMatrix(frameWidth, frameHeight, frameDegree);
        if (mGraphicDecoder == null) {
            mGraphicDecoder = new ZBarDecoder(this, mCodeType);//使用带参构造方法可指定条码识别的格式
        }
        //该区域坐标为相对于父容器的左上角顶点。
        //TODO 应考虑TextureView与ScannerFrameView的Margin与padding的情况
        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
        mCameraScanner.setGraphicDecoder(mGraphicDecoder);
    }

    @Override
    public void openCameraError() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("初始化摄像头出错");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityPageManager.getInstance().killActivity(context);
            }

        });
        builder.show();
    }

    @Override
    public void noCameraPermission() {
        Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cameraDisconnected() {
        Toast.makeText(this, "断开了连接", Toast.LENGTH_SHORT).show();
    }

    int mBrightnessCount = 0;

    @Override
    public void cameraBrightnessChanged(int brightness) {
        if (brightness <= 50) {
            if (mBrightnessCount < 0) {
                mBrightnessCount = 1;
            } else {
                mBrightnessCount++;
            }
        } else {
            if (mBrightnessCount > 0) {
                mBrightnessCount = -1;
            } else {
                mBrightnessCount--;
            }
        }
        if (mBrightnessCount > 4) {//连续5帧亮度低于50，显示闪光灯开关
            mButton_Flash.setVisibility(View.VISIBLE);
        } else if(mBrightnessCount < -4 && !mCameraScanner.isFlashOpened()){//连续5帧亮度不低于50，且闪光灯未开启，隐藏闪光灯开关
            mButton_Flash.setVisibility(View.GONE);
        }
    }

    int mCount = 0;
    String mResult = null;

    @Override
    public void decodeComplete(String result, int type, int quality, int requestCode) {
        if (result == null) return;
        if (result.equals(mResult)) {
            if (++mCount > 3) {//连续四次相同则显示结果（主要过滤脏数据，也可以根据条码类型自定义规则）
//                if (quality < 10) {
//                    Toast.makeText(this, "[类型" + type + "/精度00" + quality + "]" + result, Toast.LENGTH_SHORT).show();
//                } else if (quality < 100) {
//                    Toast.makeText(this, "[类型" + type + "/精度0" + quality + "]" + result, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "[类型" + type + "/精度" + quality + "]" + result, Toast.LENGTH_SHORT).show();
//                }
                if(scanListener != null){
                    //TODO===扫码成功回调===
                    playBeepSoundAndVibrate();
                    scanListener.onScanResult(mResult);
                }
            }
        } else {
            mCount = 1;
            mResult = result;
        }
        Log.d(TAG, getClass().getName() + ".decodeComplete() -> " + mResult);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnFlash) {
            if (v.isSelected()) {
                ((Button) v).setText("开启闪光灯");
                v.setSelected(false);
                mCameraScanner.closeFlash();
            } else {
                ((Button) v).setText("关闭闪光灯");
                v.setSelected(true);
                mCameraScanner.openFlash();
            }
        }
    }


    //TODO===========声音和震动===============
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.50f;
    private boolean vibrate;
    private static final long VIBRATE_DURATION = 200L;
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    public void closeMediaPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    //TODO===========声音和震动 END===============
}
