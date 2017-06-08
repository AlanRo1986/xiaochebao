package cn.xiaochebao.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.base.BaseFragment;
import cn.xiaochebao.app.config.Constant;

public class ItemFragment extends BaseFragment {

    private int id = 0;
    public ItemFragment() {
    }

    public static ItemFragment newInstance(int id) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.FRAG_ARGS_NAME_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            id = getArguments().getInt(Constant.FRAG_ARGS_NAME_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        return view;
    }


    public void init() {

    }

    @Override
    public void bindModel() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
