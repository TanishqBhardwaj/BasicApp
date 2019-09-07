package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        String[] list_items ={"Harry Potter 1" , "Harry Potter 2" , "Harry Potter 3" , "Harry Potter 3" , "Harry Potter 4" , "Harry Potter 5" , "Harry Potter 6" ,
                "Harry Potter 7" , "Harry Potter 8" , "Harry Potter 9" , "Harry Potter 10" , "Harry Potter 11" , "Harry Potter 12" ,
                "Harry Potter 13" , "Harry Potter 14" , "Harry Potter 15" , "Harry Potter 16" , "Harry Potter 17"};
        recyclerList.setAdapter(new adapterRV(list_items));



    }
}