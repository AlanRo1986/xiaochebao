package cn.xiaochebao.app.model;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.config.Config;

/**
 * Created by Alan on 2017/04/12 0012.
 */
public class UserInfoModel extends BaseModel {
    public UserInfoModel() {
        super(Config.API_CTL.UC_CENTER, Config.API_ACT.UC_CENTER);
        setCacheTime(60);
    }


}
