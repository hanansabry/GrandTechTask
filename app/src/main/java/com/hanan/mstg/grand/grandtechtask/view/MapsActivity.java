package com.hanan.mstg.grand.grandtechtask.view;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hanan.mstg.grand.grandtechtask.BuildConfig;
import com.hanan.mstg.grand.grandtechtask.R;
import com.hanan.mstg.grand.grandtechtask.models.DirectionsResult;
import com.hanan.mstg.grand.grandtechtask.models.Leg;
import com.hanan.mstg.grand.grandtechtask.models.Location;
import com.hanan.mstg.grand.grandtechtask.models.Route;
import com.hanan.mstg.grand.grandtechtask.models.Step;
import com.hanan.mstg.grand.grandtechtask.mvp.MapsPresenter;
import com.hanan.mstg.grand.grandtechtask.mvp.MapsView;
import com.hanan.mstg.grand.grandtechtask.network.Service;
import com.hanan.mstg.grand.grandtechtask.util.RouteDecode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapsActivity extends BaseApp implements OnMapReadyCallback, MapsView {

    @Inject
    public Service service;
    private GoogleMap mMap;
    String origin = "29.951181,%2030.912111";
    String destination = "30.025940,%2031.015215";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getActivityComponent().inject(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapsPresenter presenter = new MapsPresenter(service, this);
        presenter.getDirectionResult(origin, destination, getString(R.string.google_maps_key));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getDirectionsResultSuccess(DirectionsResult directionsResult) {
        ArrayList<LatLng> routeList = new ArrayList<>();
        if (directionsResult.getRoutes().size() > 0) {
            ArrayList<LatLng> decodelist;
            Route route = directionsResult.getRoutes().get(0);
            if (route.getLegs().size() > 0) {
                Leg leg = route.getLegs().get(0);
                List<Step> steps = leg.getSteps();
                Location legStartLocation = leg.getStartLocation();
                Location legEndLocation = leg.getEndLocation();

                //mark the start address on the map with green color
                LatLng start = new LatLng(legStartLocation.getLat(), legStartLocation.getLng());
                mMap.addMarker(new MarkerOptions()
                        .position(start)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(leg.getStartAddress()));

                //mark the end address on the map with blue color
                LatLng end = new LatLng(legEndLocation.getLat(), legEndLocation.getLng());
                mMap.addMarker(new MarkerOptions()
                        .position(end)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title(leg.getEndAddress()));


                //mark stop points
                String polyline;
                for (Step step : steps) {
                    Location startLocation = step.getStartLocation();
                    Location endLocation = step.getEndLocation();
                    polyline = step.getPolyLine().getPoints();

                    //add marker for step end location
                    LatLng stepEnd = new LatLng(endLocation.getLat(), endLocation.getLng());
                    mMap.addMarker(new MarkerOptions().position(stepEnd).title(convertHtmlTextToPlaintext(step.getHtmlInstructions())));

                    decodelist = RouteDecode.decodePoly(polyline);
                    routeList.addAll(decodelist);
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 12));

                //draw the route
                if (routeList.size() > 0) {
                    PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);

                    for (int i = 0; i < routeList.size(); i++) {
                        rectLine.add(routeList.get(i));
                    }
                    // Adding route on the map
                    mMap.addPolyline(rectLine);
                }
            }
        }
    }

    public void showFragmentDialog(View view) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... strings) {
                String response = null;
                try {
                    URL url = new URL(strings[0]);

                    //create the url connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestMethod("POST");

                    int statusCode = connection.getResponseCode();
                    if (statusCode == 200) {

                        InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                        response = convertInputStreamToString(inputStream, Charset.forName("utf-8"));
                        Log.d("aboutus", response);

                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                Document document = Jsoup.parse(response);
                Element titleElement = document.select("title").get(0);
                Element contentElement = document.select("p").get(0);

                //show aboutus dialog
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                AboutUsDialogFragment.newInstance(titleElement.html(), contentElement.html()).show(ft, "aboutus");
            }
        }.execute(BuildConfig.ABOUTUS_CALL);
    }

    public String convertInputStreamToString(InputStream inputStream, Charset charset) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    public String convertHtmlTextToPlaintext(String htmlText) {
        return Html.fromHtml(htmlText).toString();
    }
}

