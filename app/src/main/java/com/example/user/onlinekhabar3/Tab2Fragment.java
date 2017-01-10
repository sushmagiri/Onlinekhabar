package com.example.user.onlinekhabar3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by user on 6/28/2016.
 */
public class Tab2Fragment extends Fragment {
    ListView lv;
    Button btn_loadmore;
    Context context;
    String url="http://192.168.1.28/onlinekhabar/test.php?category_id=2";
    ArrayList<Entity>entityArrayList;
    HttpClient client;
    String response;
    boolean isFinished=false;
    CustomAdapter adapter;
    View rootView;
    int id=0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
         rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        lv=(ListView) rootView.findViewById(R.id.listView);
        View footer = inflater.inflate(R.layout.loadmore,null);
        lv.addFooterView(footer, null, false);
        btn_loadmore=(Button)footer.findViewById(R.id.btn_loadmore);
        MainActivity Activity=(MainActivity) getActivity();
        final String myDataFromActivity = Activity.getMyData();
        client = new DefaultHttpClient();
        new LongOperation().execute(url);
       /* lv.setAdapter(new CustomAdapter(getActivity(), EventNameList,EventImages, EventDate,EventComment));*/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("data", entityArrayList.get(position-1));
                intent.putExtra("username",myDataFromActivity);
                startActivity(intent);
            }
        });
        btn_loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFinished) {
                    Toast.makeText(getActivity().getApplicationContext(), "No more stories", Toast.LENGTH_SHORT).show();
                } else {
                    new LongOperation().execute(url);
                    //Constants.serverURl+Constants.page+"/index.php"
                }
            }
        });



        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




    private class LongOperation  extends AsyncTask<String, String, String> {

        private String error = null;
        private ProgressDialog progressDialog = new ProgressDialog(getActivity());



        @Override
        protected void onPreExecute() {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpGet request = new HttpGet(urls[0].toString()+"&id="+id);
//
            HttpResponse httpResponse;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                httpResponse = client.execute(request);
                HttpEntity entity = httpResponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));


                String line=null;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (ClientProtocolException e) {
                error="Error";
            } catch (IOException e) {
                error="Error";
            }
            response=stringBuilder.toString();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(error==null ){
//                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                try {

                    if(response.contains("[") && response.contains("]")) {
                        response = response.substring(response.indexOf('['), response.indexOf(']') + 1);
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() < 10){
                            isFinished = true;
                        }
                        // Toast.makeText(getActivity(),""+jsonArray.length(),Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject();

                        Log.d("json array length", String.valueOf(jsonArray.length()));

                        entityArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            entityArrayList.add(new Entity(jsonObject.getInt("id"),jsonObject.getString("title"),
                                    jsonObject.getString("image"),jsonObject.getString("date"),jsonObject.getString("description"),jsonObject.getInt("category_id"),jsonObject.getString("event_title")));                            //itemClasses.add(new ItemClass(jsonObject.getInt("id"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("image"),jsonObject.getString("date")));
                        }

                        Log.d("ITEMS SIZE", String.valueOf(entityArrayList.size()));
                        adapter= new CustomAdapter(getActivity(), entityArrayList);
                        if(id==0){
                            lv.setAdapter(adapter);
                        }else{
                            lv.invalidateViews();
                        }

                        id=jsonObject.getInt("id");
                        Log.d("last id", String.valueOf(id));

                    }


                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }else{
                Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
            }


        }

        }


    }








