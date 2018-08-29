package cn.louyu.lylibrary.core.utils.picasso;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cn.louyu.lylibrary.R;

/**
 * Created by sdj003 on 2018/8/29.
 */

public class PicassoHelper {
    private PicassoHelper() {
        throw new AssertionError();
    }
    private static Picasso singleton =null;

    /**
     * 获取Picasso的单例（注意：这里的picasso一定要做成单例模式，不然LRU内存缓存会失效，那时候你滚动一个listView，滚下去再滚上来，原本下载好的图片会重新下一遍的 ）
     * */
    public static Picasso getPicassoInstance(Context context){
        if(singleton == null){
            //锁定代码块
            synchronized (PicassoHelper.class) {
                if (singleton == null) {
                    singleton = new Picasso.Builder(context).downloader(new OkHttp3Downloader(context)).build();
                }
            }
        }
        return singleton;
    }

    public static void loadImageFromUrl(Context context, Uri uri, ImageView imageView){
        //获取单例的Picasso
        getPicassoInstance(context)
                //load(String imageUrl)：被加载图像的Url地址。
                //大多情况下，一个字符串代表一个网络图片的URL。
                .load(uri)
                //加载过程中的图片显示
                .placeholder(R.drawable.picasso_placeholder)
                //加载失败中的图片显示
                //如果重试3次（下载源代码可以根据需要修改）还是无法成功加载图片，则用错误占位符图片显示。
                .error(R.drawable.picasso_error)
                //加载到ImageView
                .into(imageView);
    }
}
