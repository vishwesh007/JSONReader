package com.premium.demo.jsonreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private static final String JSON_URL = "http://192.168.1.35:8080/jsondata/";
     List<Tutorial> tutorialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        tutorialList = new ArrayList<>();
        loadTutorialList();
    }

    private void loadTutorialList() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         progressBar.setVisibility(View.INVISIBLE);


                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray tutorialsArray = obj.getJSONArray("tutorials");

                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                 JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);

                                Tutorial tutorial = new Tutorial(tutorialsObject.getString("name"), tutorialsObject.getString("imageurl"),tutorialsObject.getString("description"));

                                tutorialList.add(tutorial);
                            }

                           MyAdapter adapter = new MyAdapter(tutorialList, getApplicationContext());

                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

         requestQueue.add(stringRequest);
    }
}