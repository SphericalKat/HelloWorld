package org.firehound.helloworld;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    boolean[] symptomList;
    private static final String TAG = "HomeFragment";
    public interface switchFragment {
        public void onSelectedSwitchFragment(Fragment fragment);
    }
    switchFragment callback;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        symptomList = new boolean[10];

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                switch (v.getId()) {
                    case R.id.checkBox:
                        if (checked) {
                            symptomList[0] = checked;
                        } else {
                            symptomList[0] = checked;
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
                    case R.id.checkBox6:
                        if (checked) {
                            symptomList[5] = true;
                        } else {
                            symptomList[5] = false;
                        }
                        break;
                    case R.id.checkBox7:
                        if (checked) {
                            symptomList[6] = true;
                        } else {
                            symptomList[6] = false;
                        }
                        break;
                    case R.id.checkBox8:
                        if (checked) {
                            symptomList[7] = true;
                        } else {
                            symptomList[7] = false;
                        }
                        break;
                    case R.id.checkBox9:
                        if (checked) {
                            symptomList[8] = true;
                        } else {
                            symptomList[8] = false;
                        }
                        break;
                    case R.id.checkBox10:
                        if (checked) {
                            symptomList[9] = true;
                        } else {
                            symptomList[9] = false;
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
        c6 = view.findViewById(R.id.checkBox6);
        c7 = view.findViewById(R.id.checkBox7);
        c8 = view.findViewById(R.id.checkBox8);
        c9 = view.findViewById(R.id.checkBox9);
        c10 = view.findViewById(R.id.checkBox10);

        Button submitButton = view.findViewById(R.id.button);

        c1.setOnClickListener(listener);
        c2.setOnClickListener(listener);
        c3.setOnClickListener(listener);
        c4.setOnClickListener(listener);
        c5.setOnClickListener(listener);
        c6.setOnClickListener(listener);
        c7.setOnClickListener(listener);
        c8.setOnClickListener(listener);
        c9.setOnClickListener(listener);
        c10.setOnClickListener(listener);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVolleyRequest();
            }
        });

        return view;
    }

    private JSONObject serializeSlug() {
        Date date = new Date();
        long time = date.getTime();
        String userid = MainActivity.userid;

        Gson gson = new Gson();
        String json = gson.toJson(new QueryObject(userid, new Timestamp(time), symptomList));
        //Toast.makeText(getActivity(), json, Toast.LENGTH_LONG).show();
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initVolleyRequest() {
        String url = "http://192.168.43.115:8000/new";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, serializeSlug(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSelectedSwitchFragment(new ResultsFragment());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.toString());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                //Toast.makeText(getActivity(), "Request sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (switchFragment) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Must implement the callback");
        }
    }
}
