package com.shark.app.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.businessframehelp.adapter.ListTreeAdapter;
import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ListTestActivity extends FrameActivity {
    @BindView(R.id.testlist)
    ListView mTestlist;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_list);
        List<ListTreeAdapter.ListItem> alllist=new ArrayList<>();
        alllist.add(new ListTreeAdapter.ListItem().setLevel(1));
        alllist.add(new ListTreeAdapter.ListItem().setLevel(2));
        alllist.add(new ListTreeAdapter.ListItem().setLevel(2));
        alllist.add(new ListTreeAdapter.ListItem().setLevel(1));
        alllist.add(new ListTreeAdapter.ListItem().setLevel(2));
        alllist.add(new ListTreeAdapter.ListItem().setLevel(3));
        TestListAdapter adapter=new TestListAdapter(this,alllist,3);
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
            return 3;
        }

        @Override
        public int getViewResId(int level) {
            switch(level){
                case 1:
                    return R.layout.test_listitem1;
                case 2:
                    return R.layout.test_listitem2;
                case 3:
                    return R.layout.test_listitem3;
            }
            return 0;
        }

        @Override
        public ListTreeAdapter.ListViewHolder getHolder() {
            return new ListViewHolder() {
                @Override
                public void bindView(View view,ListItem entity) {

                }

                @Override
                public void canvasView(View view,ListItem entity) {

                }
            };
        }

        @Override
        public List<ListTreeAdapter.ListItem> loaddatafromserverImp(View v, int position) {
            return null;
        }

        @Override
        public void pressenent(View v, int position) {

        }
    }
}
