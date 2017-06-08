package cn.xiaochebao.app.model;

import java.util.List;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.config.Config;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.model.extend.PageModelExtend;

/**
 * Created by Alan on 2017/04/12 0012.
 */
public class DealsModel extends BaseModel {

    private List<DealInfoModel> item = null;
    private PageModelExtend page = null;

    private String program_title = null;

    public DealsModel() {
        super(Config.API_CTL.DEALS, Config.API_ACT.DEALS);
        setCacheTime(Constant.CacheTime.SHORT_6);
    }

    public List<DealInfoModel> getItem() {
        return item;
    }

    public PageModelExtend getPageObj() {
        return page;
    }

    public void addItem(DealInfoModel infoModel){
        this.item.add(infoModel);
    }

    public void addItemAll(List<DealInfoModel> list){
        item.addAll(list);
    }

    public void setPage(PageModelExtend page) {
        this.page = page;
    }

    public String getProgramTitle() {
        return program_title;
    }
}
