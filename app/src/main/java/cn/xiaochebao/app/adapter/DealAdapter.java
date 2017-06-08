package cn.xiaochebao.app.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import cn.xiaochebao.app.R;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.core.FrameApp;
import cn.xiaochebao.app.interfaces.OnDealListInteractionListener;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.model.DealInfoModel;
import cn.xiaochebao.app.view.RoundProgressBar;

/**
 * Created by Administrator on 2017/04/24 0024.
 */
public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private Context mContext;
    private List<DealInfoModel> mDealModels = null;
    private OnDealListInteractionListener mListener;

    public DealAdapter(List<DealInfoModel> items,OnDealListInteractionListener linstener){
        this.mDealModels = items;
        this.mListener = linstener;
        mContext = FrameApp.getInstance();
    }

    public static DealAdapter getInstance(List<DealInfoModel> items,OnDealListInteractionListener linstener){
        return new DealAdapter(items,linstener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ada_deal_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mDealModels.get(position);
        holder.mTvDealStatus.setText(mDealModels.get(position).getDealStatusFormat());
        holder.mTvDealTitle.setText(mDealModels.get(position).getName());
        holder.mTvDealBorrowMoney.setText(mDealModels.get(position).getBorrowAmountFormat());
        holder.mTvDealRate.setText(mDealModels.get(position).getRateForamtW());
        holder.mTvDealRepayTime.setText(mDealModels.get(position).getRepayTime() + mDealModels.get(position).getRepayTimeTypeFormat());
        holder.mIndex = position;

        holder.mTvDealProgress.setProgress(mDealModels.get(position).getProgressPoint());

        if (mDealModels.get(position).getProgressPoint() < 10) {
            holder.mTvDealProgress.setCricleProgressColor(mContext.getResources().getColor(R.color.red));
        }else if (mDealModels.get(position).getProgressPoint() < 20){
            holder.mTvDealProgress.setCricleProgressColor(mContext.getResources().getColor(R.color.orange));
        }else if (mDealModels.get(position).getProgressPoint() < 50){
            holder.mTvDealProgress.setCricleProgressColor(mContext.getResources().getColor(R.color.colorf23));
        }else if (mDealModels.get(position).getProgressPoint() < 80){
            holder.mTvDealProgress.setCricleProgressColor(mContext.getResources().getColor(R.color.color9c0));
        }else{
            holder.mTvDealProgress.setCricleProgressColor(mContext.getResources().getColor(R.color.green2));
        }

        if (mDealModels.get(position).getDealStatus() == Constant.DealStatusInt.WAIT){
            holder.mLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.low_gray));
        }else if (mDealModels.get(position).getDealStatus() == Constant.DealStatusInt.LOANING){
            holder.mLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.c3fa9f5));
        }else if (mDealModels.get(position).getDealStatus() == Constant.DealStatusInt.FULL ){
            holder.mLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.green2));
        }else if (mDealModels.get(position).getDealStatus() == Constant.DealStatusInt.REPAYMENTING || mDealModels.get(position).getDealStatus() == Constant.DealStatusInt.REPAYMENTED){
            holder.mLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.bce25e));
        }else if (mDealModels.get(position).getDealStatus() == Constant.DealStatusInt.FAIL){
            holder.mLayoutTitle.setBackgroundColor(mContext.getResources().getColor(R.color.lightgray));
        }
    }


    @Override
    public int getItemCount() {
        return mDealModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View mView;
        public int mIndex = -1;
        public LinearLayout mLayoutTitle;
        public TextView mTvDealStatus;
        public TextView mTvDealTitle;
        public TextView mTvDealBorrowMoney;
        public TextView mTvDealRate;
        public TextView mTvDealRepayTime;
        public RoundProgressBar mTvDealProgress;
        public DealInfoModel mItem;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mLayoutTitle = (LinearLayout) itemView.findViewById(R.id.ada_layout_title);
            mTvDealStatus = (TextView) itemView.findViewById(R.id.ada_tv_deal_status);
            mTvDealTitle = (TextView) itemView.findViewById(R.id.ada_tv_deal_title);
            mTvDealBorrowMoney = (TextView) itemView.findViewById(R.id.ada_tv_borrow_money);
            mTvDealRate = (TextView) itemView.findViewById(R.id.ada_tv_rate);
            mTvDealRepayTime = (TextView) itemView.findViewById(R.id.ada_tv_repay_time);
            mTvDealProgress = (RoundProgressBar) itemView.findViewById(R.id.ada_tv_progress);
            mTvDealProgress.setDefaultConfig();
            mView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (null != mListener) {
                mListener.onListInteraction(mIndex);
            }
        }
    }
}
