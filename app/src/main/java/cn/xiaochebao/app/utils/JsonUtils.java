package cn.xiaochebao.app.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.xiaochebao.app.libs.Logger;

/**
 * JSON对象解析类
 * Created by Alan on 2017/04/01 0001.
 */
public class JsonUtils {
    public JsonUtils(){}

    /**
     * 判断是否为JSON字符串,通常判断方式是首位是否包含\{\" and \"\} 符号
     * @param str
     * @return
     */
    public static boolean isJson(String str){
        if(str.isEmpty() || str == null){
            return false;
        }

        //return str.indexOf("{\"") == 0 && str.lastIndexOf("}") > 5;
        return str.matches("^\\{(.+:.+,*){1,}\\}");
    }

    /**
     * 判断是否json字符串数组,目前是通过\[\]符号确认,如果首位都是[ ]符号,则返回true
     * @param str
     * @return
     */
    public static boolean isJsonArr(String str){
        if(str.isEmpty() || str == null){
            return false;
        }
        return str.indexOf("[") > -1 && str.lastIndexOf("]") > 0;
    }

    /**
     * 通常御用http请求获取到底json字符串,解析成功后返回一个Map集合对象,失败则throw error
     * @param str
     * @return
     */
    public static Map<String,Object> jsonToMap(String str) {
        if (!isJson(str)) {
            return null;
        }

        Map<String,Object> data = new HashMap<>();
        try {
            JSONObject json = new JSONObject(str);
            Iterator it = json.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object val = json.opt(key);
                if(isJson(json.optString(key)) == true){
                    val = JsonUtils.jsonToMap(json.optString(key));
                }else if (isJsonArr(json.optString(key)) == true){
                    val = JsonUtils.parseJsonArr(json.optString(key));
                }

                data.put(key, val);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 传入实例化过度JSON对象
     * Json对象转换成Map<String,Object>对象,其中Object对象通俗理解为:String,int,double,float,boolean,List<Map<String,Object>>
     * @param json
     * @return
     */
    public static Map<String,Object> jsonToMap(JSONObject json){

        Map<String,Object> data = new HashMap<>();

        Iterator it = json.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object val = json.opt(key);

            if(isJson(json.optString(key)) == true){
                val = JsonUtils.jsonToMap(json.optString(key));
            }else if (isJsonArr(json.optString(key)) == true){
                val = JsonUtils.parseJsonArr(json.optString(key));
            }
            data.put(key, val);
        }

        return data;
    }

    /**
     * 解析Json数组
     * 返回一个List<Map<String, Object>>对象
     * @param str
     * @return
     */
    public static List<Map<String, Object>> parseJsonArr(String str){
        if (!isJsonArr(str)) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            JSONArray jsonArray = new JSONArray(str);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                map = jsonToMap(jsonObject);
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }




    /**
     * 自动化绑定模型
     * 模型必须是以下设定的数据类型对象:String,Boolean,int,double,float,List,OtherModel
     * List<T> 为弱类型,一般属于自定义的模型类,目前我也只测试过自定义的模型类绑定,其他的需要自身亲临测试
     * OtherModel 其他自定义模型类
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object parseObject(Map<String,Object> data, Class<T> clazz) {
        Object object = null;
        try {
            if (data == null){
                return  null;
            }

            object = Class.forName(clazz.getName()).newInstance();
            Field[] fields =  clazz.getDeclaredFields();
            Type gType = null;
            Class clz = null;

            if(fields != null && fields.length > 0){
                for (Field field:fields) {
                    field.setAccessible(true);

                    /**
                     * 这个是扩展类
                     */
                    for (String key:data.keySet()){
                        if (field.getName().equals(key)){
                            switch (field.getType().getSimpleName()){
                                case "String":
                                    field.set(object,data.get(key));
                                    break;
                                case "Boolean":
                                    field.set(object,data.get(key));
                                    break;
                                case "int":
                                    field.set(object,Integer.valueOf(String.valueOf(data.get(key))));
                                    break;
                                case "double":
                                    field.set(object,Double.valueOf(String.valueOf(data.get(key))));
                                    break;
                                case "float":
                                    field.set(object,Float.valueOf(String.valueOf(data.get(key))));
                                    break;
                                case "List":
                                    gType = field.getGenericType();
                                    ParameterizedType pType = (ParameterizedType)gType;
                                    clz = (Class)pType.getActualTypeArguments()[0];
                                    field.set(object,parseObjectByList(data.get(key),clz));
                                    break;
                                default:
                                    gType = field.getGenericType();
                                    clz = (Class)gType;
                                    field.set(object,parseObjectByModel(data.get(key),clz));
                                    break;
                            }
//                            if(field.getGenericType().toString().indexOf("Model") > 0){
//                                gType = field.getGenericType();
//                                if(gType instanceof ParameterizedType){
//                                    ParameterizedType pType = (ParameterizedType)gType;
//                                    clz = (Class)pType.getActualTypeArguments()[0];
//                                    field.set(object,parseObjectByList(data.get(key),clz));
//                                }else{
//                                    clz = (Class)gType;
//                                    field.set(object,parseObjectByModel(data.get(key),clz));
//                                }
//                            }else{
//                                field.set(object,data.get(key));
//                            }
                            data.remove(key);
                            break;
                        }
                    }

                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * 处理模型类模型对象
     * @param obj
     * @param aClass
     * @return
     */
    private static Object parseObjectByModel(Object obj, Class<?> aClass) {

        return parseObject((Map<String, Object>) obj,aClass);
    }


    /**
     * 处理模型类List对象<T>这里面暂时只测试过自定义模型类
     * @param obj
     * @param aClass
     * @return
     */
    private static Object parseObjectByList(Object obj, Class<?> aClass) {
        List<Object> mList = new ArrayList<>();
        List<Map<String,Object>> list = (List<Map<String,Object>>) obj;
        if (list.size() > 0){
            for (Map<String,Object> map : list){
                mList.add(parseObject(map,aClass));
            }
        }
        return mList;
    }

}
