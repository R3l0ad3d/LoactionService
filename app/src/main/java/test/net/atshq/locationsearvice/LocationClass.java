package test.net.atshq.locationsearvice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by User on 2/6/2018.
 */

public class LocationClass {

    //ApiRespons apiRespons;


    private Context context;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    //private Data data;

    GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            locationRequest = LocationRequest.create()
                    .setInterval(5000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest,locationListener);
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };
    GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Logger.d("Location : ", location.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
            Date date = new Date(location.getTime());
            String  time = dateFormat.format(date);
            /*if(data.getUserId()>0){
                apiRespons.callLocationUpdate(data.getUserId(),location.getLatitude(),
                        location.getLongitude(),time)
                        .enqueue(new Callback<LocationUpdateResponse>() {
                            @Override
                            public void onResponse(Call<LocationUpdateResponse> call, Response<LocationUpdateResponse> response) {
                                if(response.isSuccessful()){
                                    Logger.d("LocationChange : ",response.body().toString());
                                }else {
                                    Logger.d("LocationChange : ","Error respons");
                                }
                            }

                            @Override
                            public void onFailure(Call<LocationUpdateResponse> call, Throwable t) {
                                Log.d("LocationChange Failure:",t.getMessage());
                            }
                        });
            }else {
                Logger.d("user id not found ....");
            }*/

        }
    };

    public LocationClass(Context context) {
        this.context = context;
        apiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .build();
        apiClient.connect();
        //apiRespons = ApiRespons.getInstance();

        //data = new Data(context);

        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());
        Log.d("Location Class : ","Location Class");
    }


}
