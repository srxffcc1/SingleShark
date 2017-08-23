package com.shark.pdfedit.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ay.framework.core.pojo.BasePojo;
import com.github.barteksc.pdfviewer.PDFView;
import com.shark.pdfedit.R;
import com.shark.pdfedit.adapter.BookDetailBuilder;
import com.shark.pdfedit.utils.HttpUrlConnectUtil;
import com.shark.pdfedit.utils.PdfPrintHelp;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.pdflink.IPdfBack;
import com.wisdomregulation.shark.PdfFactory2017;
import com.wisdomregulation.staticlib.Static_BookLib;
import com.wisdomregulation.utils.ConvertPrint2014;
import com.wisdomregulation.utils.ConvertPrint2017;
import com.wisdomregulation.utils.ConvertPrintkm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by King6rf on 2017/8/20.
 * 参考sample的使用方法 com.shark.pdfedit.sample.Sample
 */

public class BookFragment extends Fragment implements View.OnClickListener{
    public static final int TYPE_ADD = 0;
    public static final int TYPE_UPDATE = 1;
    public static final int TYPE_SEE = 2;

    public static final int FLAG_2017 = 0;
    public static final int FLAG_2014 = 1;
    public static final int Flag_KM = 2;


    private HashMap<String,Object> datamap =  new HashMap();
    private HashMap<String,Object> othermap= new HashMap();//外部进来的map用于提供额外上传依据
    private String url;
    private String bookaction;
    private int mtype;
    private String bookname;
    private BasePojo mbasepojo;
    private Base_Entity mbasebok;
    private int mflag;
    private String mcookie;
    private BookDetailBuilder bookDetailBuilder;
    private String pdfpath;
    private PDFView pdfview;
    private Handler handler=new Handler();
    private LinearLayout pdfcontent;
    private String mip;
    private String uuid;


    /**
     * @param flag      2017 2014 km
     * @param webobject pc端直接流过来的实体
     * @param type      ADD UPDATE SEE ->BookFragment.ADD
     * @param ip        ip地址
     * @param cookie    cookie
     * @return
     */
    public static BookFragment getInstance(int flag, BasePojo webobject, int type, String ip, String cookie) {
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("flag", flag);
        bundle.putSerializable("ip", ip);
        bundle.putSerializable("webobject", webobject);
        bundle.putSerializable("type", type);
        bundle.putSerializable("cookie", cookie);
        bookFragment.setArguments(bundle);
        return bookFragment;
    }

    /**
     * @param flag       2017 2014 km
     * @param bookentity 本地book实体
     * @param type       ADD UPDATE SEE ->BookFragment.ADD
     * @param ip         ip地址
     * @param cookie     cookie
     * @return
     */
    public static BookFragment getInstance(int flag, Base_Entity bookentity, int type, String ip, String cookie) {
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ip", ip);
        bundle.putSerializable("flag", flag);
        bundle.putSerializable("type", type);
        bundle.putSerializable("bookentity", bookentity);
        bundle.putSerializable("cookie", cookie);
        bookFragment.setArguments(bundle);
        return bookFragment;
    }


    @Deprecated
    public static BookFragment getInstance(Base_Entity bookentity) {
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ip", "");
        bundle.putSerializable("flag", FLAG_2017);
        bundle.putSerializable("type", TYPE_ADD);
        bundle.putSerializable("bookentity", bookentity);
        bundle.putSerializable("cookie", "");
        bookFragment.setArguments(bundle);
        return bookFragment;
    }

