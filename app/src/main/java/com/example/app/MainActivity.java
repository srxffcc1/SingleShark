package com.example.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shark.pdfedit.fragment.BookFragment;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitybook2017.Entity_Book_2017_0;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Base_Entity base_entity=new Entity_Book_2017_0();
        getFragmentManager().beginTransaction().replace(R.id.needp, BookFragment.getInstance(base_entity,BookFragment.TYPE_ADD)).commitAllowingStateLoss();
    }
}
