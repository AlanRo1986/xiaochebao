package cn.xiaochebao.app.config;

/**
 * Created by Alan on 2016/08/23 0023.
 */
public class Config {
    public static final String CFG_API_HASH = "";
    public static final String CFG_API_PRE = "http://";
    public static final String CFG_API_URI = "t.xiaochebao.cn";
    public static final String CFG_API_END = "/mapi/index.php";
    public static final String CFG_SERVER_URI = CFG_API_PRE + CFG_API_URI + CFG_API_END;
    public static final String CFG_UPDATE_FILENAME = "xiaochebao" + "_" + SoftType.P2P;

    public static final class SoftType
    {
        public static final String O2O = "o2o";
        public static final String P2P = "p2p";
    }

    public static final class API_CTL{
        public static final String INDEX = "init";
        public static final String DEAL = "deal";//id
        public static final String DEALS = "deals";
        public static final String DEAL_MOBILE = "deal_mobile";//id
        public static final String LOGIN = "login";
        public static final String LOGIN_OUT = "login_out";
        public static final String REGISTER = "register";
        public static final String SAVE_RESET_PWD = "save_reset_pwd";
        public static final String ARTICLE_LIST = "article_list";
        public static final String UC_CENTER = "uc_center";
        public static final String MALL = "integral_mall";
        public static final String GOODS = "goods_information";//id,t
        public static final String UC_INCHARGE_LOG = "uc_incharge_log";
        public static final String UC_CARRY_MONEY_LOG = "uc_carry_money_log";
        public static final String UC_BANK = "uc_bank";
        public static final String UC_CARRY_MONEY = "uc_carry_money";//bib
        public static final String UC_ACCOUNT_LOG = "uc_account_log";//status
        public static final String UC_INVEST = "uc_invest";//status
        public static final String UC_GIFTMONEY = "uc_giftmoney";//status
        public static final String UC_BORROWED = "uc_borrowed";//status
        public static final String UC_STATIS = "uc_financial_statistics";
        public static final String VERSION = "version";
        public static final String ADV = "adv";

    }

    public static final class API_ACT{
        public static final String INDEX = "init";
        public static final String DEAL = "deal";//id
        public static final String DEALS = "deals";
        public static final String DEAL_MOBILE = "deal_mobile";//id
        public static final String LOGIN = "login";
        public static final String LOGIN_OUT = "login_out";
        public static final String REGISTER = "register";
        public static final String SAVE_RESET_PWD = "save_reset_pwd";
        public static final String ARTICLE_LIST = "article_list";
        public static final String UC_CENTER = "uc_center";
        public static final String MALL = "integral_mall";
        public static final String GOODS = "goods_information";//id,t
        public static final String UC_INCHARGE_LOG = "uc_incharge_log";
        public static final String UC_CARRY_MONEY_LOG = "uc_carry_money_log";
        public static final String UC_BANK = "uc_bank";
        public static final String UC_CARRY_MONEY = "uc_carry_money";//bib
        public static final String UC_ACCOUNT_LOG = "uc_account_log";//status
        public static final String UC_INVEST = "uc_invest";//status
        public static final String UC_GIFTMONEY = "uc_giftmoney";//status
        public static final String UC_BORROWED = "uc_borrowed";//status
        public static final String UC_STATIS = "uc_financial_statistics";
        public static final String VERSION = "version";
        public static final String ADV = "adv";
    }

}


