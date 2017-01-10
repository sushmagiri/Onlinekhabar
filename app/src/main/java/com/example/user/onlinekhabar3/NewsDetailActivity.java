package com.example.user.onlinekhabar3;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {
    Entity entity;
    int image;
    ImageButton img_bt;
    TextView textView1,newstitle,news_date;
   ImageView imageView;
    Button btn_comment,btn_loadmore;
    EditText et_tycmnt;
     ListView lv;
    String url = "http://192.168.1.8/sharebajar/list.php?";
    String username;
    TextView user;
    HttpClient client;
    String response;
    int id;
    int start = 0;
    int limit = 10;
    boolean loadingMore = false;
  //  View loadMoreView;
    ArrayList<Entity1> centityArrayList= new ArrayList<>();
    ;
    private static final String REGISTER_URL = "http://192.168.1.28/Loginregister/comment.php";
    private static final String LIST_URL = "http://192.168.1.28/Loginregister/list.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
     // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       //setSupportActionBar(toolbar);
      //  getSupportActionBar().setIcon(R.drawable.arrow);
      getSupportActionBar().setDisplayShowTitleEnabled(true);
       // getSupportActionBar().setLogo(R.drawable.toolbar_logo);

  //  getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

      lv=(ListView)findViewById(R.id.listView);
         View header = getLayoutInflater().inflate(R.layout.activity_news_detail,null);
        lv.addHeaderView(header, null, false);
        //loadMoreView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loadmore, null, false);
        //lv.addFooterView(loadMoreView);
      //  lv.setTextFilterEnabled(true);
//        View footer = getLayoutInflater().inflate(R.layout.loadmore,null);
//        lv.addFooterView(footer, null, false);
//        btn_loadmore=(Button)footer.findViewById(R.id.btn_loadmore);
        textView1 = (TextView) header.findViewById(R.id.textView1);
        newstitle=(TextView)header.findViewById(R.id.newstitle);
        // textView2 = (TextView) header.findViewById(R.id.textView2);
       // textView3 = (TextView) header.findViewById(R.id.textView3);
        imageView = (ImageView) header.findViewById(R.id.imageView1);
        btn_comment=(Button)header.findViewById(R.id.bt_cmnt);
        et_tycmnt=(EditText)header.findViewById(R.id.et_comment);
        img_bt=(ImageButton)header.findViewById(R.id.share );
        news_date=(TextView)header.findViewById(R.id.news_date);
        et_tycmnt.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        Intent intent=getIntent();
         entity= (Entity) intent.getSerializableExtra("data");
        textView1.setText(entity.getName());
        textView1.setText(entity.getName());
        Log.d("name",entity.getName());
        newstitle.setText(entity.getEvent_title());
        news_date.setText(entity.getDate());
      //  textView2.setText(entity.getDate());
       // textView3.setText(entity.getComment());
          id=  entity.getId();
        Log.d("hello",String.valueOf(id));
        Picasso.with(this).load("http://192.168.1.28/onlinekhabar/images/"+entity.getImage()).into(imageView);
     //  toolbar = (Toolbar) findViewById(R.id.toolbar);
     //  setSupportActionBar(toolbar);
        username=getIntent().getStringExtra("username");
        Log.d("user name:",username);
        /*try{
            String i=getIntent().getStringExtra("pass");
            if(i.equals("HELLO")){
                lv.setVisibility(View.VISIBLE);}
            else{
                lv.setVisibility(View.GONE);
            }

        }
        catch (Exception e){
            lv.setVisibility(View.GONE);
        }*/
     /*   lv.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !(loadingMore)){
                   // String url = "http://10.0.2.2:8080/CountryWebService" +
                           // "/CountryServlet";
                  //  grabURL(url);
                }
            }
        });*/

        //String url = "http://10.0.2.2:8080/CountryWebService" + "/CountryServlet";
        //grabURL(url);



    btn_comment.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Toast.makeText(NewsDetailActivity.this, "Comment successful", Toast.LENGTH_LONG).show();
                setcomment();

            }
        });

        lv.setAdapter(new CustomAdapterNews(getApplicationContext(),centityArrayList));
        client=new DefaultHttpClient();
        new LongOperation().execute(LIST_URL);


        img_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =entity.getImage()+entity.getName();
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }

    private void setcomment() {
        String comment2 = et_tycmnt.getText().toString();
        doComment(username, comment2,id);

    }

    private void doComment(final String username, String comment2,Integer id) {
        String urlSuffix = ("?event_id="+id+"&name=" + username + "&comment=" + comment2).replaceAll(" ", "%20");

        class setcomment extends AsyncTask<String, String, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(NewsDetailActivity.this,"Please Wait",null,true,true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute( s);
                loading.dismiss();

                Toast.makeText(NewsDetailActivity.this,s, Toast.LENGTH_LONG).show();
                finish();
                String i ="HELLO";
                startActivity(getIntent().putExtra("pass",i));

            }

            @Override
            protected String doInBackground(String... strings) {
                String s = strings[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL + s);
                    Log.d("url comment", REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }




        }
        setcomment su=new setcomment();
        su.execute(urlSuffix);
        Log.d("values", urlSuffix);


    }

    private class LongOperation extends AsyncTask<String,String,Void>{
        String suffix="?event_id="+id;
        private String error = null;
        private ProgressDialog progressDialog = new ProgressDialog(NewsDetailActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=ProgressDialog.show(NewsDetailActivity.this,"Please wait",null,true,true);

        }



        @Override
        protected Void doInBackground(String... urls) {
            String URL = null;
            loadingMore = true;

            HttpGet request = new HttpGet(urls[0].toString()+suffix);

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
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            Toast toast;
            if(error==null){
                try {

                    if(response.contains("[") && response.contains("]")) {
                        response = response.substring(response.indexOf('['), response.indexOf(']') + 1);
                        JSONArray jsonArray = new JSONArray(response);
                        // Toast.makeText(getActivity(),""+jsonArray.length(),Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject();

                        Log.d("json array length", String.valueOf(jsonArray.length()));


                        for (int i = 0; i < jsonArray.length(); i++) {
                            start++;
                            jsonObject = jsonArray.getJSONObject(i);
                            centityArrayList.add(new Entity1(jsonObject.getInt("event_id"),jsonObject.getInt("comment_id"),jsonObject.getString("name"),jsonObject.getString("comment")));
                        }
                        Log.d("comment size", String.valueOf(centityArrayList.size()));
                        lv.setAdapter(new CustomAdapterNews(getApplicationContext(),centityArrayList));
                        lv.invalidate();



                    }


                }
                catch (JSONException e){
                    Toast.makeText(NewsDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(NewsDetailActivity.this,error, Toast.LENGTH_SHORT).show();
            }

        }

    }








        /* data = getIntent().getStringExtra("data");
        date = getIntent().getStringExtra("date");
        comment = getIntent().getStringExtra("comment");
        image = getIntent().getIntExtra("image", 0);

        textView1 = (TextVie88888888888888888w) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        imageView = (ImageView) findViewById(R.id.imageView1);
        textView1.setText(data);
        textView2.setText(date);
        textView3.setText(comment);
        imageView.setImageResource(image);*/









    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



}
