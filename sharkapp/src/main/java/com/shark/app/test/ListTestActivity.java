package com.shark.app.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.businessframehelp.adapter.ListTreeAdapter;
import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_LibraryLawDependence;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/13.
 */
@Deprecated
public class ListTestActivity extends FrameActivity {
    @BindView(R.id.testlist)
    ListView mTestlist;
    private List<ListTreeAdapter.ListItem> alllist;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_list);
        alllist = new ArrayList<>();
        List<DateBase_Entity> optionlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().query("select distinct jianchaxiangmusanji from `Entity_LibraryLawDependence`",new Entity_LibraryLawDependence()));
        for (int i = 0; i <optionlist.size() ; i++) {
            alllist.add(new ListTreeAdapter.ListItem().setShowtitle(optionlist.get(i).getValue("检查项目三级")).setLevel(1));
        }
        TestListAdapter adapter=new TestListAdapter(this, alllist,3);
        mTestlist.setAdapter(adapter);

    }

    @Override
    public void handleMessage(Message msg) {

    }

    class TestListAdapter extends ListTreeAdapter{

        /**
         * @param context         context
         * @param alllist         一层目录
         * @param whillclicklevel 需要进行跳转反馈的层级
         */
        public TestListAdapter(Activity context, List<ListTreeAdapter.ListItem> alllist, int whillclicklevel) {
            super(context, alllist, whillclicklevel);
        }

//        @Override
//        public ListView getListView() {
//            return mTestlist;
//        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getViewResId(int level) {
            switch(level){
                case 1:
                    return R.layout.item_activity_check_parent;
                case 2:
                    return R.layout.item_activity_check_child;
                case 3:
                    return R.layout.test_listitem3;
            }
            return 0;
        }

        @Override
        public ListTreeAdapter.ListViewHolder getHolder() {
            return new ListViewHolder() {

                @Override
                public void bindView(View view, final ListItem entity) {
                    CheckBox check= (CheckBox) view.findViewById(R.id.titleselect);
                    if(check!=null){
                        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                entity.setIsselect(isChecked);
                            }
                        });
                    }
                }

                @Override
                public void canvasView(View view,ListItem entity) {
                    TextView title= (TextView) view.findViewById(R.id.tv_title);
                    title.setText(entity.getShowtitle());
                    CheckBox check= (CheckBox) view.findViewById(R.id.titleselect);
                    if(check!=null){
                        check.setChecked(entity.isselect());
                    }
                }
            };
        }

        @Override
        public List<ListTreeAdapter.ListItem> loaddatafromserverImp(View v, int position,int level) {
            if(level==2){
                return null;
            }
            List<DateBase_Entity> optionlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_LibraryLawDependence().putlogic2value("jianchaxiangmusanji","=",alllist.get(position).getShowtitle())));
            List<ListTreeAdapter.ListItem> tmplist=new ArrayList<>();
            for (int i = 0; i <optionlist.size() ; i++) {
                tmplist.add(new ListTreeAdapter.ListItem().setShowtitle(optionlist.get(i).getValue("jianchaxiangmusiji")).setLevel(2));
            }
            return tmplist;
        }

        @Override
        public void pressenent(View v, int position) {

        }
    }
}
