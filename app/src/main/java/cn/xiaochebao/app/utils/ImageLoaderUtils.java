package cn.xiaochebao.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * Created by Alan on 2017/04/24 0024.
 */
public class ImageLoaderUtils {

    private Context mContext;
    private ImageLoader mImageLoader;

    public ImageLoaderUtils(Context ctx){
        mContext = ctx;

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    public static ImageLoaderUtils getInstance(Context ctx){
        return new ImageLoaderUtils(ctx);
    }

    public void showImage(String uri, ImageView imageView){
        mImageLoader.displayImage(uri,imageView);
    }

}
