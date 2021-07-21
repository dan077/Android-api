package com.example.api_oxford_dic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText word;
    Button send_text;

    ListView listViewDef;
    ArrayList<Definicion> def;
    definicionAdapter defA;
    RadioGroup rg;
    RadioButton es,en;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word = findViewById(R.id.word);
        send_text = findViewById(R.id.send_word);
        send_text.setOnClickListener(this);
        listViewDef = findViewById(R.id.listViewDef);
        def = new ArrayList<>();
        defA = new definicionAdapter(getApplicationContext(),R.layout.listado_definiciones,def);
        listViewDef.setAdapter(defA);
        rg = findViewById(R.id.button_group);

        es = findViewById(R.id.button_es);

        en = findViewById(R.id.button_en);


    }

    void apiResult(ArrayList<String>  definiciones) {
        def.clear();
        String titulo = word.getText().toString();
        int i = 1;
        for(String def : definiciones)
        {
            this.def.add(new Definicion("Definicion #"+i,def));
            i++;
            //Toast.makeText(getApplicationContext(), def, Toast.LENGTH_LONG).show();
        }

        listViewDef.setAdapter(this.defA);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_word:
                String _word = word.getText().toString();
                if(!_word.isEmpty()){
                    if(es.isChecked())
                        getSignificado(_word,"es");
                    if(en.isChecked())
                        getSignificado(_word,"en");

                }
                else{
                    Toast.makeText(this, "Por favor coloque una palabra valida en la caja de texto", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    public void getSignificado(String word, String language)
    {
        String url = String.format("https://od-api.oxforddictionaries.com:443/api/v2/entries/%s/%s", language, word);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList <String> def = new ArrayList <String>();
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject lentries = results.getJSONObject(i);
                                JSONArray la = lentries.getJSONArray("lexicalEntries");
                                for (int j = 0; j < la.length(); j++) {
                                    JSONObject entries = la.getJSONObject(j);
                                    JSONArray e = entries.getJSONArray("entries");
                                    for (int k= 0; k < e.length(); k++) {
                                        JSONObject senses = e.getJSONObject(k);
                                        JSONArray s = senses.getJSONArray("senses");
                                        for(int p = 0; p < s.length();p++) {
                                            JSONObject d = s.getJSONObject(p);
                                            JSONArray de = d.getJSONArray("definitions");
                                            def.add(de.getString(0));
                                        }

                                    }
                                }
                            }
                            apiResult(def);
                        }catch (JSONException e) {
                            e.printStackTrace();
                            def.clear();
                            listViewDef.setAdapter(defA);
                            Toast.makeText(getApplicationContext(), "No se encontró definición para esta palabra", Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                def.clear();
                listViewDef.setAdapter(defA);
                Toast.makeText(getApplicationContext(), "No se encontró definición para esta palabra", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                // headers.put("Content-Type", "application/json");
                headers.put("app_id", "7fce9f44");
                headers.put("app_key", "3772fc43b4d47eead460dc97ca3a1d65");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


}