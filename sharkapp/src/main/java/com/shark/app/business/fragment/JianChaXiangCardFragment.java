package com.shark.app.business.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shark.app.R;
import com.shark.app.business.entity.Entity_JianChaXiang;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;


@SuppressLint("ValidFragment")
public class JianChaXiangCardFragment extends Fragment {
    private String mTitle;
    private String bianhaoid;
    private TextView submittext;
    private TextView deletetext;
    String sanji;
    String siji;
    private DateBase_Entity xianchangjianchaoption;
    private RadioGroup yinhuangroup;
    private EditText yinhuanmiaoshu;
    private LinearLayout linkradio;

    public static JianChaXiangCardFragment getInstance(String title,String bianhaoid) {
        JianChaXiangCardFragment sf = new JianChaXiangCardFragment();
        sf.mTitle = title;
        sf.sanji=title.split("-")[0];
        sf.siji=title.split("-")[1];
        sf.bianhaoid=bianhaoid;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_checkoption, null);
        final TextView tv= (TextView) v.findViewById(R.id.checkclass);
        yinhuangroup = (RadioGroup) v.findViewById(R.id.yinhuangroup);
        submittext= (TextView) v.findViewById(R.id.submitmark);
        deletetext= (TextView) v.findViewById(R.id.deletemark);
        yinhuanmiaoshu = (EditText) v.findViewById(R.id.yinhuanmiaoshu);
        linkradio = (LinearLayout) v.findViewById(R.id.linkradio);
        tv.setText(mTitle);
        initView();

        return v;
    }
    public void initView(){
        xianchangjianchaoption = Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_JianChaXiang().putlogic2value("关联的执法编号id","=",bianhaoid)
                .putlogic2value("检查项三级","=",mTitle)));
        if(!xianchangjianchaoption.getId().equals("-1")){
            //说明存在这个记录
            deletetext.setVisibility(View.VISIBLE);
            submittext.setText("更新");
            if(xianchangjianchaoption.getValue("隐患级别").equals("无隐患")){
                yinhuangroup.check(R.id.yinhuanlevel1);

            }
            else if(xianchangjianchaoption.getValue("隐患级别").equals("一般隐患")){
                yinhuangroup.check(R.id.yinhuanlevel2);

            }
            else if(xianchangjianchaoption.getValue("隐患级别").equals("重大隐患")){
                yinhuangroup.check(R.id.yinhuanlevel3);

            }else{
                yinhuangroup.check(R.id.yinhuanlevel1);
            }
        }else{
            deletetext.setVisibility(View.INVISIBLE);
            submittext.setText("提交");
            yinhuangroup.check(R.id.yinhuanlevel1);
        }
        yinhuangroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.yinhuanlevel1){
                    linkradio.setVisibility(View.INVISIBLE);
                }else{

                    linkradio.setVisibility(View.VISIBLE);
                }
            }
        });
        submittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yinhuanlevel="";
                if(yinhuangroup.getCheckedRadioButtonId()==R.id.yinhuanlevel1){
                    yinhuanlevel="无隐患";
                }
                if(yinhuangroup.getCheckedRadioButtonId()==R.id.yinhuanlevel2){
                    yinhuanlevel="一般隐患";

                }
                if(yinhuangroup.getCheckedRadioButtonId()==R.id.yinhuanlevel3){
                    yinhuanlevel="重大隐患";

                }
                if(xianchangjianchaoption.getId().equals("-1")){
                    Demo_DBManager.build().save2update(new Entity_JianChaXiang()
                            .put("关联的执法编号id",bianhaoid)
                            .put("检查项三级",mTitle)
                            .put("隐患级别",yinhuanlevel)
                            .put("描述",yinhuanmiaoshu.getText().toString())
                            .put("进行的阶段转化id","现场处理措施")
                    );
                }else{
                    Demo_DBManager.build().save2update(xianchangjianchaoption
                            .put("关联的执法编号id",bianhaoid)
                            .put("检查项三级",mTitle)
                            .put("隐患级别",yinhuanlevel)
                            .put("描述",yinhuanmiaoshu.getText().toString())
                            .put("进行的阶段转化id","现场处理措施")
                    );
                }
                initView();

            }
        });
        deletetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(xianchangjianchaoption);
                initView();
            }
        });
    }
}