    @Deprecated
    public static BookFragment getInstance(Base_Entity bookentity,int type) {
        BookFragment bookFragment = new BookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ip", "");
        bundle.putSerializable("flag", FLAG_2017);
        bundle.putSerializable("type", type);
        bundle.putSerializable("bookentity", bookentity);
        bundle.putSerializable("cookie", "");
        bookFragment.setArguments(bundle);
        return bookFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle initbundle = getArguments();
        mip= (String) initbundle.getSerializable("ip");
        mflag = (int) initbundle.getSerializable("flag");
        mbasepojo = (BasePojo) initbundle.getSerializable("webobject");
        mbasebok = (Base_Entity) initbundle.getSerializable("bookentity");
        mtype = (int) initbundle.getSerializable("type");
        mcookie = (String) initbundle.getSerializable("cookie");
        LinearLayout bookcontent = (LinearLayout) view.findViewById(R.id.bookcontent);
        TextView title= (TextView) view.findViewById(R.id.booktitlename);
        Button cancel= (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        Button identify= (Button) view.findViewById(R.id.identify);
        identify.setOnClickListener(this);
        ImageView printer= (ImageView) view.findViewById(R.id.printer);
        printer.setOnClickListener(this);
        ImageView printer2= (ImageView) view.findViewById(R.id.printer2);
        printer2.setOnClickListener(this);
        ImageView see= (ImageView) view.findViewById(R.id.see);
        see.setOnClickListener(this);
        ImageView exit= (ImageView) view.findViewById(R.id.exitpdf);
        exit.setOnClickListener(this);
        LinearLayout lastline= (LinearLayout) view.findViewById(R.id.lastline);
        pdfcontent = (LinearLayout) view.findViewById(R.id.pdfcontent);
        pdfview = (PDFView) view.findViewById(R.id.pdfview);
        pdfcontent.setVisibility(View.GONE);
        if(mtype==2){
            lastline.setVisibility(View.GONE);
        }
        if (mbasebok == null) {
            try {
                switch (mflag) {
                    case 0://2017
                        mbasebok = ConvertPrint2017.getInstance().webobject2bookentity(mbasepojo);
                        bookname= Static_BookLib.BOOKNAMELIST2017[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book_2017_",""))];
                        bookaction=Static_BookLib.WEBBOOKNAME2017[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book_2017_",""))];
                        bookaction=Static_BookLib.changeHeadToLowCase(bookaction);
                        break;
                    case 1://2014
                        mbasebok = ConvertPrint2014.getInstance().webobject2bookentity(mbasepojo);
                        bookname=Static_BookLib.BOOKNAMELIST[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book",""))];
                        bookaction=Static_BookLib.WEBBOOKNAME[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book",""))];
                        bookaction=Static_BookLib.changeHeadToLowCase(bookaction);
                        break;
                    case 2://昆明
                        mbasebok = ConvertPrintkm.getInstance().webobject2bookentity(mbasepojo);
                        bookname=Static_BookLib.BOOKNAMELIST[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book",""))];
                        bookaction=Static_BookLib.WEBBOOKNAME[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book",""))];
                        bookaction=Static_BookLib.changeHeadToLowCase(bookaction);
                        break;
                    default:
                        mbasebok = ConvertPrint2017.getInstance().webobject2bookentity(mbasepojo);
                        bookname= Static_BookLib.BOOKNAMELIST2017[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book_2017_",""))];
                        bookaction=Static_BookLib.WEBBOOKNAME2017[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book_2017_",""))];
                        bookaction=Static_BookLib.changeHeadToLowCase(bookaction);
                        break;
                }
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            bookname= Static_BookLib.BOOKNAMELIST2017[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book_2017_",""))];
            bookaction=Static_BookLib.WEBBOOKNAME2017[Integer.parseInt(mbasebok.getClass().getSimpleName().replace("Entity_Book_2017_",""))];
            bookaction=Static_BookLib.changeHeadToLowCase(bookaction);
        }
        switch (mtype){
            case 0:
                url=mip+"/"+bookaction+"/"+bookaction+"Action!add";
                uuid= UUID.randomUUID().toString().replace("-","");
                break;
            case 1:
                url=mip+"/"+bookaction+"/"+bookaction+"Action!update";
                uuid= mbasepojo.getId();
                break;
            case 2:
                url="";
                uuid= UUID.randomUUID().toString().replace("-","");
                break;
            default:
                url=mip+"/"+bookaction+"/"+bookaction+"Action!add";
                uuid= UUID.randomUUID().toString().replace("-","");
                break;
        }
        title.setText(bookname);
        bookDetailBuilder = new BookDetailBuilder(this.getActivity(), mbasebok, bookcontent);
        bookDetailBuilder.setEditState(mtype == 2 ? false : true);
        bookDetailBuilder.build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_detail, container, false);
    }
    public void printer(){
        Toast.makeText(getActivity(),"文书生成中",Toast.LENGTH_SHORT).show();
        pdfpath=Environment.getExternalStorageDirectory()+"/at.pdf";
        Base_Entity booktmp=bookDetailBuilder.getResult();
        PdfFactory2017.create().setTimeout(1000).setTTFpath(Environment.getExternalStorageDirectory()+"/TTFS").setFileout(pdfpath).open().printerMaster(booktmp).close(new IPdfBack() {
            @Override
            public void writeError() {
                Toast.makeText(getActivity(),"操作过快请等待1秒",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void writeStart() {

            }

            @Override
            public void writeEnd() {
                PdfPrintHelp.print(pdfpath,getActivity(),1000);

            }
        });
    }
    public void buildMap(){
        String sClass = null;
        datamap.clear();
        try {
            Object bean = null;
            if(mflag==FLAG_2017){
                bean= ConvertPrint2017.getInstance().bookentity2webobject(bookDetailBuilder.getResult());
                sClass = bean.getClass().getSimpleName().replaceFirst("W","w");
                Field[] declaredFields = bean.getClass().getFields();
                for (Field field : declaredFields) {
                    try {
                        if(field.get(bean) == null){
                            datamap.put(sClass+"."+field.getName(),"");
                        }else{
                            if(field.getName().contains("List")){
                                List tmplist= (List) field.get(bean);
                                if(tmplist!=null&&tmplist.size()>0){
                                    for (int i = 0; i <tmplist.size() ; i++) {
                                        Object listchild=tmplist.get(i);
                                        Field[] declaredlistchildFields = listchild.getClass().getFields();

                                        for(Field childfield : declaredlistchildFields){
                                            try {
                                                datamap.put(field.getName()+"["+i+"]."+childfield.getName(),childfield.get(listchild).toString());
                                            } catch (Exception e) {
                                                System.err.println(childfield.getName());
                                                e.printStackTrace();
                                            }
                                        }
                                        if(!"".equals(uuid)){
                                            datamap.put(field.getName()+"["+i+"]."+"wsid",((BasePojo)bean).getId());
                                        }

                                    }
                                }
                            }else{
                                datamap.put(sClass+"."+field.getName(),field.get(bean).toString());
                            }

                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }else if(mflag==FLAG_2014){
                bean= ConvertPrint2014.getInstance().bookentity2webobject(bookDetailBuilder.getResult());
                sClass = bean.getClass().getSimpleName().replaceFirst("W","w");
                Field[] declaredFields = bean.getClass().getFields();
                for (Field field : declaredFields) {
                    try {
                        if(field.get(bean) == null){
                            datamap.put(sClass+"."+field.getName(),"");
                        }else{
                            if(field.getName().contains("List")){
                                List tmplist= (List) field.get(bean);
                                if(tmplist!=null&&tmplist.size()>0){
                                    for (int i = 0; i <tmplist.size() ; i++) {
                                        Object listchild=tmplist.get(i);
                                        Field[] declaredlistchildFields = listchild.getClass().getFields();

                                        for(Field childfield : declaredlistchildFields){
                                            try {
                                                datamap.put(field.getName()+"["+i+"]."+childfield.getName(),childfield.get(listchild).toString());
                                            } catch (Exception e) {
                                                System.err.println(childfield.getName());
                                                e.printStackTrace();
                                            }
                                        }
                                        if(!"".equals(uuid)){
                                            datamap.put(field.getName()+"["+i+"]."+"wsid",((BasePojo)bean).getId());
                                        }

                                    }
                                }
                            }else{
                                datamap.put(sClass+"."+field.getName(),field.get(bean).toString());
                            }

                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }else if(mflag==Flag_KM){
                bean= ConvertPrintkm.getInstance().bookentity2webobject(bookDetailBuilder.getResult());
                sClass = bean.getClass().getSimpleName().replaceFirst("W","w");
                Field[] declaredFields = bean.getClass().getFields();
                for (Field field : declaredFields) {
                    if(field.get(bean) == null){
                        datamap.put(sClass+"."+field.getName(),"");
                    }else{
//					if(field.getName().equals("pcode")){
//						String temp = field.get(bean).toString();
//						//Log.d("wjw","value = " + temp);
//					}
                        datamap.put(sClass+"."+field.getName(),field.get(bean).toString());
                    }
                }
            }
            datamap.putAll(othermap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void review(){
        Toast.makeText(this.getActivity(),"文书生成中",Toast.LENGTH_SHORT).show();
        pdfpath=Environment.getExternalStorageDirectory()+"/at.pdf";
        Base_Entity booktmp=bookDetailBuilder.getResult();
        PdfFactory2017.create().setTimeout(1000).setTTFpath(Environment.getExternalStorageDirectory()+"/TTFS").setFileout(pdfpath).open().printerMaster(booktmp).close(new IPdfBack() {
            @Override
            public void writeError() {
                Toast.makeText(getActivity(),"操作过快请等待1秒",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void writeStart() {

            }

            @Override
            public void writeEnd() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftInputFromWindow();
                        pdfcontent.setVisibility(View.VISIBLE);
                        pdfview.fromFile(new File(pdfpath)).load();
                    }
                });

            }
        });
    }public void exitpdf(){
        pdfview.recycle();
        pdfcontent.setVisibility(View.GONE);
    }
    public void cancel(){
        getActivity().finish();
    }
    public void submit(){
        new TaskIdentify().execute();
    }
    class TaskIdentify extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonobj=new JSONObject(s);
                if(jsonobj.optBoolean("operateSuccess")){
                    finish();
                }else{
                    Toast.makeText(getActivity(),"出错",Toast.LENGTH_SHORT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String result= null;
            try {
                result = HttpUrlConnectUtil.doPost(url,datamap,mcookie);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.printer) {
            printer();
        } else if (i == R.id.see) {
            review();
        } else if (i == R.id.cancel) {
            cancel();
        } else if (i == R.id.identify) {
            submit();
        }else if (i == R.id.exitpdf) {
            exitpdf();
        }else if (i == R.id.printer2) {
            printer();
        }
    }
    public void hideSoftInputFromWindow(){
        try {//为了防止下一个页面尺寸计算错误此处需要关闭键盘
            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public boolean onBackPressed() {
        if(pdfcontent.getVisibility()==View.VISIBLE){
            exitpdf();
            return true;
        }else{
            return false;
        }
    }
    public void finish(){
        getActivity().finish();
    }


}
