package com.shark.app.business.singleactivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Message;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.staticlib.StaticAppInfo;
import com.leelay.freshlayout.verticalre.VRefreshLayout;
import com.shark.app.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityEnterpriseList extends FrameActivity implements SearchView.OnQueryTextListener,SearchView.OnSuggestionListener {
    private static final String[] COLUMNS = { BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, };
    private SearchView searchView;
    private MenuItem seachmenuItem;
    private EditText searchEditText;
    private RecyclerView recycler_view;
    private VRefreshLayout mRefreshLayout;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return R.menu.enterprisemenu_menu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpriselist);

        if (StaticAppInfo.getInstance().getMode()== StaticAppInfo.StaticMode.test) {

        }else{
            if(checkNet()){
//            httpPost();
            }
        }

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(new EnterPriseAdapter(this));
        mRefreshLayout = (VRefreshLayout) findViewById(R.id.vrefresh);
        mRefreshLayout.addOnRefreshListener(new VRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
            System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(searchEditText.isShown()){
            searchView.onActionViewCollapsed();
            searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        }else{
            super.onBackPressed();
        }
    }
    QuerySuggestionsAdapter mSuggestionsAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuid()==-1||getMenuid()==0?R.menu.menu_:getMenuid(), menu);
        //在菜单中找到对应控件的item
        seachmenuItem = menu.findItem(R.id.action_seach);
        searchView = (SearchView) MenuItemCompat.getActionView(seachmenuItem);
        searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchView.setQueryHint("输入企业名称");
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);

        if (mSuggestionsAdapter == null) {
            MatrixCursor cursor = new MatrixCursor(COLUMNS);
            cursor.addRow(new String[] { "1", "Murica" });
            cursor.addRow(new String[] { "2", "Canada" });
            cursor.addRow(new String[] { "3", "Denmark" });
            mSuggestionsAdapter = new QuerySuggestionsAdapter(this, cursor);
        }
        searchView.setSuggestionsAdapter(mSuggestionsAdapter);
//        searchView.setSubmitButtonEnabled(false);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("查询企业："+query);
        searchView.onActionViewCollapsed();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
        String query = c.getString(c.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
        searchEditText.setText(query);
        return true;
    }
    class EnterPriseAdapter extends RecyclerView.Adapter<EnterPriseAdapter.SingelViewHolder>{
        public Activity mactivity;
        public Fragment mfragment;
        public android.support.v4.app.Fragment msupportfragment;

        public EnterPriseAdapter(Fragment mfragment) {
            this.mfragment = mfragment;
        }

        public EnterPriseAdapter(android.support.v4.app.Fragment msupportfragment) {
            this.msupportfragment = msupportfragment;
        }

        public EnterPriseAdapter(Activity mactivity) {
            this.mactivity = mactivity;
        }

        @Override
        public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(getBaseContext()).inflate(R.layout.item_enterpriselist,parent,false);
            AutoUtils.autoSize(itemView);
            return new SingelViewHolder(itemView);
        }



        @Override
        public void onBindViewHolder(SingelViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
            public SingelViewHolder(View itemView) {
                super(itemView);

            }

            @Override
            public void onClick(View v) {
//                System.out.println("点击项目"+getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }

    class QuerySuggestionsAdapter extends CursorAdapter {

        public QuerySuggestionsAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv = (TextView) view;
            final int textIndex = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
            tv.setText(cursor.getString(textIndex));
        }

    }

}
