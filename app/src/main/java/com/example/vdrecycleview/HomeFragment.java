package com.example.vdrecycleview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ImageSlider imageSlider;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slide1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slide2,ScaleTypes.FIT)); //Ko full để scateType là null
        imageList.add(new SlideModel(R.drawable.slide3,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slide4,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slide5,ScaleTypes.FIT));
        imageSlider.setImageList(imageList);
        return  view;

    }
}