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
import cn.louyu.lylibrary.core.utils.tools.SequentialMap;

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

    }

    @Override
    public void initListener() {

    }

    public void loadImage(View view){

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
