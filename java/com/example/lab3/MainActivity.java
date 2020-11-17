package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int id = 0;
    EditText input;
    ArrayList<String> potMatches = new ArrayList<>();
    static final int itemLayout = android.R.layout.list_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.EditTextInput);
        ListPopupWindow popupWindow = new ListPopupWindow(this);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                }

                potMatches.clear();
                preSearch(input);


            }


        });

    }


    private void preSearch(EditText toSearch) {
        final String searchedLetters = toSearch.getText().toString();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String data;
                try {
                    data = getData("https://andla.pythonanywhere.com/getnames/" + id + "/" + searchedLetters);
                    jsonParse(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        id++;
        t.start();
    }

    private void jsonParse(String data) throws JSONException {
        JSONObject jb = new JSONObject("{" + data);
        JSONArray result = jb.getJSONArray("result");
        String jsonId = jb.getString("id");
        int currID = Integer.parseInt(jsonId);
        if (currID != id) {
            return;
        } else {
            for (int k = 0; k < result.length(); k++) {
                potMatches.add(result.get(k).toString());
            }
            setArrayAdapterListPopUp();
        }
    }


    public void setArrayAdapterListPopUp(){
        ListPopupWindow listPopupWindow = new ListPopupWindow(MainActivity.this);
        listPopupWindow.setAdapter(new PopAdapter(this, potMatches));
        listPopupWindow.show();
    }

    public String getData (String URLToSearch) throws JSONException {
        String result = "";
        try {
            URL url = new URL(URLToSearch);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            System.out.print( reader.readLine());
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}