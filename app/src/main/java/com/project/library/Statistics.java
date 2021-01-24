package com.project.library;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent intent = getIntent();
        ArrayList<Book> listBookRead = (ArrayList<Book>)intent.getSerializableExtra("listBookRead");
        ArrayList<Book> listBookNoRead = (ArrayList<Book>)intent.getSerializableExtra("listBookNoRead");

        List<Double> listPagesRead = new ArrayList<>();
        List<Double> listPagesNoRead = new ArrayList<>();

        for(Book b: listBookRead) listPagesRead.add(Double.valueOf(b.getNoPages()));
        for(Book b: listBookNoRead) listPagesNoRead.add(Double.valueOf(b.getNoPages()));

        System.out.println(listPagesRead);
        System.out.println(listPagesNoRead);

        XYPlot plot = findViewById(R.id.xyplot);
        plot.setTitle(getString(R.string.plot_title));
        XYSeries xySeriesPoetry = new SimpleXYSeries(listPagesRead, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getString(R.string.read));
        XYSeries xySeriesFiction = new SimpleXYSeries(listPagesNoRead, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getString(R.string.not_read));

        plot.addSeries(xySeriesPoetry, new LineAndPointFormatter(getApplicationContext(),R.layout.f1));
        plot.addSeries(xySeriesFiction, new LineAndPointFormatter(getApplicationContext(),R.layout.f2));
    }
}