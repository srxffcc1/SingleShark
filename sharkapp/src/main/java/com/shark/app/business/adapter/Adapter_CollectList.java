package com.shark.app.business.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.businessframehelp.staticlib.StaticAppInfo;
import com.businessframehelp.widget.AutoCheckBox;
import com.businessframehelp.widget.OnCheckedChangeListener;
import com.kymjs.common.ImageLoader;
import com.shark.app.R;
import com.shark.app.business.entity.ExpandMap;
import com.shark.app.business.statich.ActionHome;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by King6rf on 2017/6/8.
 */

public class Adapter_CollectList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<ExpandMap> menuListData;
    private int mCurrentItemId = 0;
    int orientation = 1;//0为横1为竖直
    private android.support.v4.util.ArrayMap<Integer, Bitmap> bitmapmap = new android.support.v4.util.ArrayMap<Integer, Bitmap>();
    private boolean cancheck = false;
    public android.support.v4.util.ArrayMap<Integer, Boolean> checkmap = new android.support.v4.util.ArrayMap<Integer, Boolean>();

    public Adapter_CollectList(Activity context, List<ExpandMap> maplist) {
        this.context = context;
        menuListData = maplist;

    }

    public void setCancheck(boolean cancheck) {
        this.cancheck = cancheck;
    }

    public void clear() {
        checkmap.clear();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case 0:
                            return 4;
                        case 1:
                            return 1;
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.item_activity_collect_parent, parent, false);
                holder = new ViewHolderHead(view);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.item_activity_collect, parent, false);
                holder = new ViewHolderContent(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final View itemView = holder.itemView;
        boolean isTwoStaggered = true;
        if (isTwoStaggered) {
            switch (getItemViewType(position)) {
                case 0:
                    ((ViewHolderHead) holder).text.setText(menuListData.get(position).getName());
                    break;
                case 1:
                    final ImageView tmp = ((ViewHolderContent) holder).logimage;
                    final AutoCheckBox checklogo = ((ViewHolderContent) holder).check;
                    final ImageView deletemark = ((ViewHolderContent) holder).deletemark;
                    ImageLoader.getInstance(7, ImageLoader.Type.LIFO).loadImage(menuListData.get(position).getEntityvalue().getFileallpath(),tmp);
                    ((ViewHolderContent) holder).logimage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (!cancheck) {
                                menuListData.get(position).getEntityvalue().open();
                            } else {
                                checklogo.performClick();
                            }
                        }
                    });
                    ((ViewHolderContent) holder).logo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (!cancheck) {
                                //do rename
                            } else {
                                checklogo.performClick();
                            }

                        }
                    });
                    ((ViewHolderContent) holder).logimage.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            cancheck = !cancheck;
                            checkBoxFresh();
                            notifyDataSetChanged();
                            return true;
                        }
                    });
                    ((ViewHolderContent) holder).check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(View buttonView, boolean isChecked) {
                            System.out.println("checkmap:put" + position + ":" + isChecked);
                            checkmap.put(position, isChecked);
                        }
                    });

                    deletemark.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            menuListData.get(position).getEntityvalue().delete();
                            menuListData.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    if (cancheck) {
                        ((ViewHolderContent) holder).check.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderContent) holder).check.setVisibility(View.GONE);
                    }
                    if (checkmap.get(position) != null && checkmap.get(position)) {
                        ((ViewHolderContent) holder).check.setChecked(true);
                    } else {
                        ((ViewHolderContent) holder).check.setChecked(false);
                    }
                    switch (menuListData.get(position).getEntityvalue().getFileType()) {
                        case 1:
                            ((ViewHolderContent) holder).fileflag.setImageResource(R.mipmap.collect_voice);
                            break;
                        case 2:
                            ((ViewHolderContent) holder).fileflag.setImageResource(R.mipmap.collect_video);
                            break;
                        case 3:
                            ((ViewHolderContent) holder).fileflag.setImageResource(R.mipmap.collect_camera);
                            break;
                        default:
                            break;
                    }
                    break;
            }
        } else {

        }

    }

    public List<File> getFileSelectList() {
        List<File> fileselect = new ArrayList<File>();
        for (Map.Entry<Integer, Boolean> entry : checkmap.entrySet()) {
            if (entry.getValue()) {
                fileselect.add(menuListData.get(entry.getKey()).getEntityvalue().getOrg());
            }
        }
        return fileselect;
    }

    public void checkBoxFresh() {
        if(cancheck){
            StaticAppInfo.getInstance().getAppLicationContext().sendBroadcast(new Intent(ActionHome.collectundershow));
        }else{
            StaticAppInfo.getInstance().getAppLicationContext().sendBroadcast(new Intent(ActionHome.collectunderdismiss));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return menuListData.get(position).getViewtype();
    }


    @Override
    public int getItemCount() {
        return menuListData.size();
    }

    public static class ViewHolderHead extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolderHead(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            text = ((TextView) itemView.findViewById(R.id.inspectOption));
            itemView.setPadding(5,5,5,5);
            AutoUtils.autoSize(itemView);
        }
    }

    public static class ViewHolderContent extends RecyclerView.ViewHolder {
        TextView logo;
        ImageView logimage;
        ImageView fileflag;
        AutoCheckBox check;
        ImageView deletemark;

        public ViewHolderContent(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 180));
            itemView.setPadding(5,5,5,5);
            logo = (TextView) (itemView.findViewById(R.id.menuImage));
            logimage = (ImageView) itemView.findViewById(R.id.needfiximage);
            check = (AutoCheckBox) itemView.findViewById(R.id.checkBox1);
            fileflag = (ImageView) itemView.findViewById(R.id.fileflag);
            deletemark = (ImageView) itemView.findViewById(R.id.deletemark);
            RelativeLayout.LayoutParams pra = new RelativeLayout.LayoutParams(60, 60);
            pra.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            pra.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            check.setLayoutParams(pra);
            AutoUtils.autoSize(itemView);
        }
    }

}
