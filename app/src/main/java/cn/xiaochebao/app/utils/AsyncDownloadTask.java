package cn.xiaochebao.app.utils;


import android.content.Context;
import android.os.Environment;
import cn.xiaochebao.app.base.BaseAsyncTask;
import cn.xiaochebao.app.config.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 异步文件下载类
 *
 * The example:
 * AsyncDownloadTask task = AsyncDownloadTask.getInstance(mContext,"文件下载","下载中...");
 * task.execute("http://www.lanxinbase.com/app/compass.apk");
 *
 * Created by Alan on 2017/04/05 0005.
 */
public class AsyncDownloadTask extends BaseAsyncTask {

    private boolean isOpenFile = false;
    private String fileName;
    private String path;

    /**
     * 初始化异步对象
     * @param title ProgressDialog的标题
     * @param msg ProgressDialog的显示信息
     */
    public AsyncDownloadTask(Context ctx,String title, String msg){
        super.__init(ctx,title,msg);
        setPath(Environment.getExternalStorageDirectory() + "/" + Constant.DIR_NAME+"/"+Constant.DOWN_LOAD_DIR_NAME);
    }


    public static AsyncDownloadTask getInstance(Context ctx, String title, String msg){
        return new AsyncDownloadTask(ctx,title,msg);
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public void setIsOpen(boolean open){
        this.isOpenFile = open;
    }

    public void setPath(String path) {
        //生成目录
        this.path = UtilsEx.getPath(path);;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * 开始文件下载
     * @param url
     * @return
     */
    @Override
    public Object doWorking(String url) {

        if(url == null || "".equals(url)){
            return null;
        }
        fileName = url.substring(url.lastIndexOf("/"));//example:DCS_0001.JPG

        /**
         * example:DCS_0001.JPG
         * if the file name(DCS_0001.JPG) is valid,then it return the DCS_0001 + ( file Nums ) + .JPG
         *
         * example:DSC_0001(1).JPG
         *
         */
        fileName = UtilsEx.checkFileExists(getPath(),fileName);

        File file = null;
        try {
            URL uri = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                int length = conn.getContentLength();
                int target = 0;
                int tmp = 0;

                InputStream in = conn.getInputStream();
                FileOutputStream out = new FileOutputStream(new File(getPath() + getFileName()));

                byte[] b = new byte[1024*1024];

                while ((tmp = in.read(b)) > 0){
                    target += tmp;
                    publishProgress((target * 100) / length);
                    out.write(b, 0, tmp);
                }
                in.close();
                out.flush();
                out.close();
                file = new File(getPath()+getFileName());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 用户取消,那么则删除未下载成功的文件
     */
    @Override
    public void doCancel() {
        File file = new File(getPath()+getFileName());
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 下载完成判断是否需要打开文件
     * @param result
     */
    @Override
    public void doSuccess(Object result) {
        if(isOpenFile == true){
            UtilsEx.openFile((File) result);
        }
    }


}
