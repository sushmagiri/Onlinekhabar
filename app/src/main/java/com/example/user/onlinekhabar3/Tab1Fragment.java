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

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

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
public class Tab1Fragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    ListView lv;
    Context context;
    String url="http://192.168.1.28/onlinekhabar/test.php?category_id=1";
    int id=0;
  // int start = 0;
   // int limit = 10;
    Button btn_loadmore;
    ArrayList<Entity>entityArrayList = new ArrayList<>();
    SliderLayout mDemoSlider;
    HttpClient client;
    String response;
    boolean isFinished=false;
    CustomAdapter adapter;
    View rootView;
   /* public static int [] EventImages={R.drawable.naya,R.drawable.images1,R.drawable.images2,R.drawable.images3,R.drawable.images4};
    public static String [] EventNameList={"पहिराको कारण पूर्व र पछिम नेपल्बता चितवन हुदै भिभिन्न स्थानिनी आवोत्जवोत गर्ने हज्रौऔ सर्ब सदरण दुखमा परेका छन् ।",
            "३१ जेठ, काठमाडौं । दशरथ रंगशालाबाट आइतबार घोषित पार्टी नयाँ शक्ति नेपालका संयोजक डा। बाबुराम भट्टराईले आफूहरुले आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताका छन् । ।", "आइतबार दशरथ रंगशालामा नयाँ शक्ति पार्टीको औपचारिक घोषणा गर्दै उनले महाभारतका अर्जुनले चराको आँख ताकेको जस्तै नयाँ शक्तिले पनि आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताए । ",
            "हास्यकलाकार सिताराम कट्टेल (धुर्मुस) र कुञ्जना घिमिरे (सुन्तली)ले सिन्धुपाल्चोकको गिरान्चौर एकीकृत बस्ती निर्माणको काम बुधबारबाट सुरु गरेका छन् । ",
            "काठमाडौँ भारतका लागि निवर्तमान नेपाली राजदूत दीपकुमार उपाध्यायले प्रधानमन्त्री केपी शर्मा ओलीको भारत भ्रमणपछि दुई देशबीचको सम्बन्ध सामान्य बन्दै गएको बताएका छन ।"};
    public static String[]EventDate={"7days ago","2months ago","1month ago","5days ago","7hrs ago"};
    public static String[]EventComment={"2comments ","7comments","148comments","no comments","55 comments"};


    public static int [] EventImages2={R.drawable.images5,R.drawable.images6,R.drawable.images7,R.drawable.images8};
    public static String [] EventNameList2={"तिब्बतबाट आएको बाढीले आवास भवन तथा सडक कटान नरोकिएपछि सीमा क्षेत्रका बासिन्दा झन् त्रसित भएका छन् ।","मितेरी पुलदेखि खाडीचौरसम्मको भोटेकोसी र सुनकोसी किनारका करिब ३५ किलोमिटर क्षेत्रको बस्ती जोखिममा छ ।","संयुक्त लोकतान्त्रिक मधेसी मोर्चाले व्यवस्थापिका– संसद्को आजको बैठक पनि बहिस्कार गरेको छ ।","बिहीबार बिहानको बाढीले घुन्सा खोलाको पुल बगाएपछि गोठमा गएका २२ परिवारको बास हिमाली क्षेत्रको जंगलमै भएको छ । "};
    public static String[]EventDate2={"7days ago","2months ago","1month ago","5days ago"};
    public static String[]EventComment2={"2comments ","7comments","148comments","no comments"};*/




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {


        rootView = inflater.inflate(R.layout.tabs, container, false);
        lv = (ListView) rootView.findViewById(R.id.listView);
       View header = inflater.inflate(R.layout.slider, lv, false);
        lv.addHeaderView(header, null, false);
        View footer = inflater.inflate(R.layout.loadmore,null);
        lv.addFooterView(footer, null, false);
        btn_loadmore=(Button)footer.findViewById(R.id.btn_loadmore);

//        lv.setAdapter(new CustomAdapter(getActivity(), EventNameList, EventImages, EventDate, EventComment));
        /*View footer = inflater.inflate(R.layout.footer, lv, false);
        lv.addFooterView(footer, null, true);*/
       /* Button btnLoadMore = (Button)footer.findViewById(R.id.loading_bar);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setAdapter(new CustomAdapter2(getActivity(),EventNameList2, EventImages2, EventDate2, EventComment2));


            }
        });*/
     MainActivity Activity=(MainActivity) getActivity();
       final String myDataFromActivity = Activity.getMyData();
        Log.d("oe", myDataFromActivity);

        client = new DefaultHttpClient();
        new LongOperation().execute(url);


        mDemoSlider = (SliderLayout) header.findViewById(R.id.slider);
        int[] ress = new int[3];
        ress[0] = R.drawable.images2;
        ress[1] = R.drawable.images4;
        ress[2] = R.drawable.images1;

        for (int i = 0; i < ress.length; i++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            String name = "";
            textSliderView
                    .description(name)
                    .image(ress[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setPresetTransformer("Default");

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

    @Override
   public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


/*
    private class HomeSync extends AsyncTask<String,String,String> {
        JSONArray jsonArray;
        ProgressDialog progressDialog;
        String jsonString;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading.........");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            System.out.println(params[0]);
            DownloadUtil downloadUtil = new DownloadUtil(params[0], getActivity());
            jsonString = downloadUtil.downloadStringContent();
            return jsonString;
        }

        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            if (responseString.equalsIgnoreCase(DownloadUtil.NotOnline)) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_LONG).show();

            } else {
                progressDialog.hide();

                entityArrayList = new ArrayList<Entity>();
                try {
                    entityArrayList = new ArrayList<Entity>();
                    jsonArray = new JSONArray(responseString);
                    for (int i = 0; i < jsonArray.length(); i++) {

//                        Entity entity = new Entity();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        entityArrayList.add(new Entity(jsonObject.getInt("event_id"),jsonObject.getString("name"),
                                jsonObject.getString("image"),jsonObject.getString("date"),jsonObject.getString("comment")));
                        *//*String Id = jsonObject.getString("event_id");
                        String Name = jsonObject.getString("name");
                        String Image = jsonObject.getString("image");
                        String Date = jsonObject.getString("date");
                        String Comment = jsonObject.getString("comment");

                        entity.setId(Id);
                        entity.setName(Name);
                        entity.setDate(Date);
                        entity.setImage(Image);
                        entity.setComment(Comment);

                        entityArrayList.add(entity);*//*

                    }

                    lv.setAdapter(new CustomAdapter(getActivity(), entityArrayList));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }*/


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

            HttpGet request = new HttpGet(urls[0].toString()+"&id="+id);

            Log.d("url",urls[0].toString()+"&id="+id);
//            urls=String.format(urls,entityArrayList.set(1,category_id)

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

                        if (jsonArray.length() < 10){
                            isFinished = true;
                        }
                        // Toast.makeText(getActivity(),""+jsonArray.length(),Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = new JSONObject();

                        Log.d("json array length", String.valueOf(jsonArray.length()));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            entityArrayList.add(new Entity(jsonObject.getInt("id"),jsonObject.getString("title"),
                                    jsonObject.getString("image"),jsonObject.getString("date"),jsonObject.getString("description"),jsonObject.getInt("category_id"),jsonObject.getString("event_title")));                            //itemClasses.add(new ItemClass(jsonObject.getInt("id"),jsonObject.getString("title"),jsonObject.getString("description"),jsonObject.getString("image"),jsonObject.getString("date")));
                        }

                        Log.d("ITEMS SIZE", String.valueOf(entityArrayList.size()));

                        //lv.setAdapter(new CustomAdapter(getActivity(), entityArrayList));
                        adapter= new CustomAdapter(getActivity(), entityArrayList);
                        if(id==0){
                            lv.setAdapter(adapter);
                        }else{
                            lv.invalidateViews();
                        }

                        id=jsonObject.getInt("id");
                        Log.d("last id", String.valueOf(id));
                        // Toast.makeText(getActivity(),""+id,Toast.LENGTH_SHORT).show();


                    }




                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }else{
                Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
            }


        }
    }
//    private class LoadMore extends AsyncTask<Void,Void,Void>{
//        private String error = null;
//        private ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        protected void onPreExecute(){
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Please wait..");
//            progressDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            return null;
//        }
//    }

}


