package cn.louyu.lybasemodule;


import android.view.View;
import android.widget.ImageView;

import cn.louyu.lylibrary.core.annotation.InjectView;
import cn.louyu.lylibrary.core.annotation.Injector;

public class MainActivity extends BaseActionbarActivity{

    @InjectView(R.id.image)
    private ImageView image;
    private int a;

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
        image.setImageResource(R.drawable.ic_error);
    }
}
