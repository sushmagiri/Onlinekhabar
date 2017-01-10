package com.example.user.onlinekhabar3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

/**
 * Created by user on 6/28/2016.
 */
public class NewsFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    ListView lv;
    Context context;
    ArrayList EventName;

    SliderLayout mDemoSlider;



    public static int [] EventImages={R.drawable.naya,R.drawable.images1,R.drawable.images2,R.drawable.images3,R.drawable.images4};
    public static String [] EventNameList={"पहिराको कारण पूर्व र पछिम नेपल्बता चितवन हुदै भिभिन्न स्थानिनी आवोत्जवोत गर्ने हज्रौऔ सर्ब सदरण दुखमा परेका छन् ।",
            "३१ जेठ, काठमाडौं । दशरथ रंगशालाबाट आइतबार घोषित पार्टी नयाँ शक्ति नेपालका संयोजक डा। बाबुराम भट्टराईले आफूहरुले आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताका छन् । ।", "आइतबार दशरथ रंगशालामा नयाँ शक्ति पार्टीको औपचारिक घोषणा गर्दै उनले महाभारतका अर्जुनले चराको आँख ताकेको जस्तै नयाँ शक्तिले पनि आर्थिक विकास र समृद्धिमा अर्जुनदृष्टि लगाउने बताए । ",
            "हास्यकलाकार सिताराम कट्टेल (धुर्मुस) र कुञ्जना घिमिरे (सुन्तली)ले सिन्धुपाल्चोकको गिरान्चौर एकीकृत बस्ती निर्माणको काम बुधबारबाट सुरु गरेका छन् । ",
            "काठमाडौँ भारतका लागि निवर्तमान नेपाली राजदूत दीपकुमार उपाध्यायले प्रधानमन्त्री केपी शर्मा ओलीको भारत भ्रमणपछि दुई देशबीचको सम्बन्ध सामान्य बन्दै गएको बताएका छन ।"};
    public static String[]EventDate={"7days ago","2months ago","1month ago","5days ago","7hrs ago"};
    //public static String[]EventComment={"2comments ","7comments","148comments","no comments","55 comments"};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate){
        View rootView = inflater.inflate(R.layout.tabs, container, false);

        lv=(ListView) rootView.findViewById(R.id.listView);
        //lv.setAdapter(new CustomAdapter(getActivity(), EventNameList,EventImages,EventDate,EventComment));

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
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
                intent.putExtra("data", EventNameList[position]);
                intent.putExtra("date",EventDate[position]);
                intent.putExtra("image",EventImages[position]);
               // intent.putExtra("comment",EventComment[position]);
                startActivity(intent);
            }
        });

        return rootView;


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
}
