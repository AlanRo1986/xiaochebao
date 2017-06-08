package cn.xiaochebao.app.event;

import android.content.Context;
import android.view.View;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.libs.Logger;

/**
 * Created by Alan on 2016/08/11 0011.
 */
public class BntEvent implements View.OnClickListener {

    private Context mContext;

    public BntEvent(Context ctx){
        mContext = ctx;
    }


    public static BntEvent getInstance(Context ctx){
        return new BntEvent(ctx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 1:
                break;
            default:
                Logger.info("No thing...");
                break;
        }

    }
}
