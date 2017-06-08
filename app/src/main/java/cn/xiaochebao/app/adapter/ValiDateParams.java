package cn.xiaochebao.app.adapter;

/**
 * Created by Alan on 2017/04/05 0005.
 */
public class ValiDateParams {
    /**
     * 必须
     * 如果validator传入的是custom,那么必须设置regexp,regexp是一个有效的正则表达式
     */
    public static final String VALIDATOR_CUSTOM = "custom";

    /**
     * 比较
     */
    public static final String VALIDATOR_COMPARE = "compare";

    /**
     * 长度
     * 需要传入min&max进行长度匹配,允许不传入max进行最大长度匹配
     */
    public static final String VALIDATOR_LENGTH = "length";

    /**
     * 范围
     * 如果validator传入range那么,必须传入min&max进行范围判断
     */
    public static final String VALIDATOR_RANGE = "range";

    /**
     * operator主要的参数,如果设置了operator那么需要传入to这个值,一般是double
     */
    public static final String OPERATOR_EQUIVALENT = "==";
    public static final String OPERATOR_GREATER = ">";
    public static final String OPERATOR_EQUIVALENT_GREATER = ">=";
    public static final String OPERATOR_LESS = "<";
    public static final String OPERATOR_EQUIVALENT_LESS = "<=";
    public static final String OPERATOR_NOT_EQUIVALENT = "!=";



    /**
     * 自定义传入的正则表达式
     */
    private String regexp = null;

    /**
     * 输入的内容,通常为字符串型
     */
    private String input = null;
    private boolean require = true;
    private boolean result = true;
    private String validator = null;

    /**
     * 运算
     */
    private String operator = OPERATOR_EQUIVALENT;
    private String to = null;
    private int min = 0;
    private int max = 0;
    private String message = "验证失败";

    /**
     * 初始化对象
     * @param i = input 输入的内容
     * @param r require 是否必须输入
     * @param v validator 验证器
     * @param m message 反馈的提示信息
     */
    public ValiDateParams(String i,boolean r,String v,String m){
        setInput(i);
        setRequire(r);
        setValidator(v);
        setMessage(m);
    }

    /**
     * 初始化对象
     * @param i = input 输入的内容
     * @param r require 是否必须输入
     * @param v validator 验证器
     * @param m message 反馈的提示信息
     */
    public static ValiDateParams getInstance(String i,boolean r,String v,String m){
        return new ValiDateParams(i, r, v, m);
    }

    public String getTo() {
        return to;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public String getInput() {
        return input;
    }

    public String getMessage() {
        return message;
    }

    public String getOperator() {
        return operator;
    }

    public String getRegexp() {
        return regexp;
    }

    public String getValidator() {
        return validator;
    }

    public boolean getRequire(){
        return require;
    }

    public boolean getResult(){
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }
}
