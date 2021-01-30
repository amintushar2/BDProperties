package com.example.bdproperties.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.PropertyImages;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.services.ApiClient;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class SlideViewPager extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
   String[] imageUrls;
    private List<PropertyImages> imageModelArrayList;


    public SlideViewPager(Context context, List<PropertyImages> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
    }



    @Override
    public int getCount() {

        return imageModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return view ==(ConstraintLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.single_image_view,container,false);

        ImageView imageSlider = view.findViewById(R.id.imageView2);
        String filepathes =imageModelArrayList.get(position).getFilePath().substring(2);
        Toast.makeText(context, "Image : "+ApiClient.BASE_URL+filepathes, Toast.LENGTH_SHORT).show();
        Picasso.get()
                .load(ApiClient.BASE_URL+filepathes)
                .fit().centerCrop()
                .into(imageSlider);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image : "+imageModelArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });

        return  view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
    public void setImageDetails(List<PropertyImages>propertyImagesList){
       this.imageModelArrayList=propertyImagesList;
        notifyDataSetChanged();
    }
}
