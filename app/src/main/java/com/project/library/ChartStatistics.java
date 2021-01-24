package com.project.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.library.R;
import com.project.library.BarChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartStatistics extends AppCompatActivity {

    ArrayList<Book> booklist;

    LinearLayout layout;
    Map<String,Integer> src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_statistics);

        Intent i = getIntent();
        booklist = (ArrayList<Book>) i.getSerializableExtra("booklist");
        src = getSrc(booklist);

        layout=findViewById(R.id.chartLayout);
        layout.addView(new BarChartView(getApplicationContext(),src));
    }

    private Map<String, Integer> getSrc(List<Book> books){
        if(books==null || books.isEmpty())
            return new HashMap<>();
        else{
            Map<String,Integer> res = new HashMap<>();
            for(Book b:books)
                if(res.containsKey(b.getBookGenre().toString()))
                    res.put(b.getBookGenre().toString(), res.get(b.getBookGenre().toString()) +1);
                else
                    res.put(b.getBookGenre().toString(), 1);
                return res;
        }
    }
}