package cn.xiaochebao.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.xiaochebao.app.R;
import cn.xiaochebao.app.core.SDinject;
import cn.xiaochebao.app.interfaces.ViewInject;
import cn.xiaochebao.app.libs.Logger;
import cn.xiaochebao.app.model.AdInfoModel;


/**
 * 首页幻灯片
 * Created by Alan on 2017/04/07 0007.
 */
public class SlideFragment extends Fragment {

    @ViewInject(id=R.id.side_textView)
    private TextView textView;

    @ViewInject(id = R.id.side_ad_imageView)
    private ImageView imageView;

    private static AdInfoModel itemModel;
    public SlideFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   public static Fragment getInstance(int pos,AdInfoModel a) {
       SlideFragment fragment = new SlideFragment();
       itemModel = a;

       return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_silde,container,false);

        SDinject.injectView(this,view);
        textView.setText(itemModel.getName());

        if(itemModel.getImage().equals("")){
            imageView.setImageResource(R.drawable.z_init_slide);

        }else{
            Logger.info(itemModel.getImage());
            //Bitmap bitmap = BitmapFactory.decodeFile(itemModel.getImage());
            //imageView.setImageBitmap(bitmap);
        }

//        if(index == itemModel.length - 1){
//            closeButton.setVisibility(View.VISIBLE);
//        }
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(FrameApp.getInstance(),Main22Activity.class);
//                getActivity().startActivity(intent);
//                getActivity().finish();
//            }
//        });

        return view;
    }

}
