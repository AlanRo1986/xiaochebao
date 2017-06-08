package cn.xiaochebao.app.model;

import java.util.List;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.config.Config;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.model.extend.IndexListModelExtend;

/**
 * 首页模型类
 * Created by Alan on 2017/04/12 0012.
 */
public class InitModel extends BaseModel {

    private String kf_phone = null;
    private String kf_email = null;
    private int about_info = 0;
    private int version = 0;
    private int page_size = 0;
    private String program_title = null;
    private String site_domain = null;
    private String virtual_money_1 = null;
    private String virtual_money_2 = null;
    private String virtual_money_3 = null;

    private IndexListModelExtend index_list = null;
    private List<DealCateModel> deal_cate_list = null;

    public InitModel() {
        super(Config.API_CTL.INDEX, Config.API_ACT.INDEX);
        setCacheTime(Constant.CacheTime.SHORT_6);
    }

    public String getKfPhone() {
        return kf_phone;
    }

    public String getKfEmail() {
        return kf_email;
    }

    public int getAboutInfo() {
        return about_info;
    }

    public int getVersion() {
        return version;
    }

    public int getPageSize() {
        return page_size;
    }

    public String getProgramTitle() {
        return program_title;
    }

    public String getSiteDomain() {
        return site_domain;
    }

    public String getVirtualMoney1() {
        return virtual_money_1;
    }

    public String getVirtualMoney2() {
        return virtual_money_2;
    }

    public String getVirtualMoney3() {
        return virtual_money_3;
    }

    public IndexListModelExtend getIndexList() {
        return index_list;
    }

    public List<DealCateModel> getDealCateList() {
        return deal_cate_list;
    }

    public void setDealCateList(List<DealCateModel> d) {
        this.deal_cate_list = d;
    }

    public void setAboutInfo(int a) {
        this.about_info = a;
    }

    public void setIndexList(IndexListModelExtend i) {
        this.index_list = i;
    }

    public void setKfEmail(String e) {
        this.kf_email = e;
    }

    public void setKfPhone(String p) {
        this.kf_phone = p;
    }

    public void setPageSize(int p) {
        this.page_size = p;
    }

    public void setProgramTitle(String t) {
        this.program_title = t;
    }

    public void setSiteDomain(String s) {
        this.site_domain = s;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setVirtualMoney1(String m) {
        this.virtual_money_1 = m;
    }

    public void setVirtualMoney2(String m) {
        this.virtual_money_2 = m;
    }

    public void setVirtualMoney3(String m) {
        this.virtual_money_3 = m;
    }

}
