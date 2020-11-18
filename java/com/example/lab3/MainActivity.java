package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;
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
    ListPopupWindow listPopupWindow;
    PopAdapter popAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.EditTextInput);
        listPopupWindow = new ListPopupWindow(MainActivity.this);
        popAdapter = new PopAdapter(getApplicationContext(), potMatches);
        listPopupWindow.setAdapter(popAdapter);
        listPopupWindow.setAnchorView(input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    potMatches.clear();
                    preSearch(input);
                    if(Looper.myLooper() == Looper.getMainLooper());{
                        System.out.print("Looper.myLooper() == Looper.getMainLooper() true");
                    }
                }   else listPopupWindow.dismiss();
            }
        });

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                input.setText(potMatches.get(position));
                setArrayAdapterListPopUp();
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            listPopupWindow.setHeight(650);
        }
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
                    input.post(new Runnable() {
                        @Override
                        public void run() {
                            setArrayAdapterListPopUp();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        id++;
        t.start();
        if(Looper.myLooper() == Looper.getMainLooper());{
            System.out.print("Looper.myLooper() == Looper.getMainLooper() true");
        }
    }

    public ArrayList<String> getPotMatches() {
        return potMatches;
    }

    public void setPotMatches(ArrayList<String> potMatches) {
        this.potMatches = potMatches;
    }

    private void jsonParse(String data) throws JSONException {
        if( Looper.myLooper() == Looper.getMainLooper());{
            System.out.print("Looper.myLooper() == Looper.getMainLooper() json parse true");
        }
        JSONObject jsonObject = new JSONObject("{ " + data);
        JSONObject jsonObject1 = new JSONObject("{ " + data);
        JSONArray resultJSON = jsonObject.getJSONArray("result");
        String jsonID = jsonObject1.getString("id");
        if(Integer.parseInt(jsonID) != id){
            return;
        }
        for (int k = 0; k < resultJSON.length(); k++) {
            potMatches.add(resultJSON.get(k).toString());
        }
        setPotMatches(potMatches);
    }

    public void setArrayAdapterListPopUp() {
        if(!potMatches.isEmpty()) {
            popAdapter.notifyDataSetChanged();
            listPopupWindow.show();
        } else listPopupWindow.dismiss();
    }

    public String getData (String URLToSearch) throws JSONException {
        String result = "";
        System.out.println("urlto search" + URLToSearch);
        try {
            URL url = new URL(URLToSearch);
            System.out.println("url" +url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println("efter reader");
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
