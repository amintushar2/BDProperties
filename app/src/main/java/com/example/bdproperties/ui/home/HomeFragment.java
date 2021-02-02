package com.example.bdproperties.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bdproperties.Adapter.PropertyDataSetAdapter;
import com.example.bdproperties.R;
import com.example.bdproperties.pojos.AreasDataSetList;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.pojos.ProperyTypeList;
import com.example.bdproperties.pojos.SubPropertyTypeList;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    AutoCompleteTextView categoryAutoCompleteTextView,subCategoryAutoCompleteTextView,areaAutoCompleteText,maximumprice, minimumPrice;

    RecyclerView recyclerView;
    PropertyDataSetAdapter propertyDataSetAdapter;
    List <PropertySellRegistrationDataSet> propertySellRegistrationDataSetList;
    List<ProperyTypeList> properyTypeList;
    List<SubPropertyTypeList> subPropertyTypeLists;
    int propertyId,subPropertyId;
    ImageButton searchButton;
    ProgressDialog progressDialog;
    List<AreasDataSetList> areasDataSetList;
    int activeStatuse= 2;
    int pupose = 1;
    String maximumpriceText ,minimumpriceText;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState ) {
        View root =inflater.inflate(R.layout.fragment_home, viewGroup, false);
        categoryAutoCompleteTextView=root.findViewById(R.id.mainCateagory);
        subCategoryAutoCompleteTextView=root.findViewById(R.id.subcategory);
        recyclerView= root.findViewById(R.id.listRecyclerView);
        areaAutoCompleteText= root.findViewById(R.id.locations);
        maximumprice= root.findViewById(R.id.maximumPrice);
        minimumPrice= root.findViewById(R.id.minimumprice);
        searchButton= root.findViewById(R.id.searchButton);

        propertySellRegistrationDataSetList = new ArrayList<>();
        properyTypeList = new ArrayList<>();
        subPropertyTypeLists = new ArrayList<>();
        areasDataSetList = new ArrayList<>();

        progressDialog =new ProgressDialog(getContext());


        RealStateApiServices realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);

        progressDialog.show();
        propertyDataSetAdapter = new PropertyDataSetAdapter( getContext() ,propertySellRegistrationDataSetList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(propertyDataSetAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSearching();
            }
        });

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
        subCategoryAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subPropertyId =subPropertyTypeLists.get(position).getId();
            }
        });
        categoryAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                propertyId = properyTypeList.get(position).getId();
                Call<List<SubPropertyTypeList>>subPropertYtypeCall =realStateApiServices.getSubPropertyTypes(propertyId);
                subPropertYtypeCall.enqueue(new Callback<List<SubPropertyTypeList>>() {
                    @Override
                    public void onResponse(Call<List<SubPropertyTypeList>> call, Response<List<SubPropertyTypeList>> response) {
                        subPropertyTypeLists= response.body();
                        subCategoryAutoCompleteTextView.setText("");
                        subPropertyTypeListsdataShow();
                    }
                    @Override
                    public void onFailure(Call<List<SubPropertyTypeList>> call, Throwable t) {

                    }
                });
            }
        });


        Call<List<PropertySellRegistrationDataSet>>call = realStateApiServices.getPropertyLists(pupose,activeStatuse);
        call.enqueue(new Callback<List<PropertySellRegistrationDataSet>>() {
            @Override
            public void onResponse(Call<List<PropertySellRegistrationDataSet>> call, Response<List<PropertySellRegistrationDataSet>> response) {
               if (response.isSuccessful()) {
                   propertySellRegistrationDataSetList = response.body();
                   propertyDataSetAdapter.setPropertyDetails(propertySellRegistrationDataSetList);
               }else {
                   Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onFailure(Call<List<PropertySellRegistrationDataSet>> call, Throwable t) {

            }
        });



        return root;

    }

    private void setSearching() {
        String subCat = subCategoryAutoCompleteTextView.getText().toString();
        String areaCat = areaAutoCompleteText.getText().toString();
        String maxCat = maximumprice.getText().toString();
        String minCat = minimumPrice.getText().toString();
        if (!subCat.isEmpty()){
            callBySubCatagory();
           Toast.makeText(getContext(), ""+subCat, Toast.LENGTH_SHORT).show();
        }else if (!areaCat.isEmpty()){
            callbyArea();
            Toast.makeText(getContext(), ""+areaAutoCompleteText.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        }else if (!maxCat.isEmpty()||!minCat.isEmpty()){
            callByAreaMaxMinPrice();

            Toast.makeText(getContext(), "mnm:"+minCat+"max"+maxCat, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Please Select Any cat", Toast.LENGTH_SHORT).show();
        }
    }

    private void callByAreaMaxMinPrice() {

    }

    private void callbyArea() {
    }

    private void callBySubCatagory() {
    }

    private void subPropertyTypeListsdataShow() {
        String[] SubPropety = new String[subPropertyTypeLists.size()];

        for(int i=0; i<subPropertyTypeLists.size(); i++){
            //Storing names to string array
            SubPropety[i] = subPropertyTypeLists.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, SubPropety);

        subCategoryAutoCompleteTextView.setAdapter(adapter);
    }


    private void showPropertyTypeSpiner() {

        String[] LOCATIONS = new String[properyTypeList.size()];

        for(int i=0; i<properyTypeList.size(); i++){
            //Storing names to string array
            LOCATIONS[i] = properyTypeList.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, LOCATIONS);

        categoryAutoCompleteTextView.setAdapter(adapter);
    }
    private void showAreaSpiner() {

        String[] LOCATIONS = new String[areasDataSetList.size()];

        for(int i=0; i<areasDataSetList.size(); i++){
            //Storing names to string array
            LOCATIONS[i] = areasDataSetList.get(i).getArea1();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, LOCATIONS);

        areaAutoCompleteText.setAdapter(adapter);
    }
}