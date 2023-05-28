package com.himanshuhc.locationupdates;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.android.gms.location.LocationRequest;

import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationUpdateService extends Service {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;


    //onCreate
    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startLocationUpdates();

        return START_STICKY;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                MainActivity.locationCallback, Looper.myLooper());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initData() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(getApplicationContext());

    }
}
