package com.businessframehelp.module.pdf;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.businessframehelp.R;
import com.businessframehelp.utils.BarUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;

import java.io.File;


/**
 * Created by King6rf on 2017/7/16.
 */

public class PdfActivity extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_layout);
        BarUtil.initBar(this);
        pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromFile(new File(Environment.getExternalStorageDirectory()+"/test2017.pdf"))
                .swipeHorizontal(true)
                .scrollHandle(new ScrollHandle() {
                    @Override
                    public void setScroll(float position) {

                    }

                    @Override
                    public void setupLayout(PDFView pdfView) {

                    }

                    @Override
                    public void destroyLayout() {

                    }

                    @Override
                    public void setPageNum(int pageNum) {

                    }

                    @Override
                    public boolean shown() {
                        return true;
                    }

                    @Override
                    public void show() {

                    }

                    @Override
                    public void hide() {

                    }

                    @Override
                    public void hideDelayed() {
                        if(pdfView.getZoom()==1){
                            pdfView.jumpTo(pdfView.getCurrentPage());
                        }
                    }
                })
                .load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf, menu);
        return true;
    }
    public void lookTask(){}
    public void editTask(){}
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
}
