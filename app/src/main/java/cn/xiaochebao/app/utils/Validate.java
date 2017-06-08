package cn.xiaochebao.app.utils;

import cn.xiaochebao.app.adapter.ValiDateParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alan on 2017/04/05 0005.
 *
 * The example:
 *
 * ValiDateParams[] data = new ValiDateParams[7];
 * data[0] = ValiDateParams.getInstance("a123456",true,"username","请输入用户名");
 * data[1] = ValiDateParams.getInstance("a12345678",true,"password","用户密码必须包含字母+数字");
 * data[2] = ValiDateParams.getInstance("341455770@qq.com",true,"email","请输入邮箱地址");
 *
 * data[3] = ValiDateParams.getInstance("a12345678",true,ValiDateParams.VALIDATOR_COMPARE,"两次输入的密码不一致");
 * data[3].setTo("a12345678");
 * data[3].setOperator(ValiDateParams.OPERATOR_EQUIVALENT);
 *
 * data[4] = ValiDateParams.getInstance("56",true,ValiDateParams.VALIDATOR_RANGE,"年龄段范围应该在10-100岁之间");
 * data[4].setMin(10);
 * data[4].setMax(100);
 *
 * data[5] = ValiDateParams.getInstance("550112198812151230",true,"idno","输入的身份证号码不正确");
 * data[6] = ValiDateParams.getInstance("12345678",true,ValiDateParams.VALIDATOR_LENGTH,"密码长度必须大于6位");
 * data[6].setMin(6);
 *
 * Validate validate = new Validate();
 * validate.setValidateparam(data);
 * String res = validate.doValidate();
 * if(res!=null){
 *     do something....
 * }
 * Logger.info(res);
 *
 */
public class Validate {

    private Map<String,String> validator = new HashMap<String, String>(){
        {
            put("email","[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
            put("phone","^(((0[1|2]\\d{1}\\-)|(0[1|2]\\d{1}))\\d{8}|((0\\d{3}\\-)|(0\\d{3}))\\d{7,8})$");
            put("mobile","^1[3|4|5|6|7|8]\\d{1}\\d{8}$");
            put("url","^[a-zA-z]+://[^\\s]*");
            put("zip","^[0-9][0-9]{5}$");
            put("number","^[0-9]+$");
            put("currency","^[0-9]+(\\.[0-9]+)?$");
            put("qq","^[1-9][0-9]{4,9}$");
            put("integer","^[-+]?[0-9]+$");
            put("integerpositive","^[+]?[0-9]+$");
            put("double","^[-+]?[0-9]+(\\.[0-9]+)?$");
            put("doublepositive","^[+]?[0-9]+(\\.[0-9]+)?$");
            put("english","^[A-Za-z]+$");
            put("chinese","^[\\u4e00-\\u9fa5]$");
            put("nochinese","^[A-Za-z0-9_-]+$");
            put("idno","^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$");
            put("birthday","^((19[0-9]{2})|(20[0-3]{2}))+((0[1-9]{1})|([1|2][0-2]{1}))+((0[1-9]{1})|([1|2]\\d{1})|(3[0-1]{1}))$");
            put("username","^[\\w]{3,}$");
            put("password","^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$");
        }
    };
    private ValiDateParams[] validateparam;

    public Validate(){

    }

    /**
     * 正则匹配
     * @param input 输入的内容
     * @param reg 正则表达式
     * @return
     */
    private static boolean match(String input,String reg){
        if (input == null || reg == null || input.equals("") || reg.equals("")){
            return false;
        }
        return input.matches(reg);
    }

    /**
     * 验证之后取错误代码,如果没有错误则返回null
     * @return
     */
    public String getError(){
        for (ValiDateParams data:validateparam){
            if (!data.getResult()){
                return data.getMessage();
            }
        }
        return null;
    }

    /**
     * 进行数据认证
     * @return
     */
    public String doValidate(){
        if (this.validateparam == null){
            return null;
        }
        for (ValiDateParams data:validateparam){

            if (data.getInput().equals("") && data.getRequire() == true){
                data.setResult(false);
            }else{
                data.setResult(true);
            }

            if(data.getResult() == true && !data.getInput().equals("")){
                switch (data.getValidator()){
                    case ValiDateParams.VALIDATOR_CUSTOM:
                        data.setResult(match(data.getInput(),data.getRegexp()));
                        break;
                    case ValiDateParams.VALIDATOR_COMPARE:
                        boolean res = false;
                        if(data.getOperator()!=null && !data.getOperator().equals("")) {
                            switch (data.getOperator()) {
                                case ValiDateParams.OPERATOR_EQUIVALENT:
                                    res = data.getInput().equals(data.getTo());
                                    break;
                                case ValiDateParams.OPERATOR_EQUIVALENT_GREATER:
                                    res = Double.parseDouble(data.getInput()) >= Double.parseDouble(data.getTo());
                                    break;
                                case ValiDateParams.OPERATOR_EQUIVALENT_LESS:
                                    res = Double.parseDouble(data.getInput()) <= Double.parseDouble(data.getTo());
                                    break;
                                case ValiDateParams.OPERATOR_GREATER:
                                    res = Double.parseDouble(data.getInput()) > Double.parseDouble(data.getTo());
                                    break;
                                case ValiDateParams.OPERATOR_LESS:
                                    res = Double.parseDouble(data.getInput()) < Double.parseDouble(data.getTo());
                                    break;
                                case ValiDateParams.OPERATOR_NOT_EQUIVALENT:
                                    res = !data.getInput().equals(data.getTo());
                                    break;
                            }
                        }
                        data.setResult(res);
                        break;
                    case ValiDateParams.VALIDATOR_LENGTH:
                        data.setResult(data.getInput().length() >= data.getMin());

                        if(data.getMax() > data.getMin()){
                            data.setResult(data.getInput().length() < data.getMax());
                        }
                        break;
                    case ValiDateParams.VALIDATOR_RANGE:
                        data.setResult(Integer.parseInt(data.getInput()) >= data.getMin());
                        if(data.getMax() > data.getMin()){
                            data.setResult(Integer.parseInt(data.getInput()) < data.getMax());
                        }
                        break;
                    default:
                        data.setResult(match(data.getInput(),validator.get(data.getValidator())));
                        break;
                }
            }
        }

        String error = getError();
        setValidateparam(null);
        return error;
    }

    /**
     * 设置需要验证的ValidateParam对象
     * @param validateparam
     */
    public void setValidateparam(ValiDateParams[] validateparam) {
        this.validateparam = validateparam;
    }

}

