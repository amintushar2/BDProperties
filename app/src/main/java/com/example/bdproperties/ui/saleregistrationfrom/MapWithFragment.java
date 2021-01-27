package com.example.bdproperties.ui.saleregistrationfrom;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class MapWithFragment extends Fragment {
    private static final int LOCATION_REQUEST_CODE = 111;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    double updateLet;
    double updateLong;
    private Location lastKnownLocation;
    private static final String TAG="!" ;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;


    private boolean locationPermissionGranted;

    private GoogleMap map;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;


    private boolean isContinue = false;
    private boolean isGPS = false;
    Marker marker;

    Button btnUpload, btnMulUpload, btnPickImage, btnPickVideo;
    TextView gmapLocationViewText;
    String mediaPath, mediaPath1;
    MapFragment mapFragment;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog progressDialog;
    TextView str1, str2;
    int ownerId;
    PropertySellRegistrationDataSet propertySellRegistrationDataSet;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public MapWithFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map_with, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyFref", 0);
        ownerId = sharedPreferences.getInt("OwnerId", 0);
        sharedPreferences.getString("sellprice", "");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading...");

        btnMulUpload = root.findViewById(R.id.uploadMultiple);
        gmapLocationViewText = root.findViewById(R.id.textView3);
        btnPickImage = root.findViewById(R.id.pick_img);
        str1 = root.findViewById(R.id.filename1);
        str2 = root.findViewById(R.id.filename2);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            CheckPermission();
        } else {


    }


        btnMulUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        // Prompt the user for permission.
        getLocationPermission();
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        // Video must be low in Memory or need to be compressed before uploading...
        return  root;
    }

    private void updateLocationUI() {

        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                //             imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                uploadFile();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor =getActivity(). getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                mediaPath1 = cursor.getString(columnIndex);
                str2.setText(mediaPath1);
                // Set the Video Thumb in ImageView Previewing the Media
                //              imgView.setImageBitmap(getThumbnailPathForLocalFile(MainActivity.this, selectedVideo));
                cursor.close();

            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    private void uploadFile() {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part CoverPhoto = MultipartBody.Part.createFormData("CoverPhoto", file.getName(), requestBody);
        RequestBody OwnerId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ownerId));
        RealStateApiServices appApiClient = ApiClient.getClient().create(RealStateApiServices.class);

        Call<PropertySellRegistrationDataSet> call = appApiClient.setPropertyDetails(CoverPhoto,OwnerId);
        call.enqueue(new Callback<PropertySellRegistrationDataSet>() {
            @Override
            public void onResponse(Call<PropertySellRegistrationDataSet> call, Response<PropertySellRegistrationDataSet> response) {
                progressDialog.dismiss();
               PropertySellRegistrationDataSet propertySellRegistrationDataSet = response.body();

                Toast.makeText(getContext(), ""+propertySellRegistrationDataSet.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PropertySellRegistrationDataSet> call, Throwable t) {

            }
        });
    }



    public OnMapReadyCallback callback = new OnMapReadyCallback() {
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

            LatLng sydney = new LatLng(updateLet,updateLong);
            map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (CheckPermission()) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                map.setMyLocationEnabled(true);
            }
        }
    }
};


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }










    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            try {
                                if (lastKnownLocation != null) {
                                    updateLet = lastKnownLocation.getLatitude();
                                    updateLong = lastKnownLocation.getLongitude();

                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(lastKnownLocation.getLatitude(),
                                                    lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                    map.addMarker(new MarkerOptions().position(new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude())).title("Marker in Sydney"));
                                    map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                        @Override
                                        public void onMapLongClick(LatLng latLng) {
                                                map.clear();
                                            marker = map.addMarker(new MarkerOptions()
                                                    .position(
                                                            new LatLng(latLng.latitude,
                                                                    latLng.longitude))
                                                    .draggable(true).visible(true));
                                            updateLet = latLng.latitude;
                                            updateLong=latLng.longitude;

                                            Geocoder geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                                            List<Address> addresses = null;
                                            try {
                                                addresses = geo.getFromLocation(updateLet, updateLong, 1);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if (addresses.isEmpty()) {
                                                Toast.makeText(getContext(), "Waiting for Location", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                if (addresses.size() > 0) {
                                                    gmapLocationViewText.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                                                    map.getUiSettings().setZoomControlsEnabled(true);
                                                    map.getUiSettings().setMapToolbarEnabled(true);
                                                }
                                            }
                                        }
                                    });
                                    try {
                                        Geocoder geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                                        List<Address> addresses = geo.getFromLocation(updateLet, updateLong, 1);
                                        if (addresses.isEmpty()) {
                                            Toast.makeText(getContext(), "Waiting for Location", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            if (addresses.size() > 0) {
                                                gmapLocationViewText.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                                            }
                                        }
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace(); // getFromLocation() may sometimes fail
                                    }
                                }
                             else{
                                Log.d(TAG, "Current location is null. Using defaults.");
                                Log.e(TAG, "Exception: %s", task.getException());
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                        }
                });
            }else {
                Toast.makeText(getActivity(), "NotFound", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private boolean CheckPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_REQUEST_CODE);
            return false;
        }
        return true;
    }
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }

}

}