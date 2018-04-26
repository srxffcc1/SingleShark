package com.shark.pdfedit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shark.pdfedit.R;
import com.shark.pdfedit.activity.SignAtureActivity;
import com.shark.pdfedit.fragment.BookFragment;
import com.shark.pdfedit.statich.BookStatic;
import com.shark.pdfedit.utils.CallBack;
import com.shark.pdfedit.utils.DateTimePickDialog;
import com.shark.pdfedit.utils.TextColorUtil;
import com.shark.pdfedit.utils.VoiceUtil;
import com.shark.pdfedit.widget.AutoCheckBox;
import com.shark.pdfedit.widget.AutoCheckGroup;
import com.shark.pdfedit.widget.AutoDialogBuilder;
import com.shark.pdfedit.widget.OnCheckedChangeListener;
import com.shark.pdfedit.widget.PassLinearLayout;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitydemo.Entity_Demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookDetailBuilder {
    private Activity activity;
    private Base_Entity detailMapData;
    private LinearLayout content;
    private BookFragment bookFragment;
    private boolean editState;
    private boolean isshow = true;
    private Map<String, EditText> viewmap = new HashMap<String, EditText>();
    private Map<String, ImageView> imagemap = new HashMap<String, ImageView>();
    private Map<String, EditText> imagemapcopyed = new HashMap<String, EditText>();

    public BookDetailBuilder(Activity context,
                             Base_Entity detailMapData, LinearLayout content, BookFragment fragment) {
        super();
        this.activity = context;
        this.detailMapData = detailMapData;
        this.content = content;
        this.bookFragment=fragment;
    }
    public void put(String key, String value) {
        viewmap.get(key).setText(value.trim());
    }

    public Map<String, EditText> getViewmap() {
        return viewmap;
    }
    public BookDetailBuilder initView() {
        viewmap.clear();
        content.removeAllViews();
        for (int i = 0; i < getCount(); i++) {
            View add = getView(i, content);
            if (add != null) {
                if (add.getVisibility() == View.VISIBLE) {
                    content.addView(add);
                }

            }
        }
        return this;
    }
    public void setBitmap(String key, Bitmap bitmap){
//        Uri uri = Uri.fromFile(new File(bitmappath));
        ImageView imageView=imagemap.get(key);
        if(imageView!=null){
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
            TextView textView=imagemapcopyed.get(key);
            if(textView!=null){
                textView.setVisibility(View.GONE);
            }
        }
    }
    public void setBitmap(String key, String bitmappath){
        Uri uri = Uri.fromFile(new File(bitmappath));
        ImageView imageView=imagemap.get(key);
        if(imageView!=null){
            imageView.setImageURI(uri);
            imageView.setVisibility(View.VISIBLE);
            TextView textView=imagemapcopyed.get(key);
            if(textView!=null){
                textView.setVisibility(View.GONE);
            }
        }
    }
    public Base_Entity getResult() {
        detailMapData.clear();
        for (int i = 0; i < detailMapData.size(); i++) {
            EditText textvalue = viewmap.get(detailMapData.getField(i));
            if (textvalue != null) {
//                System.out.println(textvalue.getText().toString());
                detailMapData.put(i, textvalue.getText().toString());
//				
            } else {
                detailMapData.put(i, "");
            }
        }
        return detailMapData;
    }

    public BookDetailBuilder setFocus(int position) {
        (viewmap.get(detailMapData.getField(position))).setFocusable(true);
        (viewmap.get(detailMapData.getField(position))).setFocusableInTouchMode(true);
        (viewmap.get(detailMapData.getField(position))).requestFocus();
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        return this;
    }

    public BookDetailBuilder build() {
        viewmap.clear();
        content.removeAllViews();
        for (int i = 0; i < getCount(); i++) {
            View add = getView(i, content);
            if (add != null) {
                if (add.getVisibility() == View.VISIBLE) {
                    content.addView(add);
                    content.addView(getSpace(activity));
                }

            }
        }
        return this;
    }

    public BookDetailBuilder setEditState(boolean editState) {
        if (viewmap.size() > 0) {
            detailMapData = getResult();
        }

        this.editState = editState;
        return this;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return detailMapData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return detailMapData.getFieldChinese(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public String getListTitle(String org) {
        String result = "";
        String[] orgarray = org.split("_");
        result = orgarray[0].trim().replace("list", "");
        Pattern pattern = Pattern.compile("(.*?)lim(.*)");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = matcher.group(1) + "列表";
        }

        return result;
    }

    public View getView(final int position, ViewGroup parent) {
        View convertView = null;
        final String fieldtext = getItem(position).toString();
        if (fieldtext.matches("check(.*)")) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_book_check, null);
            final AutoCheckGroup autogroup = (AutoCheckGroup) convertView.findViewById(R.id.checkGroup);
            String[] fieldtexts = fieldtext.replace("check", "").split("2");
            for (int i = 0; i < fieldtexts.length; i++) {
                PassLinearLayout passparent = new PassLinearLayout(activity);
                passparent.setOrientation(LinearLayout.HORIZONTAL);
                passparent.setPadding(0, 15, 0, 15);
                passparent.setClickable(true);
                passparent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                TextView text = new TextView(activity);
                text.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
                text.setText(fieldtexts[i].toString().trim());
                AutoCheckBox box = new AutoCheckBox(activity);
                box.setPadding(0, 0, 7, 0);
                box.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                box.setImageResource(R.drawable.toggle);
                box.setClickable(true);
                passparent.addView(box);
                passparent.addView(text);
                autogroup.addView(passparent);
                if (editState) {
//					box.setEnabled(true);
                } else {
                    autogroup.setEnabled(false);
                    passparent.setClickable(false);
                    box.setClickable(false);
                }
            }
            autogroup.link();
            final EditText valueedit2 = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            autogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        valueedit2.setText((autogroup.getCheckIndex() + 1) + "");
                    }


                }
            });
            autogroup.setCheck(0);
            String sso = ((detailMapData.getValue(position).equals(" ") || detailMapData.getValue(position).equals("")) ? 1 : Integer.parseInt(detailMapData.getValue(position))) + "";
            valueedit2.setText(sso);
            viewmap.put(detailMapData.getField(position), valueedit2);
        } else if (fieldtext.matches("list(.*)")) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_book_tip, null);
            String titiletextstring = getListTitle(fieldtext);
            final String fieldtexttmp = fieldtext;
            final TextView listtitle = (TextView) convertView.findViewById(R.id.listtitle);
            final Button addtip = (Button) convertView.findViewById(R.id.addtip);
            final LinearLayout listcontent = (LinearLayout) convertView.findViewById(R.id.needaddtip);
            final TextView showhide = (TextView) convertView.findViewById(R.id.hidetip);
            listtitle.setText(titiletextstring);
            String tmpvalue = detailMapData.getValue(position);
            String tmpfield = fieldtext;
            List<Base_Entity> addlist = string2EntityList(tmpfield, tmpvalue);
            final BookTipBuilder adapter_bookAddItem = new BookTipBuilder(activity, addlist, listcontent);
            adapter_bookAddItem.build();
            final EditText valueedit2 = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            showhide.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    isshow = !isshow;
                    if (isshow) {
                        showhide.setText("隐藏↑");
                        listcontent.setVisibility(View.VISIBLE);
                    } else {
                        showhide.setText("显示↓");
                        listcontent.setVisibility(View.GONE);
                    }

                }
            });
            if (!editState) {
                showhide.setClickable(false);
                addtip.setVisibility(View.GONE);
            } else {

                addtip.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String org = fieldtexttmp;
                        String result = "";
                        String[] orgarray = org.split("_");
                        result = orgarray[0].trim().replace("list", "");
                        Pattern pattern = Pattern.compile("(.*?)lim(.*)");
                        Matcher matcher = pattern.matcher(result);
                        int limit = 0;
                        if (matcher.find()) {
                            limit = Integer.parseInt(matcher.group(2));
                        }
                        if (adapter_bookAddItem.getCount() > limit) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String org = fieldtexttmp;
                                    String result = "";
                                    String[] orgarray = org.split("_");

                                    result = orgarray[0].trim().replace("list", "");
                                    Pattern pattern = Pattern.compile("(.*?)lim(.*)");
                                    Matcher matcher = pattern.matcher(result);
                                    int limit = 0;
                                    if (matcher.find()) {
                                        limit = Integer.parseInt(matcher.group(2));
                                    }
                                    Toast.makeText(activity, "超过" + limit + "个不可继续添加", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            showDialog_AddBookItem(activity, bookFragment,fieldtexttmp, new CallBack() {
                                @Override
                                public void back(Object resultlist) {
                                    final Base_Entity result = (Base_Entity) resultlist;
                                    adapter_bookAddItem.addEntity(result);
                                    String newtext = adapter_bookAddItem.getResult();
                                    valueedit2.setText(newtext);
                                }
                            });
                        }

                    }
                });
            }

            ((TextView) convertView.findViewById(R.id.bookcontentName)).setText(fieldtext);
            final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            viewmap.put(detailMapData.getField(position), valueedit);
            valueedit.setText(detailMapData.getValue(position));
        } else if(fieldtext.matches("(.*)签名(.*)")||fieldtext.matches("(.*)签字(.*)")){
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_book_content_sign, null);
            ((TextView) convertView.findViewById(R.id.bookcontentName)).setText(fieldtext);
            final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            final ImageView signimage= (ImageView) convertView.findViewById(R.id.signimage);
            viewmap.put(detailMapData.getField(position), valueedit);
            valueedit.setText(detailMapData.getValue(position));
            ImageButton imageButton= (ImageButton) convertView.findViewById(R.id.signstart);
            imagemapcopyed.put(detailMapData.getField(position),valueedit);
            imagemap.put(detailMapData.getField(position),signimage);
            imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookFragment.startActivityForResult(new Intent(activity, SignAtureActivity.class).putExtra("startkey",detailMapData.getField(position)),1200);
                }
            });
        }else if(fieldtext.matches("(.*)情况(.*)")||fieldtext.matches("(.*)内容(.*)")){
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_book_content_voice, null);
            ((TextView) convertView.findViewById(R.id.bookcontentName)).setText(fieldtext);
            final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            viewmap.put(detailMapData.getField(position), valueedit);
            valueedit.setText(detailMapData.getValue(position));
            ImageButton imageButton= (ImageButton) convertView.findViewById(R.id.voicestart);
            imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    VoiceUtil.startVoiceDialog(activity,valueedit);
                }
            });
        }else {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_book_content, null);
            ((TextView) convertView.findViewById(R.id.bookcontentName)).setText(fieldtext);
            final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            viewmap.put(detailMapData.getField(position), valueedit);
            valueedit.setText(detailMapData.getValue(position));
        }
        final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
        if (fieldtext.matches("(.*)null(.*)")) {
            convertView.setVisibility(View.GONE);
        } else if (fieldtext.matches("(.*)时间(.*)") || fieldtext.matches("(.*)日期(.*)")) {
            valueedit.setFocusable(false);
            valueedit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DateTimePickDialog(activity, "").show(valueedit);

                }
            });
        } else if (fieldtext.matches("(.*)数量(.*)") || fieldtext.matches("(.*)序号(.*)") || fieldtext.matches("(.*)号码(.*)") || fieldtext.matches("(.*)电话(.*)") || fieldtext.matches("(.*)邮编(.*)")) {
            valueedit.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {

        }


        if (editState) {
//			valueedit.setEnabled(true);
        } else {
            valueedit.setOnClickListener(null);
            valueedit.setOnTouchListener(null);
            valueedit.setClickable(false);
            valueedit.setKeyListener(null);
        }
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(layoutParams);
        TextColorUtil.fixTextColor(convertView);
        return convertView;
    }

    public List<Base_Entity> string2EntityList(String orgfieldstring, String orgliststring) {
        List<Base_Entity> result = new ArrayList<Base_Entity>();
        String[] listitem = null;


        if (orgliststring != null && orgliststring.matches("(.*)@(.*)")) {
            listitem = orgliststring.split("@");
            if (listitem != null) {
                for (int i = 0; i < listitem.length; i++) {
                    Base_Entity tmp = string2Entity(orgfieldstring, listitem[i]);
                    if(tmp!=null){
                        result.add(tmp);
                    }

                }
            } else {
            }
        } else {
        }
        return result;

    }

    public Base_Entity string2Entity(String orgfieldstring, String orgliststring) {
        Base_Entity itementity = null;
        try {
            itementity = new Entity_Demo();
            String org = orgfieldstring;
            String result = "";
            String[] orgarray = org.split("_");
            String[] orgarray2 = orgarray[1].split("2");
            String[] valuestringarray = orgliststring.split("#");

            for (int i = 0; i < orgarray2.length; i++) {
                try {
                    itementity.add(orgarray2[i].trim(), valuestringarray[i].trim());
                } catch (ArrayIndexOutOfBoundsException e) {

                    e.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println(orgfieldstring);
            System.out.println(orgliststring);
            e.printStackTrace();
        }
        return itementity;
    }

    public static View showDialog_AddBookItem(final Activity activity, BookFragment bookFragment,Object entity, final CallBack back) {
        LinearLayout dialogview = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.dialog_add_item, null);
//        Util_MatchTip.initAllScreenText(dialogview);
        TextView dialogtitle = (TextView) dialogview.findViewById(R.id.dialogtitle);
        LinearLayout itemcontent = (LinearLayout) dialogview.findViewById(R.id.itemcontent);

        String org = (String) entity;
        String result = "";
        String[] orgarray = org.split("_");
        String[] orgarray2 = orgarray[1].split("2");
        result = orgarray[0].trim().replace("list", "");
        Pattern pattern = Pattern.compile("(.*?)lim(.*)");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = matcher.group(1) + "-->限制:" + matcher.group(2);
        }
        dialogtitle.setText(result);
        Base_Entity itementity = new Entity_Demo();
        for (int i = 0; i < orgarray2.length; i++) {
            itementity.add(orgarray2[i].trim(), "");
        }
        final BookDetailBuilder adapter = new BookDetailBuilder(activity, itementity, itemcontent,bookFragment);
        adapter.setEditState(true).build();
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LinearLayout.LayoutParams((int) (BookStatic.getInstance().getScreenWidth() / 1.1), (int) (BookStatic.getInstance().getScreenHeight() / 1.5)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Base_Entity resultentity = adapter.getResult();
                back.back(resultentity);

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }
    public static View getSpace(final Activity activity){
        View convertView = null;
        convertView = LayoutInflater.from(activity).inflate(R.layout.item_book_content_space, null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) (BookStatic.getInstance().getScreenHeight() /BookStatic.space));
        convertView.setLayoutParams(layoutParams);
        return convertView;
    }

}
