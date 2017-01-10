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

/**
 * Created by user on 7/4/2016.
 */
public class AboutUs extends AppCompatActivity {
    ListView lv;
    Context context;
    String url = "http://192.168.1.5/onlinekhabar/test.php";
    ArrayList<Entity> entityArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        lv=(ListView)findViewById(R.id.listView);
        new Homesync().execute(url);
    }

    private class Homesync extends AsyncTask<String, String, String> {
        JSONArray jsonArray;
        ProgressDialog progressDialog;
        String jsonString;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AboutUs.this);
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

