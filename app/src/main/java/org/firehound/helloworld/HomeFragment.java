package org.firehound.helloworld;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CheckBox c1,c2,c3,c4,c5;
    boolean[] symptomList;
    private static final String TAG = "HomeFragment";


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        symptomList = new boolean[5];

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                switch (v.getId()) {
                    case R.id.checkBox:
                        if (checked) {
                            symptomList[0] = true;
                        } else {
                            symptomList[0] = false;
                        }
                        break;
                    case R.id.checkBox2:
                        if (checked) {
                            symptomList[1] = true;
                        } else {
                            symptomList[1] = false;
                        }
                        break;
                    case R.id.checkBox3:
                        if (checked) {
                            symptomList[2] = true;
                        } else {
                            symptomList[2] = false;
                        }
                        break;
                    case R.id.checkBox4:
                        if (checked) {
                            symptomList[3] = true;
                        } else {
                            symptomList[3] = false;
                        }
                        break;
                    case R.id.checkBox5:
                        if (checked) {
                            symptomList[4] = true;
                        } else {
                            symptomList[4] = false;
                        }
                        break;
                }
            }
        };

        c1 = view.findViewById(R.id.checkBox);
        c2 = view.findViewById(R.id.checkBox2);
        c3 = view.findViewById(R.id.checkBox3);
        c4 = view.findViewById(R.id.checkBox4);
        c5 = view.findViewById(R.id.checkBox5);
        Button submitButton = view.findViewById(R.id.button);

        c1.setOnClickListener(listener);
        c2.setOnClickListener(listener);
        c3.setOnClickListener(listener);
        c4.setOnClickListener(listener);
        c5.setOnClickListener(listener);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVolleyRequest();
            }
        });



        return view;
    }

    private JsonObject serializeQuery() {
        Date date = new Date();
        long time = date.getTime();
        String userid = MainActivity.userid;

        Gson gson = new Gson();
        String json = gson.toJson(new QueryObject(userid, new Timestamp(time), symptomList));
        Toast.makeText(getActivity(), json, Toast.LENGTH_LONG).show();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        return jsonObject;
    }

    private void initVolleyRequest() {
        String url = "http://192.168.43.115:8000/new/";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("content-type","application/json");
        GsonRequest gsonRequest = new GsonRequest(url, serializeQuery(), headers, new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(gsonRequest);
    }

}
