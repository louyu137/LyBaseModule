package cn.louyu.lybasemodule;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import cn.louyu.lylibrary.core.annotation.InjectView;
import cn.louyu.lylibrary.core.annotation.Injector;
import cn.louyu.lylibrary.core.utils.okhttp.BitmapCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.OkClientOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;

public class MainActivity extends BaseActionbarActivity{

    @InjectView(R.id.image)
    private ImageView image;
    @InjectView(R.id.image2)
    private ImageView image2;
    @InjectView(R.id.image3)
    private ImageView image3;
    @InjectView(R.id.image4)
    private ImageView image4;
    @InjectView(R.id.image5)
    private ImageView image5;
    WebSocketClient webSocketClient;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        Injector.inject(this);
    }

    @Override
    public void initData() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://192.168.0.103:2018"), new Draft_17()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           showToast("连接成功");
                       }
                   });
                }

                @Override
                public void onMessage(final String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showNotifictionIcon(MainActivity.this,message);
                        }
                    });

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("链接关闭");
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("发生错误");
                        }
                    });
                }
            };

        }catch (Exception e){}
    }

    @Override
    public void initListener() {

    }

    public void loadImage(View view){
        OkClientOnUI onUI=new OkClientOnUI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531558393007&di=c7435664fb3ee6147d64f12bb6839d65&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3405886258%2C2620565011%26fm%3D214%26gp%3D0.jpg", new BitmapCallbackOnUI() {
            @Override
            public void onBeforeOnUI() {

            }

            @Override
            public void onSuccessOnUI(ResultMsg<Bitmap> msg) {
                image.setImageBitmap(msg.Data);
            }

            @Override
            public void onFailureOnUI(ResultMsg<Bitmap> msg) {

            }
        });
        onUI.setMethod(OkClientOnUI.GET).connect();
        OkClientOnUI onUI2=new OkClientOnUI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531558755538&di=2ea711285dcb2ea1c912a426af1546e5&imgtype=0&src=http%3A%2F%2Fp3.gexing.com%2FG1%2FM00%2F86%2F3A%2FrBACE1J0ZbiQEaCmAADyHzsi-NI664.jpg", new BitmapCallbackOnUI() {
            @Override
            public void onBeforeOnUI() {

            }

            @Override
            public void onSuccessOnUI(ResultMsg<Bitmap> msg) {
                image2.setImageBitmap(msg.Data);
            }

            @Override
            public void onFailureOnUI(ResultMsg<Bitmap> msg) {

            }
        });
        onUI2.setMethod(OkClientOnUI.GET).connect();
        OkClientOnUI onUI3=new OkClientOnUI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531558755538&di=46019c5024d7d96836fc74624644d548&imgtype=0&src=http%3A%2F%2Fold.bz55.com%2Fuploads%2Fallimg%2F140517%2F137-14051GG950.jpg", new BitmapCallbackOnUI() {
            @Override
            public void onBeforeOnUI() {

            }

            @Override
            public void onSuccessOnUI(ResultMsg<Bitmap> msg) {
                image3.setImageBitmap(msg.Data);
            }

            @Override
            public void onFailureOnUI(ResultMsg<Bitmap> msg) {

            }
        });
        onUI3.setMethod(OkClientOnUI.GET).connect();
        OkClientOnUI onUI4=new OkClientOnUI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531558755536&di=5811535b9b7a2952fb579c34033a53fb&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Ff9198618367adab409a7e8988ed4b31c8601e4f2.jpg", new BitmapCallbackOnUI() {
            @Override
            public void onBeforeOnUI() {

            }

            @Override
            public void onSuccessOnUI(ResultMsg<Bitmap> msg) {
                image4.setImageBitmap(msg.Data);
            }

            @Override
            public void onFailureOnUI(ResultMsg<Bitmap> msg) {

            }
        });
        onUI4.setMethod(OkClientOnUI.GET).connect();
        OkClientOnUI onUI5=new OkClientOnUI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531558755533&di=d00e41648389d38f2b97ce04a63c3857&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201405%2F04%2F20140504190916_aRGaU.jpeg", new BitmapCallbackOnUI() {
            @Override
            public void onBeforeOnUI() {

            }

            @Override
            public void onSuccessOnUI(ResultMsg<Bitmap> msg) {
                image5.setImageBitmap(msg.Data);
            }

            @Override
            public void onFailureOnUI(ResultMsg<Bitmap> msg) {

            }
        });
        onUI5.setMethod(OkClientOnUI.GET).connect();
    }

    public static void showNotifictionIcon(Context context,String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        Intent intent = new Intent(context, XXXActivity.class);//将要跳转的界面
        Intent intent = new Intent();//只显示通知，无页面跳转
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker("接收到一条消息");
        builder.setContentTitle("服务器推送");
        builder.setContentText(msg);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
