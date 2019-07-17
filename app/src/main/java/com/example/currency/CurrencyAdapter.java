package com.example.currency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<Currency> {
    private Context context;
    private List<Currency> currencyList = new ArrayList<>();

    public CurrencyAdapter(Context context, ArrayList<Currency> currencyList){
        super(context,0,currencyList);
        this.context=context;
        this.currencyList=currencyList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        Currency currentCurrency = currencyList.get(position);

        ImageView image = (ImageView) listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentCurrency.getImage());

        TextView shortName = (TextView) listItem.findViewById(R.id.textView_name);
        shortName.setText(currentCurrency.getShortName());

        TextView longName = (TextView) listItem.findViewById(R.id.textView_long_name);
        longName.setText(currentCurrency.getLongName());

        TextView currency = (TextView) listItem.findViewById(R.id.textView_currency);
        currency.setText(String.valueOf(currentCurrency.getCurrency())+" TRY");
        return listItem;
    }
}
