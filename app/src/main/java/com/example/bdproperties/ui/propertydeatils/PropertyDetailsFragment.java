package com.example.bdproperties.ui.propertydeatils;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.pojos.ProperyDetailsDataSets;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailsFragment extends Fragment implements OnMapReadyCallback {
    int propertyId;
    TextView PropertyDetails , AreaNameText, BedRoomDetails,WashRoomDetails,VarandhaDetails,DrawingDinigDetails,ParkingDetails;
    ProperyDetailsDataSets propertySellRegistrationDataSet;
    ProgressDialog progressDialog;

    public PropertyDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_details, container, false);
        PropertyDetails=view.findViewById(R.id.properDetailsName);
        progressDialog = new ProgressDialog(getContext());
        Bundle bundle =getArguments();
        propertyId= bundle.getInt("propertyID");


        progressDialog.show();
        RealStateApiServices realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);

        realStateApiServices.getpropertyDetails(propertyId).enqueue(new Callback<ProperyDetailsDataSets>() {
         @Override
         public void onResponse( Call<ProperyDetailsDataSets> call, Response<ProperyDetailsDataSets> response) {


             propertySellRegistrationDataSet = response.body();
             progressDialog.dismiss();
             Log.e("onResponse",response.toString());
             PropertyDetails.setText(propertySellRegistrationDataSet.getPropertyName());
         }

         @Override
         public void onFailure(@NotNull Call<ProperyDetailsDataSets> call, Throwable t) {
             Toast.makeText(getContext(), "Faild", Toast.LENGTH_SHORT).show();
             Log.e("onFailure",t.toString());

         }
     });


        return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}