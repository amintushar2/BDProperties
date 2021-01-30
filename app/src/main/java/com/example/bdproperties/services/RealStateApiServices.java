package com.example.bdproperties.services;

import com.example.bdproperties.pojos.AreasDataSetList;
import com.example.bdproperties.pojos.OwnerDetailsDataSet;
import com.example.bdproperties.pojos.PropertyImages;
import com.example.bdproperties.pojos.PropertySellRegistrationDataSet;
import com.example.bdproperties.pojos.ProperyDetailsDataSets;
import com.example.bdproperties.pojos.ProperyTypeList;
import com.example.bdproperties.pojos.SubPropertyTypeList;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RealStateApiServices {

    @POST("api/Owners/PostOwner")
    Call<Void>setOwnerDetails(@Body OwnerDetailsDataSet ownerDetailsDataSet);

    @GET("api/Property/Get?")
    Call<List<PropertySellRegistrationDataSet>>getPropertyLists(@Query("purpose") int purpose,@Query("status")int status);

    @GET("api/Owners/Get?")
    Call<OwnerDetailsDataSet>getOwnerDetails(@Query("email") String email);

    @GET("api/Property/GetPropertyPhotos?")
    Call<List<PropertyImages>>getPropertyImages(@Query("propertyId") int propertyId);

    @Multipart
    @POST("api/Property/PostProperty")
    Call<PropertySellRegistrationDataSet>setPropertyDetails(@Part MultipartBody.Part CoverPhoto,
            @Part("OwnerId") RequestBody OwnerId
            ,
            @Part("PropertyName") RequestBody PropertyName,
            @Part("Area") RequestBody Area,
            @Part("Address") RequestBody Address,
            @Part("Longitude") RequestBody Longitude ,
            @Part("Latitude") RequestBody Latitude,
            @Part("SalePrice") RequestBody SalePrice,
            @Part("SubPropertyType") RequestBody SubPropertyType,
            @Part("BedRoom") RequestBody BedRoom,
            @Part("WashRoom") RequestBody WashRoom,
            @Part("Varanda") RequestBody Varanda,
            @Part("DrawingRoom") Boolean DrawingRoom,
            @Part("Parking ") Boolean Parking,
            @Part("FloorLevel") RequestBody FloorLevel
//            ,
//            @Part("Preference") RequestBody Preference
                                        );

    @GET("api/Data/GetPropertyType")
    Call<List<ProperyTypeList>>getPropertyTypes();

    @GET("api/Data/GetPropertyByType/{id}")
    Call<List<SubPropertyTypeList>>getSubPropertyTypes(@Path("id")int id);

    @GET("api/Data/GetArea")
    Call<List<AreasDataSetList>>getAreas();

    @GET("api/Property/GetById/{id}")
    Call<ProperyDetailsDataSets>getpropertyDetails(@Path("id")int id);

    @Multipart
    @POST("api/Property/PostPropertyPhotos")
    Call<ResponseBody> uploadMultipleFiles(
            @Part("propertyId") RequestBody propertyId,
            @Part("details1") RequestBody details1,
            @Part("details2") RequestBody details2,
            @Part("details3") RequestBody details3,
            @Part MultipartBody.Part photo,
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2);

}
