package cn.xiaochebao.app.event;

import android.content.Context;
import android.view.MenuItem;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.utils.DialogUtil;

/**
 *
 * Created by Alan on 2017/04/14 0014.
 */
public class OpentionItemSelectedEvent {

    private Context mContext;

    public OpentionItemSelectedEvent(Context ctx){
        mContext = ctx;
    }
    public static OpentionItemSelectedEvent getInstance(Context ctx){
        return new OpentionItemSelectedEvent(ctx);
    }

    public boolean OnClick(MenuItem item){

        int id = item.getItemId();
        switch (id){
            case R.id.action_ucenter:
                //DialogUtil.getInstance(mContext).snackBar(item.getActionView(),mContext.getString(R.string.app_name),null,null);
                break;

            default:
                Logger.info("Have not find the MenuItem ID.");
                break;
        }


        return true;
    }

}
