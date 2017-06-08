package cn.xiaochebao.app.model;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.config.Config;

/**
 * Created by Alan on 2017/04/10 0010.
 */
public class VersionModel extends BaseModel {

    private int serverVersion;
    private String filename;
    private String android_upgrade;
    private int hasfile;
    private int forced_upgrade;
    private float filesize;
    private int has_upgrade;
    private String share_title;
    private String share_context;
    private String share_img;

    public VersionModel(){
        super(Config.API_CTL.VERSION, Config.API_ACT.VERSION);

        setCacheTime(3600*12);
    }

    /**
     * 取服务器版本
     * @return
     */
    public int getServerVersion() {
        return serverVersion;
    }

    /**
     * 取文件名
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 文件大小
     * @return
     */
    public float getFilesize() {
        return filesize;
    }

    /**
     * 是否可以更新
     * 1:可升级;0:不可升级
     * @return
     */
    public int getHasUpgrade() {
        return has_upgrade;
    }

    /**
     * 取分享内容
     * @return
     */
    public String getShareContext() {
        return share_context;
    }

    /**
     * 取分享图片
     * @return
     */
    public String getShareTmg() {
        return share_img;
    }

    /**
     * 取分享标题
     * @return
     */
    public String getShareTitle() {
        return share_title;
    }

    /**
     * 取升级日志
     * @return
     */
    public String getAndroidUpgrade() {
        return android_upgrade;
    }

    public int getForcedUpgrade() {
        return forced_upgrade;
    }
}
