package com.businessframehelp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.businessframehelp.inter.IListTreeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KingGT80 on 2017/3/15.
 */
@Deprecated
abstract public class ListTreeAdapter extends BaseAdapter implements IListTreeAdapter{
    public Activity context;
    public List<ListItem> alllist = new ArrayList<>();//第一层进去的展示列表
    public List<ListItem> showlist = new ArrayList<>();//用户看到的展示列表
    public int whillclicklevel;//设置第几层为点击跳转
    private boolean isinitListView=false;
    /**
     *
     * @param context  context
     * @param alllist  一层目录
     * @param whillclicklevel  需要进行跳转反馈的层级
     */
    public ListTreeAdapter(Activity context, List<ListItem> alllist,int whillclicklevel) {
        this.context = context;
        this.alllist = alllist;//构造方法中传入的是第一层目录
        this.whillclicklevel = whillclicklevel;
        changeData();

    }
//    abstract public ListView getListView();
//    private void initListener(){
//        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //System.out.println("进入点击事件");
//                checktoshowchild(view,position);
//            }
//        });
//    }
    final public int getItemViewType(int position) {
        return showlist.get(position).getLevel()-1;
    }
    abstract public int getViewTypeCount();
        @Override
    final public int getCount() {
        return showlist.size();
    }

    @Override
    final public Object getItem(int position) {
        return showlist.get(position);
    }

    @Override
    final public long getItemId(int position) {
        return position;
    }

    @Override
    final public View getView(final int position, View convertView, ViewGroup parent) {
        if(!isinitListView){
            ((AbsListView) parent).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checktoshowchild(view,position);
                }
            });
            isinitListView=true;
        }
        ListViewHolder holder = null;
        ListItem entity = showlist.get(position);
        int level = entity.getLevel();

        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(getViewResId(level),parent,false);
            holder=getHolder();
            holder.level=level;
            holder.bindView(convertView,entity);
            convertView.setTag(holder);
        } else {
            holder= (ListViewHolder) convertView.getTag();
            if(holder.level==level){
                holder.bindView(convertView,entity);
            }else{
                convertView= LayoutInflater.from(context).inflate(getViewResId(level),parent,false);
                holder.level=level;
                holder.bindView(convertView,entity);
                convertView.setTag(holder);
            }

        }

        holder.canvasView(convertView,entity);
        return convertView;
    }
    public abstract ListViewHolder getHolder();

    public abstract class ListViewHolder {
        public int level;

        /**
         * 绑定view 给控件进行绑定
         * @param view
         * @param entity
         */
        public abstract void bindView(View view,ListItem entity);

        /**
         * 给这些控件进行赋值
         * @param view
         * @param entity
         */
        public abstract void canvasView(View view,ListItem entity);
//界面绑定
    }
    final public void checktoshowchild(View v, int position) {
//        //System.out.println("修改列表状态");
        int nowlevel = showlist.get(position).getLevel();
        boolean listhaschange=false;
        if (position < getCount() - 1) {
            if (nowlevel < showlist.get(position + 1).getLevel()) {//说明应该是收起操作
                listhaschange= hidechild(v, position);
            } else {//说明是展示操作
                listhaschange=showchild(v, position);
            }
        } else {//说明按到了底部了那就应该是展示操作了
            listhaschange= showchild(v, position);
        }
        if(listhaschange){
            changeData();//说明直接就有数据在大list里直接展示或者隐藏就行
        }else{
            if(nowlevel==whillclicklevel){
                pressenent(v,position);
            }else{
                if(position==getCount() - 1||nowlevel==showlist.get(position+1).getLevel()){//说明有下级且没有进行过载入 需要进行网络请求进行载入
                    loaddatafromserver(v,position,nowlevel);
                }
            }
        }

    }
    abstract public List<ListItem> loaddatafromserverImp(final View v, final int position,int level);
    final public void loaddatafromserver(final View v, final int position,final int level){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ListItem> tmplist=loaddatafromserverImp(v,showlist.get(position).getPostionorg(),level);
                if(tmplist!=null&&tmplist.size()>0){
                    for (int i = 0; i <tmplist.size() ; i++) {
                        tmplist.get(i).setIsshow(true);
                    }
                    alllist.addAll(showlist.get(position).getPostionorg()+1,tmplist);//将数据插入到完整list 再执行changedata操作；
                    changeData();
                }

            }
        }).start();


        //会按照网络请求规则去访问网络

    }
    final public boolean showchild(View v, int position) {
        boolean listhaschange=false;
        int nowlevel = showlist.get(position).getLevel();
        for (int i = showlist.get(position).getPostionorg()+1; i < alllist.size(); i++) {//从大list看是否已经在大list中加载过child了
            if(nowlevel<alllist.get(i).getLevel()){
                if(alllist.get(i).isshow()){
                    break;
                }else{
                    if(nowlevel+1==alllist.get(i).getLevel()){
                        listhaschange=true;
                        alllist.get(i).setIsshow(true);
                    }else{
                        break;
                    }

                }
            }else{
                break;
            }

        }
        return listhaschange;
    }

    final public boolean hidechild(View v, int position) {//将对菜单进行收起 做标记
        boolean listhaschange=false;
        int nowlevel = showlist.get(position).getLevel();
        for (int i = position+1; i < showlist.size(); i++) {
            if(nowlevel<showlist.get(i).getLevel()){
                if(!showlist.get(i).isshow()){
                    break;
                }else{
                    listhaschange=true;
                    alllist.get(showlist.get(i).getPostionorg()).setIsshow(false);
                }
            }else{
                break;
            }
        }
        return listhaschange;
    }

    final public void changeData() {
        showlist.clear();
        for (int i = 0; i <alllist.size() ; i++) {
            if(alllist.get(i).isshow()){
                showlist.add(alllist.get(i).setPostionorg(i));
            }else{
                alllist.get(i).setPostionorg(i);
            }
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                notifyDataSetChanged();
            }
        });
    }

    /**
     * 跳转逻辑
     * @param v
     * @param position
     */
    abstract public void pressenent(View v, int position);


    public static class ListItem {
        public int postionorg;
        public int level;
        public boolean isselect=false;
        public boolean isshow=true;
        public Object includeobj;
        public String showtitle;

        public boolean isselect() {
            return isselect;
        }

        public ListItem setIsselect(boolean isselect) {
            this.isselect = isselect;
            return this;
        }

        public int getLevel() {
            return level;
        }

        public int getPostionorg() {
            return postionorg;
        }

        public ListItem setPostionorg(int postionorg) {
            this.postionorg = postionorg;
            return this;
        }

        public ListItem setLevel(int level) {
            this.level = level;
            return  this;
        }

        public boolean isshow() {
            return isshow;
        }

        public ListItem setIsshow(boolean isshow) {
            this.isshow = isshow;
            return  this;
        }

        public Object getIncludeobj() {
            return includeobj;
        }

        public ListItem setIncludeobj(Object includeobj) {
            this.includeobj = includeobj;
            return  this;
        }

        public String getShowtitle() {
            return showtitle;
        }

        public ListItem setShowtitle(String showtitle) {
            this.showtitle = showtitle;
            return  this;
        }
    }
}
