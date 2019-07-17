package com.example.currency;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class DownloadData extends AsyncTask<String, Void, String> {

    public WebServiceListener delegate = null;

    String date;
    String currency;
    String status;
    String strNow;
    String strEarlier;
    String dolar;
    String euro;
    String gbpound;
    String chf_string;
    String cad_string;
    String nok_string;
    String currentDynamicKey;
    float eur;
    float reciprocal_euro;
    float usd;
    float reciprocal_usd;
    float gbp;
    float reciprocal_gbp;
    float chf;
    float reciprocal_chf;
    float cad;
    float reciprocal_cad;
    float nok;
    float reciprocal_nok;
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<Float> usdList = new ArrayList<>();
    ArrayList<Float> euroList = new ArrayList<>();
    ArrayList<Float> gbpList = new ArrayList<>();
    ArrayList<Float> chfList = new ArrayList<>();
    ArrayList<Float> cadList = new ArrayList<>();
    ArrayList<Float> nokList = new ArrayList<>();

    JSONObject currentDynamicValue;

    public DownloadData (WebServiceListener delegate, String status){
        this.delegate=delegate;
        this.status=status;
    }

    public DownloadData (String status,String currency){
        this.status=status;
        this.currency=currency;
    }


    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        URL url;
        HttpsURLConnection httpsURLConnection;

        try {
            url = new URL(strings[0]);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data > 0) {
                char ch = (char) data;
                result += ch;

                data = inputStreamReader.read();
            }
            inputStreamReader.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        switch (status) {
            case "currency":
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String base = jsonObject.getString("base");
                    //System.out.println("base"+base);

                    date = jsonObject.getString("date");

                    String rates = jsonObject.getString("rates");
                    //System.out.println("rates"+rates);

                    JSONObject jsonObject1 = new JSONObject(rates);
                    euro = jsonObject1.getString("EUR");
                    dolar = jsonObject1.getString("USD");
                    gbpound = jsonObject1.getString("GBP");
                    chf_string = jsonObject1.getString("CHF");
                    cad_string = jsonObject1.getString("CAD");
                    nok_string = jsonObject1.getString("NOK");
                    eur = Float.valueOf(euro);
                    reciprocal_euro = 1 / eur;
                    usd = Float.valueOf(dolar);
                    reciprocal_usd = 1 / usd;
                    gbp = Float.valueOf(gbpound);
                    reciprocal_gbp = 1 / gbp;
                    chf = Float.valueOf(chf_string);
                    reciprocal_chf = 1/ chf;
                    cad = Float.valueOf(cad_string);
                    reciprocal_cad = 1 / cad;
                    nok = Float.valueOf(nok_string);
                    reciprocal_nok = 1 / nok;
                    //System.out.println("1 EUR = "+ reciprocal_euro + " TRY");
                    //System.out.println("1 USD = "+ reciprocal_usd + " TRY");
                    //System.out.println("1 GBP = "+ reciprocal_gbp + " TRY");

                    try {
                        Date now = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        Date earlier = new Date();
                        earlier.setDate(now.getDate() - 27);
                        strNow = new SimpleDateFormat("yyyy-MM-dd").format(now);
                        strEarlier = new SimpleDateFormat("yyyy-MM-dd").format(earlier);
                        //System.out.println(strNow);
                        //System.out.println(strEarlier);
                    } catch (ParseException e){
                    }

                    delegate.OnTaskCompleted(reciprocal_usd,reciprocal_euro,reciprocal_gbp,reciprocal_chf, reciprocal_cad, reciprocal_nok, strNow,strEarlier);

                } catch (Exception e) {
                }
                break;
            case "analyze":
                try {
                    JSONObject newJsonObject = new JSONObject(s);
                    String newBase = newJsonObject.getString("base");
                    //System.out.println("base"+base);

                    String newRates = newJsonObject.getString("rates");
                    //System.out.println("rates"+rates);

                    JSONObject dates = newJsonObject.getJSONObject("rates");
                    Iterator keys = dates.keys();

                    while (keys.hasNext()) {
                        currentDynamicKey = (String) keys.next();
                        //System.out.println(currentDynamicKey);
                        dateList.add(currentDynamicKey);
                        Collections.sort(dateList);
                        //System.out.println(dateList);
                    }
                    for (int i = 0; i < dateList.size(); i++) {
                        //System.out.println(dateList.get(i));
                        currentDynamicValue = dates.getJSONObject(dateList.get(i));
                        dolar = currentDynamicValue.getString("USD");
                        euro = currentDynamicValue.getString("EUR");
                        gbpound = currentDynamicValue.getString("GBP");
                        chf_string = currentDynamicValue.getString("CHF");
                        cad_string = currentDynamicValue.getString("CAD");
                        nok_string = currentDynamicValue.getString("NOK");
                        usd = Float.valueOf(dolar);
                        reciprocal_usd = 1 / usd;
                        usdList.add(reciprocal_usd);
                        eur = Float.valueOf(euro);
                        reciprocal_euro = 1 / eur;
                        euroList.add(reciprocal_euro);
                        gbp = Float.valueOf(gbpound);
                        reciprocal_gbp = 1 / gbp;
                        gbpList.add(reciprocal_gbp);
                        chf = Float.valueOf(chf_string);
                        reciprocal_chf = 1 / chf;
                        chfList.add(reciprocal_chf);
                        cad = Float.valueOf(cad_string);
                        reciprocal_cad = 1 / cad;
                        cadList.add(reciprocal_cad);
                        nok = Float.valueOf(nok_string);
                        reciprocal_nok = 1 / nok;
                        nokList.add(reciprocal_nok);
                    }

                    delegate.OnDetailTaskCompleted(dateList,usdList,euroList,gbpList,chfList,cadList,nokList);
                } catch (Exception e) {
                }
                break;
            default:
                break;
        }
        super.onPostExecute(s);
    }
}
