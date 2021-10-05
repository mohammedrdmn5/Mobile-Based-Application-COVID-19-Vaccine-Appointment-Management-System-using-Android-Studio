package com.example.groupassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView mRecyclerView;
    int[] numberimages = {R.drawable.feverpng, R.drawable.coughpng, R.drawable.headachepng,
            R.drawable.nauseapng, R.drawable.sore_throatpng, R.drawable.short_breathpng};


    ExpandableHeightGridView mGridView;


    String[] PreventString;
    int[] image = {R.drawable.hand_wash,
            R.drawable.ic_distance,
            R.drawable.wear_face_mask,
            R.drawable.ic_touch_face,
            R.drawable.avoid_contact,
            R.drawable.clean_pets,
            R.drawable.sleep_well};

    private GoogleMap map;
    private ScrollView mScrollView;
    TextView mtextView;

    SharedPreferences shareRef; // SharedPreferences

    BottomNavigationView navigationView;
    VideoView mvideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SharedPreferences
        shareRef = getSharedPreferences("shareRefUserInfo", Context.MODE_PRIVATE);
        checkUserLoginStatus(); // call the method to cehck if the user has permession to see this page or send the user to login page
        try {
            createUserAdminFirstRunPorgramm();// add the admin user if first time run the app
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mRecyclerView = findViewById(R.id.rv_symptoms);
        Adapter adapter;
        adapter = new Adapter(this, numberimages);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        PreventString = getResources().getStringArray(R.array.prevent_list);
        mGridView = (ExpandableHeightGridView) findViewById(R.id.gv_prevent);
        mGridView.setExpanded(true);


        mGridView = (ExpandableHeightGridView) findViewById(R.id.gv_prevent);
        Prevent_Adapter prevent_adapter = new Prevent_Adapter(getApplicationContext(), image, PreventString);
        mGridView.setAdapter(prevent_adapter);
        prevent_adapter.notifyDataSetChanged();


        getdataAPICases();


        TouchMapFragment mapfrag;
        mapfrag = ((TouchMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        assert mapfrag != null;
        mapfrag.getMapAsync(this);
        mScrollView = (ScrollView) findViewById(R.id.main_scrollview);

        ((TouchMapFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map))).setListener(new TouchMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });


        videoplayer();

    }

    private void videoplayer() {
        //Assign Variable
        mvideoView = findViewById(R.id.videoview_treatment);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.recognizing_symptomsof_coronavirus;
        //Parse Video Link
        Uri uri = Uri.parse(path);
        //Set MediaController And Video Uri
        mvideoView.setVideoURI(uri);
        //Creating MediaController
        MediaController mediaController = new MediaController(MainActivity.this);
        mvideoView.setMediaController(mediaController);
        mediaController.setAnchorView(mvideoView);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        checkUserLoginStatus();
        // new code for navigation bar
        NavigationItemSelected();
    }

    // for check if the user if logged in or now , if no , send the user to login page
    public void checkUserLoginStatus() {
        if (shareRef.getBoolean("isLogin", false) == false) // if the login is false means the user not logedin , send to the login interface
        {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void createUserAdminFirstRunPorgramm() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        DatabaseManager myDB = new DatabaseManager(MainActivity.this);
        myDB.CreateMainAdmin();
    }


    private void NavigationItemSelected() {
        BottomNavigationView navigationView;
        //initialize navigation and assign variable
        navigationView = findViewById(R.id.bottom_navigation);
        // set covid page selected
        navigationView.setSelectedItemId(R.id.nav_covid);
        //this to know which of navigation bar are selected to go next
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_covid:
                        return true;
                    case R.id.nav_vaccine:
                        startActivity(new Intent(getApplicationContext(), Vaccinations_COVID.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(MainActivity.this, Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Unexpected value: " + item.getItemId(), Toast.LENGTH_LONG);
                }
                return false;
            }
        });
    }


    public void openByBrowser(View view) {
        Uri uri = Uri.parse("https://bit.ly/3qBSRYQ");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Find an activity to hand the intent and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    public void getdataAPICases() {

        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://api.apify.com/v2/actor-tasks/HxdPB9Z3Djb0CwZwo/runs/last/dataset/items?token=FhkN7mSjqtvswvFJg2TkbAf4u";
//        String url ="https://api.apify.com/v2/datasets/7Fdb90FMDLZir2ROo/items?format=json&clean=1";
        String url = "https://api.apify.com/v2/datasets/CxwYWXmxIRSJRFvzH/items?clean=true&format=json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                for (int i = response.length() - 1; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int testedPositive = jsonObject.getInt("testedPositive");
                        mtextView = findViewById(R.id.tv_confirmed_cases_num);
                        mtextView.setText(String.valueOf(testedPositive));

                        int recovered = jsonObject.getInt("recovered");
                        mtextView = findViewById(R.id.tv_recovered_cases_num);
                        mtextView.setText(String.valueOf(recovered));

                        int activeCases = jsonObject.getInt("activeCases");
                        mtextView = findViewById(R.id.tv_new_case_num);
                        mtextView.setText(String.valueOf(activeCases));

                        int deceased = jsonObject.getInt("deceased");
                        mtextView = findViewById(R.id.tv_death_case_num);
                        mtextView.setText(String.valueOf(deceased));

                    } catch (JSONException e) {
                        Log.e("API values ", "That didn't work! " + e);
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  Handle error
                Log.e("API values ", "That didn't work! " + error);
            }
        });
        queue.add(jsonArrayRequest);


    }


    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        ArrayList<String> arrayList_API_title = new ArrayList<>(), arrayList_API_lat = new ArrayList<>(), arrayList_API_lng = new ArrayList<>();
        ArrayList<LatLng> place = new ArrayList<>();
        map = googleMap;


        // this static data when API connection fail
        LatLng points = new LatLng(6.1505972, 100.3656689);
        map.addMarker(new MarkerOptions().position(points).title("Stadium Sultan Abdul Halim"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(6.4147448, 100.1949503);
        map.addMarker(new MarkerOptions().position(points).title("Dewan 2020"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.807668, 101.7375239);
        map.addMarker(new MarkerOptions().position(points).title("Klinik Masya"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.8097745, 101.7384872);
        map.addMarker(new MarkerOptions().position(points).title("Dewan Serbaguna Bandar Baru Salak Tinggi"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.8119392, 101.7375569);
        map.addMarker(new MarkerOptions().position(points).title("Klinik Dr Nina"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.9761982, 101.720225);
        map.addMarker(new MarkerOptions().position(points).title("Serdang Hospital"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.8192853, 101.6940983);
        map.addMarker(new MarkerOptions().position(points).title("Klinik Hazra Medic"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.8191638, 101.68967);
        map.addMarker(new MarkerOptions().position(points).title("SPoliklinik Medi Ihsan Saujana KLIA"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.9290529, 101.6741956);
        map.addMarker(new MarkerOptions().position(points).title("Putrajaya Hospital"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));

        points = new LatLng(2.926907, 101.6739021);
        map.addMarker(new MarkerOptions().position(points).title("National Cancer Institute"));
        map.moveCamera(CameraUpdateFactory.newLatLng(points));
        // Instantiate the RequestQueue.

//        String url = "https://api.apify.com/v2/datasets/PYZm0r4LhS8bOaZT3/items?clean=true&format=json";
        String url = "https://api.apify.com/v2/datasets/h26NEAXUHc6Jx9CND/items?clean=true&format=json";
        RequestQueue mqueue = Volley.newRequestQueue(this);

// Request a JsonArray response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String searchPageUrl = jsonObject.getString("url");
                                String[] arrpage = searchPageUrl.substring(searchPageUrl.lastIndexOf("@") + 1).split(",");
                                arrayList_API_title.add(title);
                                arrayList_API_lat.add(arrpage[0]);
                                arrayList_API_lng.add(arrpage[1]);
                            } catch (JSONException e) {
                                Log.e("API values ", "That didn't work! " + e);
                            }
                        }
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                place.add(i, new LatLng(Double.parseDouble(arrayList_API_lat.get(i)), Double.parseDouble(arrayList_API_lng.get(i))));
                                map.addMarker(new MarkerOptions().position(place.get(i)).title(arrayList_API_title.get(i)));
                                map.moveCamera(CameraUpdateFactory.newLatLng(place.get(i)));

                            } catch (IndexOutOfBoundsException e) {
                                Log.e("API values ", "That didn't work! " + e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("API values ", "That didn't work!");

                    }
                });
        // Add the request to the RequestQueue.
        mqueue.add(request);
    }


    public void open_about_vaccinations(View view) {
        Intent intent = new Intent(MainActivity.this, Vaccinations_COVID.class);
        startActivity(intent);
    }

}

