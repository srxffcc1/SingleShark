package com.shark.app.singleactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

import com.artifex.mupdf.mini.DocumentActivity;
import com.businessframehelp.R;
import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.utils.BarUtil;

import java.io.File;


/**
 * Created by King6rf on 2017/7/16.
 */

public class ActivityPdfBook extends FrameActivity {
    int nowtask=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_layout);
        BarUtil.initBar(this);

        lookTask();
//        pdfView = (PDFView) findViewById(R.id.pdfview);
//        pdfView.fromFile(new File(Environment.getExternalStorageDirectory()+"/test2017.pdf"))
//                .swipeHorizontal(true)
//                .scrollHandle(new ScrollHandle() {
//                    @Override
//                    public void setScroll(float position) {
//
//                    }
//
//                    @Override
//                    public void setupLayout(PDFView pdfView) {
//
//                    }
//
//                    @Override
//                    public void destroyLayout() {
//
//                    }
//
//                    @Override
//                    public void setPageNum(int pageNum) {
//
//                    }
//
//                    @Override
//                    public boolean shown() {
//                        return true;
//                    }
//
//                    @Override
//                    public void show() {
//
//                    }
//
//                    @Override
//                    public void hide() {
//
//                    }
//
//                    @Override
//                    public void hideDelayed() {
//                        if(pdfView.getZoom()<=1){
//                            pdfView.jumpTo(pdfView.getCurrentPage(),true);
//                        }
//                    }
//                })
//                .load();
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf, menu);
        return true;
    }
    public void lookTask(){
        if(nowtask!=R.id.action_pdflook){
            Intent pdfintent=new Intent(this, DocumentActivity.class);
            pdfintent.setAction(Intent.ACTION_VIEW);
            pdfintent.setData(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/test2017.pdf")));
            addIntentContentView(R.id.pdfframe,"mupdf",pdfintent);
        }
        nowtask=R.id.action_pdflook;

    }
    public void editTask(){
        if(nowtask!=R.id.action_pdfedit){
            clearViewInGroup(R.id.pdfframe);
        }
        nowtask=R.id.action_pdfedit;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_pdflook) {
            lookTask();
            return true;
        }
        if (id == R.id.action_pdfedit) {
            editTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }
}
