package cn.xiaochebao.app.core;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import cn.xiaochebao.app.interfaces.ViewInject;

/**
 * Created by Alan on 2017/03/31 0031.
 */
public class SDinject {

    public SDinject(){

    }

    public static void injectView(Activity activity){
        injectView(activity,activity.getWindow().getDecorView());
    }

    public static void injectView(Object obj, View view){
        Field[] fields = obj.getClass().getDeclaredFields();

        if(fields != null && fields.length > 0){
            for (Field field:fields) {
                try {
                    field.setAccessible(true);
                    if(field.get(obj) == null) {
                        ViewInject e = field.getAnnotation(ViewInject.class);
                        if(e != null) {
                            int viewId = e.id();
                            //Logger.info(viewId);
                            field.set(obj, view.findViewById(viewId));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
