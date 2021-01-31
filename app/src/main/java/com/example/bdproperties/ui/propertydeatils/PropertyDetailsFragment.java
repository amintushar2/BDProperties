package com.example.bdproperties.ui.propertydeatils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdproperties.Adapter.SlideViewPager;
import com.example.bdproperties.MainActivity;
import com.example.bdproperties.R;
import com.example.bdproperties.pojos.BookingPost;
import com.example.bdproperties.pojos.OwnerDetailsDataSet;
import com.example.bdproperties.pojos.PropertyImages;
import com.example.bdproperties.pojos.ProperyDetailsDataSets;
import com.example.bdproperties.pojos.SubPropertyTypeList;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailsFragment extends Fragment  {
    int propertyId;
    TextView PropertyDetails , areaNameText, bedRoomDetails,washRoomDetails,varandhaDetails,drawingDinigDetails,parkingDetails,aaAddressetTableRaw;
    ProperyDetailsDataSets propertySellRegistrationDataSet;
    ProgressDialog progressDialog;
    String propertyName;
    OwnerDetailsDataSet ownerDetailsDataSet;
    GoogleMap map;
    private LinearLayout mdots;
    double upLatLat,upLong;
    LatLng sydney;
    private TextView[] mdotsview;
    PropertyImages propertyImages;
    List<PropertyImages> propertyImagesList;
    ViewPager viewPager;
    SlideViewPager adapter ;
    int ownerid;
    Button BookingButton;
    RealStateApiServices realStateApiServices;
    FirebaseUser currentUser;
    String emailAddress;

    public PropertyDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_details, container, false);
        PropertyDetails=view.findViewById(R.id.properDetailsName);
        areaNameText=view.findViewById(R.id.areasetTableRaw);
        bedRoomDetails=view.findViewById(R.id.bedRoomsettTableRaw);
        washRoomDetails=view.findViewById(R.id.bathRoomseTableRaw);
        varandhaDetails=view.findViewById(R.id.varandasetTableRaw);
        drawingDinigDetails=view.findViewById(R.id.drawingsetTableRaw);
        parkingDetails=view.findViewById(R.id.dparkingTableRaw);
        aaAddressetTableRaw=view.findViewById(R.id.aAddressetTableRaw);
        BookingButton = view.findViewById(R.id.bookingButton);
        progressDialog = new ProgressDialog(getContext());
        progressDialog = new ProgressDialog(getContext());
        mdots=view.findViewById(R.id.mdots);
        Bundle bundle =getArguments();
        propertyId= bundle.getInt("propertyID");
        upLatLat= bundle.getDouble("lattitude");
        upLong= bundle.getDouble("langitute");
        propertyImagesList = new ArrayList<>();
         viewPager = view.findViewById(R.id.imageViewPager);
        Timer timer= new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);
        Toast.makeText(getContext(), ""+upLatLat, Toast.LENGTH_SHORT).show();

        currentUser =  FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);

        progressDialog.show();
        realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);

        realStateApiServices.getpropertyDetails(propertyId).enqueue(new Callback<ProperyDetailsDataSets>() {
         @SuppressLint("SetTextI18n")
         @Override
         public void onResponse( Call<ProperyDetailsDataSets> call, Response<ProperyDetailsDataSets> response) {

             propertySellRegistrationDataSet = response.body();

             Log.e("onResponse",response.toString());
             propertyName = propertySellRegistrationDataSet.getPropertyName();
             PropertyDetails.setText(propertySellRegistrationDataSet.getPropertyName());
             areaNameText.setText(propertySellRegistrationDataSet.getAreaName());
             aaAddressetTableRaw.setText(propertySellRegistrationDataSet.getAddress());
             bedRoomDetails.setText(propertySellRegistrationDataSet.getBedRoom().toString());
             washRoomDetails.setText(propertySellRegistrationDataSet.getWashRoom().toString());


             if ((propertySellRegistrationDataSet.getDrawingRoom() == true)){
                 drawingDinigDetails.setText("Yes");
             }else {
                 drawingDinigDetails.setText("No");
             }
             if (propertySellRegistrationDataSet.getParking()==true ){
                 drawingDinigDetails.setText("Yes");
             }else {
                 drawingDinigDetails.setText("No");
             }
             varandhaDetails.setText(propertySellRegistrationDataSet.getVaranda().toString());
             upLatLat= Double.parseDouble(propertySellRegistrationDataSet.getLatitude());
             upLong= Double.parseDouble(propertySellRegistrationDataSet.getLongitude());
             sydney = new LatLng(upLatLat,upLong);
             map.addMarker(new MarkerOptions().position(sydney).title(propertyName+"/n"));
             map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                     new LatLng(upLatLat,
                             upLong), 15));
             map.getUiSettings().setMapToolbarEnabled(true);
             map.getUiSettings().setAllGesturesEnabled(true);
             map.getUiSettings().setZoomControlsEnabled(true);
             map.getUiSettings().setZoomGesturesEnabled(true);
             map.getUiSettings().setRotateGesturesEnabled(true);
             map.getUiSettings().setScrollGesturesEnabled(true);
             map.getUiSettings().setTiltGesturesEnabled(true);
             map.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);


         }

         @Override
         public void onFailure(@NotNull Call<ProperyDetailsDataSets> call, Throwable t) {
             Toast.makeText(getContext(), "Faild", Toast.LENGTH_SHORT).show();
             Log.e("onFailure",t.toString());

         }
     });

        realStateApiServices.getPropertyImages(propertyId).enqueue(new Callback<List<PropertyImages>>() {
            @Override
            public void onResponse(Call<List<PropertyImages>> call, Response<List<PropertyImages>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    propertyImagesList =response.body();
                    adapter.setImageDetails(propertyImagesList);

                    Log.d("prop", String.valueOf(propertyImagesList.size()));
                }else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PropertyImages>> call, Throwable t) {

            }
        });
            showImageFile();



            BookingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser ==null){
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.loginFragment);
                    }else {
                        postBooking(createBookingPost());
                    }
                }
            });
        return  view;
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            emailAddress =currentUser.getEmail();
            userIdget();
        }
    }

    private void userIdget() {
        realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);
        realStateApiServices.getOwnerDetails(emailAddress).enqueue(new Callback<OwnerDetailsDataSet>() {
            @Override
            public void onResponse(Call<OwnerDetailsDataSet> call, Response<OwnerDetailsDataSet> response) {
                if (response.isSuccessful()){
                    ownerDetailsDataSet =response.body();
                    ownerid = ownerDetailsDataSet.getId();
                }
            }

            @Override
            public void onFailure(Call<OwnerDetailsDataSet> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void postBooking(BookingPost bookingPost) {
        realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);
        Call<Void>call = realStateApiServices.setBookingPost(bookingPost);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()){
                Toast.makeText(getContext(), "Booked Your Property", Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
        map=googleMap;



        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragmentView);
        if (mapFragment != null) {

                mapFragment.getMapAsync(callback);


        }
    }

    void   showImageFile(){
//        String[] imageUrls  = new String[propertyImagesList.size()];
//
//        for(int i=0; i<propertyImagesList.size(); i++){
//            //Storing names to string array
//            imageUrls [i] = propertyImagesList.get(i).getFilePath();
//        }
        adapter = new SlideViewPager(getActivity(), propertyImagesList );
        viewPager.setAdapter(adapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(onPageChangeListener);

    }
    public  void  addDotsIndicator(int position){

        mdotsview = new TextView[propertyImagesList.size()];
        mdots.removeAllViews();
        for (int i = 0 ;i < mdotsview.length;i++){
            mdotsview[i] = new TextView(getContext());
            mdotsview[i].setText(Html.fromHtml("&#8226"));
            mdotsview[i].setTextSize(60);
            mdotsview[i].setTextColor(getResources().getColor(R.color.design_default_color_secondary));
            mdots.addView(mdotsview[i]);
        }
        if (mdotsview.length>0){
            mdotsview[position].setTextColor(getResources().getColor(R.color.black));
            // mdotsview[position].setText(40);
        }


    }



    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    public  class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem()==0){
                            viewPager.setCurrentItem(1);
                        }else if(viewPager.getCurrentItem()==1){
                            viewPager.setCurrentItem(2);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public BookingPost createBookingPost(){
        BookingPost bookingPost = new BookingPost();
        bookingPost.setOwnerId(ownerid);
        bookingPost.setPropertyId(propertyId);
        return bookingPost;

    }
    }



