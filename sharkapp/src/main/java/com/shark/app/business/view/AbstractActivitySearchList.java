package com.shark.app.business.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessframehelp.adapter.QuerySuggestionsAdapter;
import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.leelay.freshlayout.verticalre.VRefreshLayout;
import com.shark.app.R;
import com.shark.app.business.interfaces.ISearch;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/5/25.
 * 带查找的activity
 */

abstract public class AbstractActivitySearchList extends FrameActivity implements View.OnClickListener,ISearch,VRefreshLayout.OnRefreshListener,SearchView.OnQueryTextListener,SearchView.OnSuggestionListener {
    private static final String[] COLUMNS = { BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, };
    public SearchView searchView;
    public MenuItem seachmenuItem;
    public EditText searchEditText;
    public RecyclerView recycler_view;
    public VRefreshLayout mRefreshLayout;
    public RecyclerView.Adapter adapter;
    public TextView patetext;
    public TextView totaltext;
    public String queryold="";
    private ImageView mGoButton;
    public LinearLayoutManager manager;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
     public int getMenuid() {
        return R.menu.mutilsearch_menu;
    }

    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.include_pageturnfreshlist);
        patetext= (TextView) findViewById(R.id.pagetext);
        totaltext= (TextView) findViewById(R.id.totaltext);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler_view.setLayoutManager(manager);
        adapter = getRecycleAdapter();
        if(adapter ==null){
//            //System.out.println("测试列表");
            adapter =new TmpAdapter(this);

        }
        recycler_view.setAdapter(adapter);
        mRefreshLayout = (VRefreshLayout) findViewById(R.id.vrefresh);
        findViewById(R.id.previous).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        mRefreshLayout.addOnRefreshListener(this);
        onCreateImp(savedInstanceState);


    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
            clickMultiSearch();
        }

        return super.onOptionsItemSelected(item);
    }
    abstract public void toNext();
    abstract public void toPrevious();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
                toPrevious();
                break;
            case R.id.next:
                toNext();
                break;
        }
    }

    @Override
    final public void onBackPressed() {
        if(searchEditText.isShown()){
            searchView.onActionViewCollapsed();
            searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            Toast.makeText(this,"关闭搜索页面",Toast.LENGTH_SHORT).show();
        }else{
            super.onBackPressed();
        }
    }
    QuerySuggestionsAdapter mSuggestionsAdapter;
    @Override
    final public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuid()==-1||getMenuid()==0?R.menu.menu_:getMenuid(), menu);
        //在菜单中找到对应控件的item
        seachmenuItem = menu.findItem(R.id.action_seach);
        searchView = (SearchView) MenuItemCompat.getActionView(seachmenuItem);
        searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mGoButton = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn);
        searchView.setQueryHint(getSearchHint());
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        MatrixCursor cursor = getSuggestionCursor();
        if (mSuggestionsAdapter == null) {
            if(cursor==null){
                cursor=new MatrixCursor(COLUMNS);
            }
            mSuggestionsAdapter = new QuerySuggestionsAdapter(this, cursor);
        }
        searchView.setSuggestionsAdapter(mSuggestionsAdapter);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText(queryold);
            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(searchEditText.isShown()){
                    queryold=searchEditText.getText().toString();
                    toSearchResult(queryold);
                    searchView.onActionViewCollapsed();
                    searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
//        //System.out.println("查询企业："+query);
        searchView.onActionViewCollapsed();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        queryold=query;
        toSearchResult(query);
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


    class TmpAdapter extends RecyclerView.Adapter<TmpAdapter.SingelViewHolder>{
        public Activity mactivity;
        public android.app.Fragment mfragment;
        public android.support.v4.app.Fragment msupportfragment;

        public TmpAdapter(Fragment mfragment) {
            this.mfragment = mfragment;
        }

        public TmpAdapter(android.support.v4.app.Fragment msupportfragment) {
            this.msupportfragment = msupportfragment;
        }

        public TmpAdapter(Activity mactivity) {
            this.mactivity = mactivity;
        }

        @Override
        public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_enterpriselist,parent,false);
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
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(),ActivityEnterpriseMesh.class));
//                //System.out.println("点击项目"+getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }

}
