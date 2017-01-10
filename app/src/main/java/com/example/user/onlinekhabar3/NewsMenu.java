package com.example.user.onlinekhabar3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsMenu extends AppCompatActivity  {
    ListView lv;
    Context context;
    String url="http://192.168.1.5/onlinekhabar/test.php";
    ArrayList<Entity>entityArrayList;

   /* ArrayList EventName;*/
   /* public static int [] EventImages={R.drawable.images5,R.drawable.images6,R.drawable.images7,R.drawable.images8,R.drawable.images4};
    public static String [] EventNameList={"पहिराको कारण पूर्व र पछिम नेपल्बता चितवन हुदै भिभिन्न स्थानिनी आवोत्जवोत गर्ने हज्रौऔ सर्ब सदरण दुखमा परेका छन् ।",
            "३१ जेठ, काठमाडौं । दशरथ रंगशालाबाट आइतबार घोषित पार्टी नयाँ शक्ति नेपालका संयोजक डा। बाबुराम भट्टराईले आफूहरुले आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताका छन् । ।",
            "आइतबार दशरथ रंगशालामा नयाँ शक्ति पार्टीको औपचारिक घोषणा गर्दै उनले महाभारतका अर्जुनले चराको आँख ताकेको जस्तै नयाँ शक्तिले पनि आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताए । ",
            "हास्यकलाकार सिताराम कट्टेल (धुर्मुस) र कुञ्जना घिमिरे (सुन्तली)ले सिन्धुपाल्चोकको गिरान्चौर एकीकृत बस्ती निर्माणको काम बुधबारबाट सुरु गरेका छन् । ",
            "काठमाडौँ भारतका लागि निवर्तमान नेपाली राजदूत दीपकुमार उपाध्यायले प्रधानमन्त्री केपी शर्मा ओलीको भारत भ्रमणपछि दुई देशबीचको सम्बन्ध सामान्य बन्दै गएको बताएका छन ।"};
    public static String[]EventDate={"7days ago","2months ago","1month ago","5days ago","7hrs ago"};
    public static String[]EventComment={"2comments ","7comments","148comments","no comments","55 comments"};*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_menu);
        lv=(ListView) findViewById(R.id.listView);
        new Homesync().execute(url);


       /* lv.setAdapter(new CustomAdapter(NewsMenu.this, EventNameList,EventImages, EventDate,EventComment));*/
    }
    private class Homesync extends AsyncTask<String, String, String> {
        JSONArray jsonArray;
        ProgressDialog progressDialog;
        String jsonString;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewsMenu.this);
            progressDialog.setMessage("Loading.........");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            System.out.println(params[0]);
            DownloadUtil downloadUtil = new DownloadUtil(params[0], getApplicationContext());
            jsonString = downloadUtil.downloadStringContent();
            return jsonString;
        }

        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            if (responseString.equalsIgnoreCase(DownloadUtil.NotOnline)) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();

            } else {
                entityArrayList = new ArrayList<Entity>();
                try {
                    entityArrayList = new ArrayList<Entity>();
                    jsonArray = new JSONArray(responseString);
                    for (int i = 0; i < jsonArray.length(); i++) {

//                        Entity entity = new Entity();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        entityArrayList.add(new Entity(jsonObject.getInt("event_id"),jsonObject.getString("name"),
                                jsonObject.getString("image"),jsonObject.getString("date"),jsonObject.getString("comment"),jsonObject.getInt("category_id"),jsonObject.getString("event_title")));
                        /*String Id = jsonObject.getString("event_id");
                        String Name = jsonObject.getString("name");
                        String Image = jsonObject.getString("image");
                        String Date = jsonObject.getString("date");
                        String Comment = jsonObject.getString("comment");

                        entity.setId(Id);
                        entity.setName(Name);
                        entity.setDate(Date);
                        entity.setImage(Image);
                        entity.setComment(Comment);

                        entityArrayList.add(entity);*/

                    }

                    lv.setAdapter(new CustomAdapter(getApplicationContext(), entityArrayList));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

