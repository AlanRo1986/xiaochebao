package cn.xiaochebao.app.model;

import cn.xiaochebao.app.base.BaseModel;
import cn.xiaochebao.app.config.Config;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.model.extend.DealTypeInfoModelExtend;
import cn.xiaochebao.app.model.extend.DealUserInfoModelExtend;

/**
 * Created by Alan on 2017/04/12 0012.
 */
public class DealInfoModel extends BaseModel {

    private int id = 0;
    private String name = null;
    private String sub_name = null;
    private int cate_id = 0;
    private int agency_id = 0;
    private int user_id = 0;
    private String description = null;
    private int is_effect = 0;
    private int is_delete = 0;
    private int sort = 0;
    private int type_id = 0;
    private double borrow_amount = 0;
    private double min_loan_money = 0;
    private String min_loan_money_format = null;
    private double max_loan_money = 0;
    private int repay_time = 0;
    private float rate = 0;
    private int day = 0;
    private int create_time = 0;
    private int update_time = 0;
    private int buy_count = 0;
    private double load_money = 0;
    private double repay_money = 0;
    private int start_time = 0;
    private int success_time = 0;
    private int repay_start_time = 0;
    private int last_repay_time = 0;
    private int next_repay_time = 0;
    private int bad_time = 0;
    private int deal_status = 0;
    private int loantype = 0;
    private int repay_time_type = 0;
    private int risk_rank = 0;
    private int pay_amount = 0;
    private int is_noob = 0;
    private String AuditStat = null;
    private int last_time = 0;
    private float progress_point = 0;
    private int remain_time = 0;
    private String remain_time_format = null;
    private String borrow_amount_format = null;

    private String rate_foramt = null;
    private String rate_foramt_w = null;

    private double month_repay_money = 0;
    private double month_manage_money = 0;
    private String month_repay_money_format = null;
    private String month_manage_money_format = null;

    private double true_month_repay_money = 0;
    private double all_manage_money = 0;

    private String need_money = null;

    private String share_url = null;
    private String app_url = null;
    private String url = null;



    private DealCateModel cate_info = null;
    private DealUserInfoModelExtend user = null;
    private DealTypeInfoModelExtend type_info = null;


    private String deal_status_format_string = null;
    private boolean isLoaning = false; // 是否是借款中

    public DealInfoModel() {
        super(Config.API_CTL.DEAL, Config.API_ACT.DEAL);
        setCacheTime(60);
    }

    public int getId() {
        return id;
    }

    public double getBorrowAmount() {
        return borrow_amount;
    }

    public double getLoadMoney() {
        return load_money;
    }

    public double getMaxLoanMoney() {
        return max_loan_money;
    }

    public double getMinLoanMoney() {
        return min_loan_money;
    }

    public double getRepayMoney() {
        return repay_money;
    }

    public int getAgencyId() {
        return agency_id;
    }

    public float getRate() {
        return rate;
    }

    public DealCateModel getCateInfo() {
        return cate_info;
    }

    public int getCateId() {
        return cate_id;
    }

    public int getBadTime() {
        return bad_time;
    }

    public int getBuyCount() {
        return buy_count;
    }

    public int getProgressPoint() {
        return (int) progress_point;
    }

    public void setProgressPoint(float progress_point) {
        this.progress_point = progress_point;
    }

    public int getCreateTime() {
        return create_time;
    }

    public int getDay() {
        return day;
    }

    public int getDealStatus() {
        return this.deal_status;
    }
    public String getDealStatusFormat() {
        String str = Constant.DealStatusString.REPAYMENTED;
        switch (getDealStatus()){
            case Constant.DealStatusInt.WAIT:
                str = Constant.DealStatusString.WAIT;
                break;
            case Constant.DealStatusInt.LOANING:
                str = Constant.DealStatusString.LOANING;
                break;
            case Constant.DealStatusInt.FULL:
                str = Constant.DealStatusString.FULL;
                break;
            case Constant.DealStatusInt.FAIL:
                str = Constant.DealStatusString.FAIL;
                break;
            case Constant.DealStatusInt.REPAYMENTING:
                str = Constant.DealStatusString.REPAYMENTING;
                break;
            case Constant.DealStatusInt.REPAYMENTED:
                str =  Constant.DealStatusString.REPAYMENTED;
                break;
        }
        return str;
    }

    public int getIsDelete() {
        return is_delete;
    }

    public int getIsEffect() {
        return is_effect;
    }

    public int getIsNoob() {
        return is_noob;
    }

    public int getLastRepayTime() {
        return last_repay_time;
    }

    public DealTypeInfoModelExtend getTypeInfo() {
        return type_info;
    }

    public int getLastTime() {
        return last_time;
    }

    public int getLoantype() {
        return loantype;
    }

    public DealUserInfoModelExtend getUser() {
        return user;
    }

    public double getAllManageMoney() {
        return all_manage_money;
    }

    public int getNextRepayTime() {
        return next_repay_time;
    }

    public int getPayAmount() {
        return pay_amount;
    }

    public int getRemainTime() {
        return remain_time;
    }

    public double getMonthManageMoney() {
        return month_manage_money;
    }

    public int getRepayStartTime() {
        return repay_start_time;
    }

    public int getRepayTime() {
        return repay_time;
    }

    public int getRepayTimeType() {
        return repay_time_type;
    }

    public String getRepayTimeTypeFormat() {
        if(getRepayTimeType() == Constant.DealRepayTimeInt.FOR_DAY){
            return Constant.DealRepayTimeString.FOR_DAY;
        }else{
            return Constant.DealRepayTimeString.FOR_MONTH;
        }
    }

    public int getRiskRank() {
        return risk_rank;
    }

    public double getMonthRepayMoney() {
        return month_repay_money;
    }

    public int getSort() {
        return sort;
    }

    public int getStartTime() {
        return start_time;
    }

    public int getSuccessTime() {
        return success_time;
    }

    public int getTypeId() {
        return type_id;
    }

    public int getUpdateTime() {
        return update_time;
    }

    public int getUserId() {
        return user_id;
    }

    public String getAuditStat() {
        return AuditStat;
    }

    public String getBorrowAmountFormat() {
        return borrow_amount_format;
    }

    public String getDescription() {
        return description;
    }

    public String getMinLoanMoneyFormat() {
        return min_loan_money_format;
    }

    public String getName() {
        return name;
    }

    public String getRateForamt() {
        return rate_foramt;
    }

    public String getRemainTimeFormat() {
        return remain_time_format;
    }

    public String getRateForamtW() {
        return rate_foramt_w;
    }

    public double getTrueMonthRepayMoney() {
        return true_month_repay_money;
    }

    public String getAppUrl() {
        return app_url;
    }

    public String getSubName() {
        return sub_name;
    }

    public String getDealStatusFormatString() {
        return deal_status_format_string;
    }

    public String getMonthManageMoneyFormat() {
        return month_manage_money_format;
    }

    public String getMonthRepayMoneyFormat() {
        return month_repay_money_format;
    }

    public String getNeedMoney() {
        return need_money;
    }

    public String getShareUrl() {
        return share_url;
    }

    public String getUrl() {
        return url;
    }


}
