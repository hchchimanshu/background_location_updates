package com.himanshuhc.locationupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private TextView locationPermissionButton, backgroundPermissionButton, gettingLocationUpdatesButton, stopLocationUpdatesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /*
    initializing the views
     */
    private void initView() {
        locationPermissionButton = findViewById(R.id.location_permission_TV);
        locationPermissionButton.setOnClickListener(this);
        backgroundPermissionButton = findViewById(R.id.background_permission_TV);
        backgroundPermissionButton.setOnClickListener(this);
        gettingLocationUpdatesButton = findViewById(R.id.start_service_TV);
        gettingLocationUpdatesButton.setOnClickListener(this);
        stopLocationUpdatesButton = findViewById(R.id.stop_service_TV);
        stopLocationUpdatesButton.setOnClickListener(this);
    }

    /*
    for checking the permission
     */
    private void checkPermissionss() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(this, permissionsRequired, 2);
            }
        }
    }


    /*
    for checking if permission is granted or not
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.location_permission_TV :
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )==PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION},
                            2);
                }
                else
                {
                    checkPermissionss();
                }
                break;
            case R.id.background_permission_TV:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION )!=PackageManager.PERMISSION_GRANTED) {

//                    String[] permissionsRequiredBackground = new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION};
                    ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},1);
                }else
                    Toast.makeText(this, "Already Granted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.start_service_TV:
                if (!isMyServiceRunning(LocationUpdateService.class)) {
                    AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(this, LocationUpdateService.class);
                    /* 5000 for starting the service after 5 seconds */
                    PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, alarmIntent);

                    Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Already Started", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stop_service_TV:
                if (isMyServiceRunning(LocationUpdateService.class)) {
                    LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(this.locationCallback);
                    Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
                    stopService(new Intent(this,LocationUpdateService.class));
                }
                else{
                    Toast.makeText(this, "Not Running", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*
    for checking if service is running or not
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /*
    for location callbacks and printing the coordinates in logcat
     */
    public static LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location currentLocation = locationResult.getLastLocation();
            Log.d("Locations", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
        }
    };
}