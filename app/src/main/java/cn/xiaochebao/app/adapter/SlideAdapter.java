package cn.xiaochebao.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.xiaochebao.app.fragment.SlideFragment;
import cn.xiaochebao.app.model.AdInfoModel;

/**
 * 幻灯片适配器对象
 * Created by Alan on 2017/04/07 0007.
 */
public class SlideAdapter extends FragmentStatePagerAdapter {
    private int count = 0;
    private List<AdInfoModel> adItemModels = new ArrayList<>();

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    public SlideAdapter(FragmentManager fm,AdInfoModel model) {
        super(fm);
        addAdItemModels(model);

    }


    @Override
    public Fragment getItem(int position) {
        return SlideFragment.getInstance(position,getAdItemModel(position));
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addAdItemModels(AdInfoModel ad) {
        this.adItemModels.add(ad);
        setCount(count+1);
    }

    public AdInfoModel getAdItemModel(int position) {
        return adItemModels.get(position);
    }


}
