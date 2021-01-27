package com.example.bdproperties.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.services.ApiClient;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertyDataSetAdapter extends RecyclerView.Adapter<PropertyDataSetAdapter.PropertyDataSetAdapterViewHolder> {
Context context;
List<PropertySellRegistrationDataSet>propertySellRegistrationDataSets;

    public PropertyDataSetAdapter(Context context, List<PropertySellRegistrationDataSet> propertySellRegistrationDataSets) {
        this.context = context;
        this.propertySellRegistrationDataSets = propertySellRegistrationDataSets;
    }

    @NonNull
    @Override
    public PropertyDataSetAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_raw_list,parent,false);


        return new PropertyDataSetAdapterViewHolder(view) ;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PropertyDataSetAdapterViewHolder holder, int position) {
        holder.propertyNameTextView.setText(String.valueOf(propertySellRegistrationDataSets.get(position).getAddress()));
        holder.propertyAdressView.setText(String.valueOf(propertySellRegistrationDataSets.get(position).getAddress()));
        holder.propertyPriceView.setText(propertySellRegistrationDataSets.get(position).getSalePrice().toString());
        String coverImageUrlS = propertySellRegistrationDataSets.get(position).getCoverUrl().substring(2);
        Picasso.get().load(ApiClient.BASE_URL+coverImageUrlS).resize(200,250).centerCrop().into(holder.coverImageView);
        Toast.makeText(context, ""+propertySellRegistrationDataSets.get(position).getCoverUrl(), Toast.LENGTH_SHORT).show();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("propertyID",propertySellRegistrationDataSets.get(position).getId());
                NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);
                navController.navigate(R.id.propertyDetailsFragment,bundle);
            }
        });

    }


    @Override
    public int getItemCount() {
        return propertySellRegistrationDataSets.size();
    }

    class PropertyDataSetAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView propertyNameTextView,propertyAdressView,propertyPriceView,propertyDetailsView;
        ImageView coverImageView;
       public PropertyDataSetAdapterViewHolder(@NonNull View itemView) {
           super(itemView);

           propertyNameTextView = itemView.findViewById(R.id.propertyNameView);
           propertyAdressView = itemView.findViewById(R.id.adressView);
           propertyPriceView=itemView.findViewById(R.id.sellPriceView);
           propertyDetailsView=itemView.findViewById(R.id.detailsView);
           coverImageView = itemView.findViewById(R.id.covetImageView);
       }
   }
    public void setPropertyDetails(List<PropertySellRegistrationDataSet>propertyDetails){
        this.propertySellRegistrationDataSets=propertyDetails;
        notifyDataSetChanged();
    }

}
