package cn.louyu.lybasemodule;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import cn.louyu.lylibrary.core.annotation.InjectView;
import cn.louyu.lylibrary.core.annotation.Injector;


public class MainActivity extends BaseActionbarActivity{

    @InjectView(R.id.switch_pin29)
    private Switch switch_pin29;

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

    public void onConnect(View view){

    }

    @Override
    public void initListener() {
        switch_pin29.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }


//    public void loading(View view){
//        //Url加密验证规则
//        //1.sign=MD5(userId+timestamp+randomString+salt1)
//        String userNum="sdj001";
//        String salt = "sdj705888";
//        String random = "123456";
//        long timeStamp=18560051;
//        String passStr=MD5Util.MD5Encode(userNum+timeStamp+random+salt,"UTF-8",false);
//
//        OkClientOnUI okClientOnUI=new OkClientOnUI("https://c.lwhale.cn/home/sdjSoftListAdd", new TxtCallbackOnUI() {
//           @Override
//           public void onBeforeOnUI() {
//               showLoadingDialog();
//           }
//
//           @Override
//           public void onSuccessOnUI(ResultMsg<String> msg) {
//                heidLoadingDialog();
//               requestText.setText(msg.Data);
//           }
//
//           @Override
//           public void onFailureOnUI(ResultMsg<String> msg) {
//               heidLoadingDialog();
//           }
//       });
//        SdjSoftListModel model=new SdjSoftListModel("ljs test","2018-8-15 16:56:44","Sdj Test","略...", "2018-8-15 17:17:40");
//        ResultMsg<SdjSoftListModel> msg=new ResultMsg<>();
//        msg.Success=true;
//        msg.Msg="闪电鲸测试";
//        msg.Data=model;
//        okClientOnUI.add("userId",userNum);
//        okClientOnUI.add("randomString",random);
//        okClientOnUI.add("timestamp",String.valueOf(timeStamp));
//        okClientOnUI.add("sign",passStr);
//        okClientOnUI.add("sModel",JSON.toJSONString(msg));
//        okClientOnUI.connect();
//        showDialog(passStr);
//    }
//
//    public static <T> Map<String,String> getRequestData(String salt,String userNum, ResultMsg<T> msg){
//        String random=RandomCharacterUtils.generate(new Random(), 10,
//                RandomCharacterUtils.Type.ONLYDIGIT|RandomCharacterUtils.Type.LOWERCASE|RandomCharacterUtils.Type.UPPERCASE );
//        long timeStamp= System.currentTimeMillis();
//        String sign=MD5Util.MD5Encode(userNum+timeStamp+random+salt,"UTF-8",false);
//        Map<String,String> param=new HashMap<String,String>();
//        param.put("userId",userNum);
//        param.put("randomString",random);
//        param.put("timestamp",String.valueOf(timeStamp));
//        param.put("sign",sign);
//        param.put("sModel",JSON.toJSONString(msg));
//        return param;
//    }

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
