package com.example.a16033774.demolocationdetection;

import android.Manifest;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    Button btnGetLastLocation;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetLastLocation = (Button) findViewById(R.id.btnGetLastLocation);
        client = LocationServices.getFusedLocationProviderClient(this);

        btnGetLastLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission() == true) {
                    Task<Location> task = client.getLastLocation();
                    task.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                String msg = "Lat : " + location.getLatitude() + "Lng : " + location.getLongitude();
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                String msg = "No Last Known Location found";
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "No permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
                }
            }
        });
    }


                private boolean checkPermission () {
                    int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                            MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                            MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                    if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                            || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
                        return true;
                    } else {
                        return false;
                    }
                }
}



