package cn.xiaochebao.app.libs;


import android.os.Environment;
import android.util.Base64;

import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.utils.UtilsEx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * 6.0Android需要注意一下,内存卡写入失败
 * Created by Alan on 2017/03/31 0031.
 */
public class FileCache {
    protected static String prefix = ".@~";
    protected static long validTime = 60000;
    protected static String filePath = null;
    protected static boolean CACHE_ON = true;

    public FileCache(){
        filePath = Environment.getExternalStorageDirectory() + "/" + Constant.DIR_NAME;
        init();
    }

    private void init() {
        filePath = UtilsEx.getPath(filePath+"/"+Constant.CACHE_LOAD_DIR_NAME+"/");
        CACHE_ON = true;
    }

    private String getFileName(String name){
        return filePath + prefix + UtilsEx.md5(name);
    }

    public String get(String key){
        if (CACHE_ON == false){
            return null;
        }
        String fileNmae = getFileName(key);
        File file = new File(fileNmae);
        String str = null;

        try {

            if (!file.exists()){
                throw new IOException("Not Cache");
            }

            RandomAccessFile raf = new RandomAccessFile(file,"r");
            raf.seek(0);
            byte[] buffer = new byte[(int) file.length()];
            if(raf.read(buffer) > 0){
                str = new String(Base64.decode(buffer,Base64.DEFAULT));

                if(str.length() > 13){

                    long createTime = Long.parseLong(str.substring(0,13));

                    if(validTime > 0 && (createTime + validTime) > new Date().getTime()){
                        str = str.substring(13);
                        Logger.info("from cache:"+fileNmae);
                    }else{
                        str = null;
                        file.delete();
                    }
                    //Logger.info("get:"+fileNmae+" data:"+str+" validTime:"+(createTime + validTime)+":"+new Date().getTime());
                }
            }

            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public boolean set(String key,String val){
        if (CACHE_ON == false || val == null || val.equals("") || key.equals("")){
            return false;
        }

        String fileNmae = getFileName(key);
        File file = new File(fileNmae);

        try {

            if(!file.exists()){
                file.createNewFile();
            }
            byte[] bytes = Base64.encode((new Date().getTime() + val).getBytes(),Base64.DEFAULT);
            RandomAccessFile raf = new RandomAccessFile(file,"rw");
            raf.write(bytes);
            raf.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean rm(String key){
        if (CACHE_ON == false){
            return true;
        }
        String fileNmae = getFileName(key);
        Logger.info("rm:"+fileNmae);
        File file = new File(fileNmae);

        if (!file.exists()){
            return true;
        }
        return file.delete();

    }

    public void clear(){
        File file = new File(filePath);
        if (file.list().length > 0){
            for(String _filename : file.list()){
                Logger.info("clear:"+filePath+_filename);
                new File(filePath+_filename).delete();
            }
        }
    }

    public static FileCache getInstance(){
        return new FileCache();
    }

    public void setCacheOn(boolean cacheOn) {
        CACHE_ON = cacheOn;
    }

    public void setFilePath(String filePath) {
        FileCache.filePath = filePath;
    }

    public void setPrefix(String prefix) {
        FileCache.prefix = prefix;
    }

    public void setValidTime(long validTime) {
        if(validTime > 1000){
            FileCache.validTime = validTime;
        }
    }

    public long getValidTime() {
        return validTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getPrefix() {
        return prefix;
    }

}
