package com.android.rahul.findme;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class ShareActivity extends Activity {
    private float longitude=0,latitude=0;
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private String pathURL(float latitude,float longitude){
        return "https://www.google.com/maps/dir/?api=1&destination="+latitude+"%2C"+longitude;
    }
    private FusedLocationProviderClient mFusedLocationClient;

    private void getLocationInfo(){
        if (ContextCompat.checkSelfPermission(ShareActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ShareActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(ShareActivity.this,R.string.need_location,Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(ShareActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSIONS);

            } else {
                ActivityCompat.requestPermissions(ShareActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSIONS);
            }
        }else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = (float) location.getLatitude();
                                longitude = (float) location.getLongitude();
                                sendWhatsapp("THIS IS MY LOCATION. CLICK ON THE LINK TO SEE THE PATH " + pathURL(latitude, longitude));
                            }
                        }
                    });
        }
    }
    public void sendWhatsapp(String s){
        Intent sendIntent = new Intent();
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, s);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationInfo();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationInfo();
                } else {
                    sendWhatsapp("THE RECIPIENT REFUSED TO PROVIDE LOCATION ACCESS");
                }
                return;
            }

        }
    }
}
