package cn.xiaochebao.app.model.extend;

import java.util.List;

import cn.xiaochebao.app.model.AdInfoModel;
import cn.xiaochebao.app.model.DealInfoModel;

/**
 * 首页数据模型扩展
 * Created by Alan on 2017/04/12 0012.
 */
public class IndexListModelExtend {

    private List<AdInfoModel> adv_list = null;
    private List<DealInfoModel> deal_list = null;


    public List<AdInfoModel> getAdvList() {
        return adv_list;
    }

    public List<DealInfoModel> getDealList() {
        return deal_list;
    }

    public void setAdvList(List<AdInfoModel> a) {
        this.adv_list = a;
    }

    public void setDealList(List<DealInfoModel> d) {
        this.deal_list = d;
    }
}
