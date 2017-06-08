package cn.xiaochebao.app.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import java.util.List;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.config.Constant;
import cn.xiaochebao.app.interfaces.OnSlideClickListener;

/**
 * 幻灯片类
 * Created by Alan on 2017/04/25 0025.
 */
public class SlideView extends RelativeLayout{

    private Context mContext;
    private ViewFlipper mFlipper;
    private int displayedChild;
    private List<Button> mButtonList = new ArrayList<Button>();
    private LayoutInflater mLayoutInflater;

    private int mCount = 0;
    private Animation preInAnimated,preOutAnimated,nextInanimated,nextOutAnimated;


    public SlideView(Context ctx){
        super(ctx);
        mContext = ctx;
        init();
    }

    public SlideView (Context ctx, AttributeSet attrs){
        super(ctx,attrs);
        mContext = ctx;
        init();
    }

    private void init(){

        mLayoutInflater = ((Activity)mContext).getLayoutInflater();
        View view = mLayoutInflater.inflate(R.layout.z_slide_pager,null);

        mFlipper = (ViewFlipper)view.findViewById(R.id.slide_pager_viewFlipper);
        addView(view);

        setAnimation( R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left,R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right);
        mFlipper.setFlipInterval(Constant.SLIDE_FilP_TIME);
        mFlipper.setAutoStart(true);

    }

    /**
     * 上一个幻灯片,做了点击事件,没有做触摸切换到功能
     */
    public void preFlip() {
        mFlipper.startFlipping();
        mFlipper.setInAnimation(preInAnimated);
        mFlipper.setOutAnimation(preOutAnimated);
        mFlipper.getOutAnimation().setAnimationListener(new SlideAnimationListener(this));
        mFlipper.showPrevious();
    }


    /**
     * 开始幻灯片
     */
    public void startFlip() {
        mFlipper.startFlipping();
        mFlipper.setInAnimation(nextInanimated);
        mFlipper.setOutAnimation(nextOutAnimated);
        mFlipper.getOutAnimation().setAnimationListener(new SlideAnimationListener(this));
        mFlipper.showNext();
    }

    /**
     * 设置幻灯片的动画
     * @param preIn
     * @param preOut
     * @param nextIn
     * @param nextOut
     */
    public void setAnimation(Animation preIn, Animation preOut, Animation nextIn, Animation nextOut){
        this.preInAnimated = preIn;
        this.preOutAnimated = preOut;
        this.nextInanimated = nextIn;
        this.nextOutAnimated = nextOut;

    }

    /**
     * 设置幻灯片的动画效果by id
     * @param preIn
     * @param preOut
     * @param nextIn
     * @param nextOut
     */
    public void setAnimation(int preIn, int preOut, int nextIn, int nextOut){
        this.setAnimation(
                AnimationUtils.loadAnimation(getContext(), preIn),
                AnimationUtils.loadAnimation(getContext(), preOut),
                AnimationUtils.loadAnimation(getContext(), nextIn),
                AnimationUtils.loadAnimation(getContext(), nextOut));
    }


    /**
     * 把图片添加到幻灯片中
     * @param images
     */
    public void setImage(List<ImageView> images) {
        for (int i = 0; i < images.size(); i++) {
            mFlipper.addView(images.get(i));
        }

        mCount = images.size();

        //读取幻灯片小标签的布局文件
        View view = mLayoutInflater.inflate(R.layout.z_slide_pager_button, null);
        LinearLayout butLayout = (LinearLayout) view.findViewById(R.id.slide_pager_button_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30,4);

        //循环加入小标签
        for (int i = 0; i < mCount; i++) {
            Button button = new Button(getContext());
            lp.setMargins(1,0,1,10);

            button.setLayoutParams(lp);

            if(i == 0){
                button.setBackgroundResource(R.drawable.lib_slideview_btn_active);
            }else{
                button.setBackgroundResource(R.drawable.lib_slideview_btn_notactive);
            }

            butLayout.addView(button);
            mButtonList.add(button);
        }
        //设置小标签布局文件的参数
        LayoutParams rlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_END);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            rlp.setMarginEnd(50);
        }
        butLayout.setLayoutParams(rlp);

        //把小标签布局文件加入到this对象中
        addView(view);

        mFlipper.setDisplayedChild(mCount - 1);
        startFlip();
    }

    /**
     * 设置点击事件监听
     * @param listener
     */
    public void setOnSlideClickListener (final OnSlideClickListener listener){
        mFlipper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClick(mFlipper.getDisplayedChild());
                }
            }
        });
    }

    /**
     * 取当前幻灯片的index id
     * @return
     */
    public int getDisplayedChild() {
        return displayedChild;
    }

    public int getCount() {
        return mCount;
    }

    /**
     * 设置小标签的背景颜色
     * @param position
     */
    private void setButton(int position) {
        if (mCount > 1) {
            for (int m = 0; m < mCount; m++) {
                if (m == position % mCount) {
                    mButtonList.get(m).setBackgroundResource(R.drawable.lib_slideview_btn_active);
                } else {
                    mButtonList.get(m).setBackgroundResource(R.drawable.lib_slideview_btn_notactive);
                }
            }
        }
    }


    /**
     * 动画切换类
     */
    private class SlideAnimationListener implements Animation.AnimationListener {
        private SlideAnimationListener(SlideView slideView) {

        }

        @Override
        public final void onAnimationEnd(Animation paramAnimation) {

        }

        @Override
        public final void onAnimationRepeat(Animation paramAnimation) {
        }

        @Override
        public final void onAnimationStart(Animation paramAnimation) {
            displayedChild = mFlipper.getDisplayedChild();
            setButton(displayedChild);
        }
    }

}
