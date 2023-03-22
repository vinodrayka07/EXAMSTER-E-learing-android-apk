package com.example.examaster;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;

public class chartFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);




        Bundle bundle = this.getArguments();


        String sk = (bundle.getString("otherurl"));
        String rg = (bundle.getString("otherurl1"));
        String wr = (bundle.getString("otherurl2"));


        BarChart barChart = view.findViewById(R.id.barchart);
        ArrayList<BarEntry> Result = new ArrayList<>();






        Result.add(new BarEntry(2, Float.parseFloat(rg)));
        Result.add(new BarEntry(3, Float.parseFloat(sk)));
        Result.add(new BarEntry(0, Float.parseFloat(wr)));
        BarDataSet barDataSet =new BarDataSet(Result, "Results");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColors(Collections.singletonList(Color.rgb(21, 197, 93)));
        barDataSet.setValueTextSize(16f);

        BarData barData =new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Quiz Result");
        barChart.animateY(1000);






        return view;
    }
}