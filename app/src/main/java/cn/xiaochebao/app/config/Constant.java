package cn.xiaochebao.app.config;

public class Constant
{

	public static final String DIR_NAME = "xiaochebao";
	public static final String DOWN_LOAD_DIR_NAME = "Download";
	public static final String CACHE_LOAD_DIR_NAME = "Cache";
	public static final String Image_LOAD_DIR_NAME = "Image";
	public static final int SLIDE_FilP_TIME = 6000;
	public static final String WEBBVIEW_URI_NAME = "urlAddr";

	public static final String FRAG_ARGS_NAME_ID = "id";

	public static final class ActionName{
		public static final String UPGRADE_APP = "cn.xiaochebao.app.UpgradeApp";
		public static final String NOTIFY_CLICK = "com.lanxinbase.app.NOTIFY_CLICK";
		public static final String NOTIFY_CLEAR = "com.lanxinbase.app.NOTIFY_CLEAR";
	}

	public static final class CacheTime{
		public static final int SHORT_2 = 20;
		public static final int SHORT_6 = 60;
		public static final int MIDDLE_1 = 300;
		public static final int MIDDLE_2 = 600;
		public static final int LONG_1 = 1800;
		public static final int LONG_2 = 3600;
	}

	public static final class DeviceType
	{
		public static final String DEVICE_ANDROID = "android";
	}

	public static final class RequestDataType
	{
		public static final int BASE64 = 2;
		public static final int REQUEST = 1;
	}

	public static final class ResponseDataType
	{
		public static final int BASE64 = 0;
		public static final int JSON = 1;
		public static final int ARRAY = 2;
	}

	public static final class DealStatusInt
	{
		/** 等待材料 */
		public static final int WAIT = 0;
		/** 借款中 */
		public static final int LOANING = 1;
		/** 满标 */
		public static final int FULL = 2;
		/** 流标 */
		public static final int FAIL = 3;
		/** 还款中 */
		public static final int REPAYMENTING = 4;
		/** 已还清 */
		public static final int REPAYMENTED = 5;
	}

	public static final class DealStatusString
	{
		/** 等待材料 */
		public static final String WAIT = "等待材料";
		/** 借款中 */
		public static final String LOANING = "借款中";
		/** 满标 */
		public static final String FULL = "满标";
		/** 流标 */
		public static final String FAIL = "流标";
		/** 还款中 */
		public static final String REPAYMENTING = "还款中";
		/** 已还清 */
		public static final String REPAYMENTED = "已还清";
	}

	public static final class DealRepayTimeInt{
		public static final int FOR_DAY = 0;
		public static final int FOR_MONTH = 1;
	}

	public static final class DealRepayTimeString{
		public static final String FOR_DAY = "天";
		public static final String FOR_MONTH = "个月";
	}

	public static final class DealsActSearchConditionCid
	{
		/** 全部借款列表 */
		public static final String ALL = "0";
	}

	public static final class DealsActSearchConditionDealStatus
	{
		/** 等待材料 */
		public static final String ALL = "0";
		/** 借款中 */
		public static final String LOANING = "1";
		/** 满标 */
		public static final String FULL = "2";
		/** 流标 */
		public static final String FAIL = "3";
		/** 还款中 */
		public static final String REPAYMENTING = "4";
		/** 已还清 */
		public static final String REPAYMENTED = "5";
	}

	public static final class DealsActSearchConditionInterest
	{
		/** 全部利率 */
		public static final String ALL = "0";
		/** 10%以上 */
		public static final String TEN = "10";
		/** 12%以上 */
		public static final String TWELVE = "12";
		/** 15%以上 */
		public static final String FIFTEEN = "15";
		/** 18%以上 */
		public static final String EIGHTEEN = "18";
	}

	public static final class DealsActSearchConditionLeftTime
	{
		/** 全部剩余时间 */
		public static final String ALL = "0";
		/** 1天内 */
		public static final String ONE = "1";
		/** 3天内 */
		public static final String THREE = "3";
	}


	/**
	 * 还款方式
	 * 
	 */
	public static final class LoanType
	{
		/** 等额本息 */
		public static final String ZERO = "0";
		/** 付息还本 */
		public static final String ONE = "1";
		/** 到期还本息 */
		public static final String TWO = "2";
	}

	/**
	 * 还款方式字符串
	 * 
	 */
	public static final class LoanTypeString
	{
		/** 等额本息 */
		public static final String ZERO = "等额本息";
		/** 付息还本 */
		public static final String ONE = "付息还本";
		/** 到期还本息 */
		public static final String TWO = "到期还本息";
	}


	public static final class MyInvestConditionMode
	{
		/** 我的投资 */
		public static final String INDEX = "index";
		/** 招标中借款 */
		public static final String INVITE = "invite";
		/** 回收中借款 */
		public static final String ING = "ing";
		/** 已回收借款 */
		public static final String OVER = "over";
		/** 我的坏账 */
		public static final String BAD = "bad";
	}

	/**
	 * [type] => (1:文章 2:url连接址) [data] => (type = 1:文章ID;type =2:url链接)
	 * [open_url_type] =>(type = 2时，有效；0:使用webview;1:使用系统浏览器)
	 */
	public static final class Init_Adv_List
	{
		public static final int Init_Adv_List_Type_1 = 1;

		public static final int Init_Adv_List_Type_2 = 2;

		public static final int Init_Adv_List_Open_Url_Type_0 = 0;

		public static final int Init_Adv_List_Open_Url_Type_1 = 1;
	}

	public static final class PushType
	{
		public static final int NORMAL = 1;
		public static final int PROJECT_ID = 2;
		public static final int ARTICLE_ID = 3;
		public static final int URL = 4;
	}

}
