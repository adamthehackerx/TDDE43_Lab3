package com.example.lab3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PopAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> arrayList = new ArrayList<>();


    public PopAdapter(@NonNull Context context, ArrayList<String> arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        System.out.println("INSIDE");
        return new ListPopupWindow(context, arrayList.get(position));
    }
}
