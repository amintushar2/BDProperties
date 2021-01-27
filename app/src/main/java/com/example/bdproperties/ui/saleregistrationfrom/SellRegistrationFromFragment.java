package com.example.bdproperties.ui.saleregistrationfrom;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.OwnerDetailsDataSet;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellRegistrationFromFragment extends Fragment implements OnMapReadyCallback {

    AutoCompleteTextView propertyNameField,sellpriceField,areaField,adressField,subpropField,propertyField,bedroomField,bathroomField,drawingDiningField,varandahField,buildingBuildYear,floorLevelField;
    MaterialButton nextButton ;
    MaterialCheckBox parkingCheckBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String emailAddress;
    int ownerId;
    OwnerDetailsDataSet propertySellRegistrationDataSet;
    public SellRegistrationFromFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_sell_registration_from, container, false);
        propertyNameField = view.findViewById(R.id.propertyTitleEDT);
        sellpriceField = view.findViewById(R.id.sellingPriceEdt);
        areaField = view.findViewById(R.id.areaAutoComplete);
        adressField = view.findViewById(R.id.adressAutoComplete);
        propertyField = view.findViewById(R.id.sellpropertyType);
        subpropField = view.findViewById(R.id.sellSubpropertyType);
        bedroomField = view.findViewById(R.id.bedroomCount);
        bathroomField = view.findViewById(R.id.bathroomCount);
        drawingDiningField = view.findViewById(R.id.drawingDining);
        varandahField = view.findViewById(R.id.varandhaEDT);
        buildingBuildYear = view.findViewById(R.id.buildingBuildYearEdt);
        floorLevelField= view.findViewById(R.id.floorLevelEDt);
        parkingCheckBox = view.findViewById(R.id.parkingCheck);
        nextButton = view.findViewById(R.id.sellRegistrationNextbtn);
        Bundle bundle =getArguments();
        //emailAddress =  bundle.getString("email");
        emailAddress = "jjj@gmail.com";
        Toast.makeText(getContext(), "ID = "+emailAddress, Toast.LENGTH_SHORT).show();

        RealStateApiServices realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);
        realStateApiServices.getOwnerDetails(emailAddress).enqueue(new Callback<OwnerDetailsDataSet>() {
            @Override
            public void onResponse(Call<OwnerDetailsDataSet> call, Response<OwnerDetailsDataSet> response) {
               if (response.isSuccessful()){
                   propertySellRegistrationDataSet =response.body();
                   ownerId = propertySellRegistrationDataSet.getId();
                   Toast.makeText(getContext(), ""+ownerId, Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<OwnerDetailsDataSet> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencedataSave();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.mapWithFragment);
                savedDataSellRegistration(createProperty());
//                Intent intent = new Intent(getContext(), UploadActivity.class);
//                startActivity(intent);
            }
        });
        return view;
    }

    private void sharedPreferencedataSave() {
        preferences =this.getContext().getSharedPreferences("MyFref",0);
      //  preferences = getActivity().getSharedPreferences("MyFref", 0);
        editor =preferences.edit();
        editor.putInt("OwnerId",ownerId);
        editor.putString("proepertyName",propertyNameField.getText().toString());
        editor.apply();

//        editor.putString("sellprice",sellpriceField.getText().toString());
//        editor.putString("area",areaField.getText().toString());
//        editor.putString("adress",adressField.getText().toString());
//        editor.putString("drwaing",drawingDiningField.getText().toString());
//        editor.putInt("subproperty", Integer.parseInt(subpropField.getText().toString()));
//        editor.putInt("bedroom", Integer.parseInt(bedroomField.getText().toString()));
//        editor.putInt("bathroom", Integer.parseInt(bathroomField.getText().toString()));
//        editor.putInt("varandha", Integer.parseInt(varandahField.getText().toString()));
//        editor.putInt("buildingYear", Integer.parseInt(buildingBuildYear.getText().toString()));
    }

    private void savedDataSellRegistration(PropertySellRegistrationDataSet property) {

    }


    public PropertySellRegistrationDataSet createProperty(){
        PropertySellRegistrationDataSet properties = new  PropertySellRegistrationDataSet();
        properties.setAddress("000");
      return properties;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {



    }
}