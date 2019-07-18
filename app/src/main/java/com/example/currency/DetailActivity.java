package com.example.currency;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements WebServiceListener{

    HorizontalBarChart horizontalBarChart;
    String currency_short;
    String currency_long;
    String strEarlier;
    String strNow;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        currency_short = intent.getStringExtra("currency_short");
        currency_long = intent.getStringExtra("currency_long");
        strEarlier = intent.getStringExtra("strEarlier");
        strNow= intent.getStringExtra("strNow");
        //System.out.println(strEarlier);
        //System.out.println(strNow);

        setTitle(currency_long +" Analizi");
        //textView.setText(currency_short);

        horizontalBarChart = (HorizontalBarChart) findViewById(R.id.chartBar);
        horizontalBarChart.getDescription().setText(currency_short +"/TRY Grafiği");
        horizontalBarChart.setNoDataText("Veriler indirilirken lütfen bekleyin!");
        horizontalBarChart.setNoDataTextColor(Color.parseColor("#f76262"));
        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.setExtraBottomOffset(6f);



        try{
            Uri.Builder newBuilder = new Uri.Builder();
            newBuilder.scheme("https")
                    .authority("api.exchangeratesapi.io")
                    .appendPath("history")
                    .appendQueryParameter("start_at",strEarlier)
                    .appendQueryParameter("end_at",strNow)
                    .appendQueryParameter("base","TRY");
            String newUrl = newBuilder.build().toString();
            //System.out.println("URL= " + newUrl);
            String status="analyze";
            DownloadData downloadBigData = new DownloadData(this, status);
            downloadBigData.execute(newUrl);
        } catch (Exception e){
        }
    }

    @Override
    public void OnTaskCompleted(float usd, float euro, float gbp, float chf, float cad, float nok, String strNow, String strEarlier) {

    }

    @Override
    public void OnDetailTaskCompleted(final ArrayList<String> datelist, ArrayList<Float> usdList, ArrayList<Float> euroList,
                                      ArrayList<Float> gbpList, ArrayList<Float> chfList, ArrayList<Float> cadList, ArrayList<Float> nokList) {
        //System.out.println(datelist);
        //System.out.println(usdList);

        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 0.5f;
        float spaceForBar = 1f;
        BarDataSet barDataSet;
        BarData barData;

        final XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datelist));
        xAxis.setLabelCount(datelist.size()+1);

        if ("USD".equals(currency_short)) {
            yVals.clear();
            for (int i = 0; i < datelist.size(); i++) {
                yVals.add(new BarEntry(i * spaceForBar, usdList.get(i)));
            }
            barDataSet = new BarDataSet(yVals, currency_short +"/TRY Paritesi");
            barDataSet.setColor(Color.parseColor("#f76262"));
            barData = new BarData(barDataSet);
            barData.setBarWidth(barWidth);
            //horizontalBarChart.setFitBars(true);
            //horizontalBarChart.getData().setValueTextSize(16);
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();
        } else if ("EURO".equals(currency_short)) {
            yVals.clear();
            for (int i = 0; i < datelist.size(); i++) {
                yVals.add(new BarEntry(i * spaceForBar, euroList.get(i)));
            }
            barDataSet = new BarDataSet(yVals, currency_short +"/TRY Paritesi");
            barDataSet.setColor(Color.parseColor("#f76262"));
            barData = new BarData(barDataSet);
            barData.setBarWidth(barWidth);
            //horizontalBarChart.setFitBars(true);
            //horizontalBarChart.getData().setValueTextSize(16);
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();
        } else if ("GBP".equals(currency_short)) {
            yVals.clear();
            for (int i = 0; i < datelist.size(); i++) {
                yVals.add(new BarEntry(i * spaceForBar, gbpList.get(i)));
            }
            barDataSet = new BarDataSet(yVals, currency_short +"/TRY Paritesi");
            barDataSet.setColor(Color.parseColor("#f76262"));
            barData = new BarData(barDataSet);
            barData.setBarWidth(barWidth);
            //horizontalBarChart.setFitBars(true);
            //horizontalBarChart.getData().setValueTextSize(16);
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();
        } else if ("CHF".equals(currency_short)) {
            yVals.clear();
            for (int i = 0; i < datelist.size(); i++) {
                yVals.add(new BarEntry(i * spaceForBar, chfList.get(i)));
            }
            barDataSet = new BarDataSet(yVals, currency_short +"/TRY Paritesi");
            barDataSet.setColor(Color.parseColor("#f76262"));
            barData = new BarData(barDataSet);
            barData.setBarWidth(barWidth);
            //horizontalBarChart.setFitBars(true);
            //horizontalBarChart.getData().setValueTextSize(16);
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();
        } else if ("CAD".equals(currency_short)) {
            yVals.clear();
            for (int i = 0; i < datelist.size(); i++) {
                yVals.add(new BarEntry(i * spaceForBar, cadList.get(i)));
            }
            barDataSet = new BarDataSet(yVals, currency_short +"/TRY Paritesi");
            barDataSet.setColor(Color.parseColor("#f76262"));
            barData = new BarData(barDataSet);
            barData.setBarWidth(barWidth);
            //horizontalBarChart.setFitBars(true);
            //horizontalBarChart.getData().setValueTextSize(16);
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();
        } else if ("NOK".equals(currency_short)) {
            yVals.clear();
            for (int i = 0; i < datelist.size(); i++) {
                yVals.add(new BarEntry(i * spaceForBar, nokList.get(i)));
            }
            barDataSet = new BarDataSet(yVals, currency_short +"/TRY Paritesi");
            barDataSet.setColor(Color.parseColor("#f76262"));
            barData = new BarData(barDataSet);
            barData.setBarWidth(barWidth);
            //horizontalBarChart.setFitBars(true);
            //horizontalBarChart.getData().setValueTextSize(16);
            horizontalBarChart.setData(barData);
            horizontalBarChart.invalidate();
        } else {
            horizontalBarChart.setNoDataText("Houston, we have a problem!");
        }
    }
}
