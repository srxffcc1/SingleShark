package com.shark.app.business.view.xingzhengzhifa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
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
public class ActivityCheckOptionSelect extends FrameActivity {
    @BindView(R.id.testlist)
    ListView mTestlist;
    private List<ListTreeAdapter.ListItem> alllist;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }
    @Override
    public boolean needActionBar() {
        return false;
    }
    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkoption_select_list);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        alllist = new ArrayList<>();
        List<DateBase_Entity> optionlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().query("select distinct jianchaxiangmusanji from `Entity_LibraryLawDependence`",new Entity_LibraryLawDependence()));
        for (int i = 0; i <optionlist.size() ; i++) {
            alllist.add(new ListTreeAdapter.ListItem().setShowtitle("检查项分类:"+optionlist.get(i).getValue("检查项目三级")).setLevel(1).setIncludeobj(optionlist.get(i)));
        }
        AppTreeListAdapter adapter=new AppTreeListAdapter(this, alllist,3);
        mTestlist.setAdapter(adapter);


    }
    public String  getResult(){
        String result="";
        for (int i = 0; i <alllist.size() ; i++) {
            if(alllist.get(i).isselect()){
                DateBase_Entity entity= (DateBase_Entity) alllist.get(i).getIncludeobj();
                String three2f=entity.getValue("检查项目三级")+"-"+entity.getValue("检查项目四级");
                result=result+three2f.replace("检查项分类:","")+",";
            }

        }
        return result.substring(0,result.length()-1);
    }
    @Override
    public void buttonSubmit(View view) {
        setResult(1002,new Intent().putExtra("result",getResult().replace(",","\n")));
        finish();
    }

    @Override
    public void buttonCancel(View view) {
        finish();
    }

    @Override
    public void handleMessage(Message msg) {

    }

    class AppTreeListAdapter extends ListTreeAdapter{

        /**
         * @param context         context
         * @param alllist         一层目录
         * @param whillclicklevel 需要进行跳转反馈的层级
         */
        public AppTreeListAdapter(Activity context, List<ListItem> alllist, int whillclicklevel) {
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
        public ListViewHolder getHolder() {
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
        public List<ListItem> loaddatafromserverImp(View v, int position,int level) {
            if(level==2){
                return null;
            }
            List<DateBase_Entity> optionlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_LibraryLawDependence().putlogic2value("jianchaxiangmusanji","=",alllist.get(position).getShowtitle().replace("检查项分类:",""))));
            List<ListItem> tmplist=new ArrayList<>();
            for (int i = 0; i <optionlist.size() ; i++) {
                tmplist.add(new ListItem().setShowtitle(optionlist.get(i).getValue("jianchaxiangmusiji")).setLevel(2).setIncludeobj(optionlist.get(i)));
            }
            return tmplist;
        }

        @Override
        public void pressenent(View v, int position) {

        }
    }
}
