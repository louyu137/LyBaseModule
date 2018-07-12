package cn.louyu.lybasemodule;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.louyu.lylibrary.core.base.BaseActivity;

/**
 * Created by sdj003 on 2018/6/25.
 */

public abstract class BaseActionbarActivity extends BaseActivity{

    protected TextView tv_actionbar_title;
    protected ImageView iv_actionbar_back,iv_actionbar_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        tv_actionbar_title=findViewById(R.id.tv_actionbar_title);
        iv_actionbar_back=findViewById(R.id.iv_actionbar_back);
        iv_actionbar_home=findViewById(R.id.iv_actionbar_home);
        iv_actionbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                onBackPressed();
            }
        });

        iv_actionbar_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回主页
                showToast("返回主页");
            }
        });
    }



}
