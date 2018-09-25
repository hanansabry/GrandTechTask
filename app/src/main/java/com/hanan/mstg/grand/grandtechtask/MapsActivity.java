package com.hanan.mstg.grand.grandtechtask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hanan.mstg.grand.grandtechtask.models.DirectionsResult;
import com.hanan.mstg.grand.grandtechtask.models.Leg;
import com.hanan.mstg.grand.grandtechtask.models.Location;
import com.hanan.mstg.grand.grandtechtask.models.Route;
import com.hanan.mstg.grand.grandtechtask.models.Step;
import com.hanan.mstg.grand.grandtechtask.mvp.MapsPresenter;
import com.hanan.mstg.grand.grandtechtask.mvp.MapsView;
import com.hanan.mstg.grand.grandtechtask.network.Service;
import com.hanan.mstg.grand.grandtechtask.util.RouteDecode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapsActivity extends BaseApp implements OnMapReadyCallback, MapsView {

    @Inject
    public Service service;
    private GoogleMap mMap;

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
        presenter.getDirectionResult("29.951181,%2030.912111",
                "30.025940,%2031.015215", getString(R.string.google_maps_key));
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

//    public void getApiResult() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://maps.googleapis.com/maps/api/directions/")
//                .build();
//
//
//        GoogleMapsApiService mapsApiService = retrofit.create(GoogleMapsApiService.class);
//        Observable<DirectionsResult> directions = mapsApiService.getDirections("29.951181,%2030.912111",
//                "30.025940,%2031.015215", getString(R.string.google_maps_key));
//
////        directions.subscribeOn(Schedulers.newThread())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(directionResults -> {
////                    Log.d("DirectionsResult", Html.fromHtml(directionResults.getRoutes()
////                            .get(0).getLegs()
////                            .get(0).getSteps()
////                            .get(12).getHtmlInstructions()).toString());
////                });
//        directions.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(directionResults -> {
//                    ArrayList<LatLng> routeList = new ArrayList<>();
//                    if (directionResults.getRoutes().size() > 0) {
//                        ArrayList<LatLng> decodelist;
//                        Route route = directionResults.getRoutes().get(0);
//                        if (route.getLegs().size() > 0) {
//                            Leg leg = route.getLegs().get(0);
//                            List<Step> steps = leg.getSteps();
//                            Location legStartLocation = leg.getStartLocation();
//                            Location legEndLocation = leg.getEndLocation();
//
//                            //mark the start address on the map with green color
//                            LatLng start = new LatLng(legStartLocation.getLat(), legStartLocation.getLng());
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(start)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//                                    .title(leg.getStartAddress()));
//
//                            //mark the end address on the map with blue color
//                            LatLng end = new LatLng(legEndLocation.getLat(), legEndLocation.getLng());
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(end)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                                    .title(leg.getEndAddress()));
//
//                            String polyline;
//                            for (Step step : steps){
//                                Location startLocation = step.getStartLocation();
//                                Location endLocation = step.getEndLocation();
//                                polyline = step.getPolyLine().getPoints();
//
//                                //add marker for step start location
////                                LatLng stepStart = new LatLng(startLocation.getLat(), startLocation.getLng());
////                                mMap.addMarker(new MarkerOptions().position(stepStart).title(convertHtmlTextToPlaintext(step.getHtmlInstructions())));
////
////                                //add marker for step end location
//                                LatLng stepEnd = new LatLng(endLocation.getLat(), endLocation.getLng());
//                                mMap.addMarker(new MarkerOptions().position(stepEnd).title(convertHtmlTextToPlaintext(step.getHtmlInstructions())));
//
//                                decodelist = RouteDecode.decodePoly(polyline);
//                                routeList.addAll(decodelist);
//                            }
//
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,12));
//
//                            if(routeList.size()>0){
//                                PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);
//
//                                for (int i = 0; i < routeList.size(); i++) {
//                                    rectLine.add(routeList.get(i));
//                                }
//                                // Adding route on the map
//                                mMap.addPolyline(rectLine);
//                            }
//                        }
//                    }
//                });
//    }

    public String convertHtmlTextToPlaintext(String htmlText){
        return Html.fromHtml(htmlText).toString();
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

                String polyline;
                for (Step step : steps){
                    Location startLocation = step.getStartLocation();
                    Location endLocation = step.getEndLocation();
                    polyline = step.getPolyLine().getPoints();

                    //add marker for step start location
//                                LatLng stepStart = new LatLng(startLocation.getLat(), startLocation.getLng());
//                                mMap.addMarker(new MarkerOptions().position(stepStart).title(convertHtmlTextToPlaintext(step.getHtmlInstructions())));
//
//                                //add marker for step end location
                    LatLng stepEnd = new LatLng(endLocation.getLat(), endLocation.getLng());
                    mMap.addMarker(new MarkerOptions().position(stepEnd).title(convertHtmlTextToPlaintext(step.getHtmlInstructions())));

                    decodelist = RouteDecode.decodePoly(polyline);
                    routeList.addAll(decodelist);
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,12));

                if(routeList.size()>0){
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
        String title = "About Us";
        String content = "Grand Technology, a subsidiary of Mohammed Saif Thabet Group, was established in 2006 as a next-generation digital solutions company by a team of professionals, passionate about providing latest technology and regional expertise in the field of communications, and information technology to help our clients design for the future while evolving their existing businesses.   Grand Technology has come a long way to be recognized as one of the leading solutions and content providers in Yemen.</p>\\r\\n";
        //show aboutus dialog
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AboutUsDialogFragment.newInstance(title, content).show(ft, "aboutus");

    }
}
