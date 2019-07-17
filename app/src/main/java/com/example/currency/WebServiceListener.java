package com.example.currency;

import java.util.ArrayList;

public interface WebServiceListener {
    void OnTaskCompleted(float usd, float euro, float gbp, float chf, float cad, float nok, String strNow,String strEarlier);
    void OnDetailTaskCompleted(ArrayList<String> datelist, ArrayList<Float> usdList, ArrayList<Float> euroList,
                               ArrayList<Float> gbpList, ArrayList<Float> chfList, ArrayList<Float> cadList, ArrayList<Float> nokList);
}
