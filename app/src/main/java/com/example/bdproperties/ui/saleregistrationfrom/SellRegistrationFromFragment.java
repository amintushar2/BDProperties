package com.example.bdproperties.ui.saleregistrationfrom;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.AreasDataSetList;
import com.example.bdproperties.pojos.OwnerDetailsDataSet;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.pojos.ProperyTypeList;
import com.example.bdproperties.pojos.SubPropertyTypeList;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellRegistrationFromFragment extends Fragment  {

    AutoCompleteTextView propertyNameField,sellpriceField,areaField,adressField,subpropField,propertyField,bedroomField,bathroomField,drawingDiningField,varandahField,buildingBuildYear,floorLevelField;
    MaterialButton nextButton ;
    MaterialCheckBox parkingCheckBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String emailAddress;

    int ownerId;
    int propertyId;
    int subpropertyId;
    boolean drawingdinigID;
    boolean parkingId;
    int areaId;
    List<ProperyTypeList> properyTypeList;
    List<SubPropertyTypeList> subPropertyTypeLists;
    ProgressDialog progressDialog;
    FirebaseUser currentUser;
    List<AreasDataSetList> areasDataSetList;
    ArrayAdapter<String> adapter;
    int purposeID;
    RealStateApiServices realStateApiServices;
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
        preferences = getActivity().getSharedPreferences("MyFref", 0);
        purposeID = preferences.getInt("purpose", 0);
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
        properyTypeList = new ArrayList<>();
        subPropertyTypeLists = new ArrayList<>();
        progressDialog =new ProgressDialog(getContext());

        currentUser =  FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);

        nextButton = view.findViewById(R.id.sellRegistrationNextbtn);


        Toast.makeText(getContext(), "ID = "+emailAddress, Toast.LENGTH_SHORT).show();


        realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);

        realStateApiServices.getOwnerDetails(emailAddress).enqueue(new Callback<OwnerDetailsDataSet>() {
            @Override
            public void onResponse(Call<OwnerDetailsDataSet> call, Response<OwnerDetailsDataSet> response) {
               if (response.isSuccessful()){
                   propertySellRegistrationDataSet =response.body();
                   ownerId = propertySellRegistrationDataSet.getId();

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
                if (purposeID==2){
                    Toast.makeText(getContext(), "ss", Toast.LENGTH_SHORT).show();
                }else{
                    if (parkingCheckBox.isChecked()){
                        parkingId = true;
                    }else if (!parkingCheckBox.isChecked()){
                        parkingId = false;
                    }
                    sharedPreferencedataSave();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.mapWithFragment);
                    savedDataSellRegistration(createProperty());
                }

            }
        });
        showPropertyList();
        shoqAreaList();
        showSubPropertyTypeList();
        subpropertyClickListener();
        yesnoSpiner();
        return view;
    }

    private void shoqAreaList() {

        Call<List<AreasDataSetList>> areaCall = realStateApiServices.getAreas();
        areaCall.enqueue(new Callback<List<AreasDataSetList>>() {
            @Override
            public void onResponse(Call<List<AreasDataSetList>> call, Response<List<AreasDataSetList>> response) {
                if (response.isSuccessful()){
                    areasDataSetList =response.body();
                    showAreaSpiner();

                }else {
                    Toast.makeText(getContext(), "Eror", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AreasDataSetList>> call, Throwable t) {

            }
        });
    }

    private void showAreaSpiner() {
        String[] LOCATIONS = new String[areasDataSetList.size()];
        for(int i=0; i<areasDataSetList.size(); i++){
            //Storing names to string array
            LOCATIONS[i] = areasDataSetList.get(i).getArea1();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, LOCATIONS);
        areaField.setAdapter(adapter);
        areaField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaId = areasDataSetList.get(position).getId();
            }
        });

    }

    private void yesnoSpiner() {
        String[] Statuse = {
                "Yes","No"
        };
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < Statuse.length; ++i) {
            list.add(Statuse[i]);

        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, Statuse);
        drawingDiningField.setAdapter(adapter);

        drawingDiningField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).toString()=="Yes"){

                    drawingdinigID =true;
                }else if (adapter.getItem(position).toString()=="No"){
                    drawingdinigID =false;
                }
                Toast.makeText(getContext(), ""+drawingdinigID, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void subpropertyClickListener() {
        subpropField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subpropertyId = subPropertyTypeLists.get(position).getId();
            }
        });


    }

    private void showSubPropertyTypeList() {
        propertyField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                propertyId = properyTypeList.get(position).getId();
                Call<List<SubPropertyTypeList>>subPropertYtypeCall =realStateApiServices.getSubPropertyTypes(propertyId);
                subPropertYtypeCall.enqueue(new Callback<List<SubPropertyTypeList>>() {
                    @Override
                    public void onResponse(Call<List<SubPropertyTypeList>> call, Response<List<SubPropertyTypeList>> response) {
                        subPropertyTypeLists= response.body();
                        subPropertyTypeListsdataShow();
                    }
                    @Override
                    public void onFailure(Call<List<SubPropertyTypeList>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void subPropertyTypeListsdataShow() {


        String[] SubPropety = new String[subPropertyTypeLists.size()];

        for(int i=0; i<subPropertyTypeLists.size(); i++){
            //Storing names to string array
            SubPropety[i] = subPropertyTypeLists.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, SubPropety);

        subpropField.setAdapter(adapter);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!= null) {
            emailAddress = currentUser.getEmail();
        }

    }

    private void showPropertyList() {
        Call<List<ProperyTypeList>> propertytypecall = realStateApiServices.getPropertyTypes();
        propertytypecall.enqueue(new Callback<List<ProperyTypeList>>() {
            @Override
            public void onResponse(Call<List<ProperyTypeList>> call, Response<List<ProperyTypeList>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    properyTypeList =response.body();
                    showPropertyTypeSpiner();

                }else {
                    Toast.makeText(getContext(), "Eror", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ProperyTypeList>> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPropertyTypeSpiner() {


        String[] LOCATIONS = new String[properyTypeList.size()];

        for(int i=0; i<properyTypeList.size(); i++){
            //Storing names to string array
            LOCATIONS[i] = properyTypeList.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, LOCATIONS);

        propertyField.setAdapter(adapter);

    }

    private void sharedPreferencedataSave() {
        preferences =this.getContext().getSharedPreferences("MyFref",0);
      //  preferences = getActivity().getSharedPreferences("MyFref", 0);
        editor =preferences.edit();
        editor.putInt("OwnerId",ownerId);
        editor.putString("proepertyName",propertyNameField.getText().toString());
        editor.putString("sellprice",sellpriceField.getText().toString());
        editor.putInt("area",areaId);
        editor.putString("adress",adressField.getText().toString());
        editor.putBoolean("drwaing",drawingdinigID);
        editor.putInt("subproperty", subpropertyId);
        editor.putBoolean("parking", parkingId);
        editor.putInt("bedroom", Integer.parseInt(bedroomField.getText().toString()));
        editor.putInt("bathroom", Integer.parseInt(bathroomField.getText().toString()));
        editor.putInt("varandha", Integer.parseInt(varandahField.getText().toString()));
        editor.putInt("buildingYear", Integer.parseInt(buildingBuildYear.getText().toString()));
        editor.putInt("floodLevel", Integer.parseInt(floorLevelField.getText().toString()));
        editor.apply();
    }

    private void savedDataSellRegistration(PropertySellRegistrationDataSet property) {

    }


    public PropertySellRegistrationDataSet createProperty(){
        PropertySellRegistrationDataSet properties = new  PropertySellRegistrationDataSet();
        properties.setAddress("000");
      return properties;
    }



}