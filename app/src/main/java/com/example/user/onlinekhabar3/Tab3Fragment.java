package com.example.user.onlinekhabar3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class Tab3Fragment extends Fragment {
    ListView lv;
    Context context;
    String url="http://192.168.1.28/onlinekhabar/test.php?category_id=3";
    ArrayList<Entity>entityArrayList;
    HttpClient client;
    String response;
  /*  ArrayList EventName;

    public static int [] EventImages={R.drawable.naya,R.drawable.images1,R.drawable.images2,R.drawable.images3,R.drawable.images4};
    public static String [] EventNameList={"पहिराको कारण पूर्व र पछिम नेपल्बता चितवन हुदै भिभिन्न स्थानिनी आवोत्जवोत गर्ने हज्रौऔ सर्ब सदरण दुखमा परेका छन् ।",
            "३१ जेठ, काठमाडौं । दशरथ रंगशालाबाट आइतबार घोषित पार्टी नयाँ शक्ति नेपालका संयोजक डा। बाबुराम भट्टराईले आफूहरुले आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताका छन् । ।",
            "आइतबार दशरथ रंगशालामा नयाँ शक्ति पार्टीको औपचारिक घोषणा गर्दै उनले महाभारतका अर्जुनले चराको आँख ताकेको जस्तै नयाँ शक्तिले पनि आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताए । ",
            "हास्यकलाकार सिताराम कट्टेल (धुर्मुस) र कुञ्जना घिमिरे (सुन्तली)ले सिन्धुपाल्चोकको गिरान्चौर एकीकृत बस्ती निर्माणको काम बुधबारबाट सुरु गरेका छन् । ",
            "काठमाडौँ भारतका लागि निवर्तमान नेपाली राजदूत दीपकुमार उपाध्यायले प्रधानमन्त्री केपी शर्मा ओलीको भारत भ्रमणपछि दुई देशबीचको सम्बन्ध सामान्य बन्दै गएको बताएका छन ।"};
    public static String[]EventDate={"7days ago","2months ago","1month ago","5days ago","7hrs ago"};
    public static String[]EventComment={"2comments ","7comments","148comments","no comments","55 comments"};*/




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment1_tab3, container, false);
        lv=(ListView) rootView.findViewById(R.id.listView);
       /* lv.setAdapter(new CustomAdapter(getActivity(), EventNameList,EventImages,EventDate,EventComment));*/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("data", entityArrayList.get(position));
                startActivity(intent);
            }
        });

        client = new DefaultHttpClient();
        new LongOperation().execute(url);
        return rootView;
    }
    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private String error = null;
        private ProgressDialog progressDialog = new ProgressDialog(getActivity());

        protected void onPreExecute() {

            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();

        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            HttpGet request = new HttpGet(urls[0].toString());

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


        protected void onPostExecute(Void unused) {

            progressDialog.dismiss();
            if(error==null ){
//                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                try {

                    if(response.contains("[") && response.contains("]")) {
                        response = response.substring(response.indexOf('['), response.indexOf(']') + 1);
                        JSONArray jsonArray = new JSONArray(response);
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
                        lv.setAdapter(new CustomAdapter(getActivity(), entityArrayList));

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
