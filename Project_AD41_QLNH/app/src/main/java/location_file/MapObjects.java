package location_file;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.project_ad41_qlnh.DeFile;
import com.example.project_ad41_qlnh.R;
import com.here.sdk.core.Anchor2D;
import com.here.sdk.core.Color;
import com.here.sdk.core.GeoCircle;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.GeoPolygon;
import com.here.sdk.core.GeoPolyline;
import com.here.sdk.core.Metadata;
import com.here.sdk.core.errors.InstantiationErrorException;
import com.here.sdk.mapview.MapCamera;
import com.here.sdk.mapview.MapImage;
import com.here.sdk.mapview.MapImageFactory;
import com.here.sdk.mapview.MapMarker;
import com.here.sdk.mapview.MapPolygon;
import com.here.sdk.mapview.MapPolyline;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapView;
import com.here.sdk.routing.CalculateRouteCallback;
import com.here.sdk.routing.CarOptions;
import com.here.sdk.routing.Maneuver;
import com.here.sdk.routing.ManeuverAction;
import com.here.sdk.routing.Route;
import com.here.sdk.routing.RoutingEngine;
import com.here.sdk.routing.RoutingError;
import com.here.sdk.routing.Section;
import com.here.sdk.routing.Waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MapObjects {
    private Context context;
    private MapView mapView;
    private MapScene mapScene;
    private MapPolyline mapPolyline;
    private MapPolygon mapPolygon;
    private MapPolygon mapCircle;
    private GeoCoordinates currrent_Location;
    private final List<MapMarker> mapMarkerList = new ArrayList<>();
    private final List<MapPolyline> mapPolylines = new ArrayList<>();
    private RoutingEngine routingEngine;
    private GeoCoordinates startGeoCoordinates;
    private GeoCoordinates destinationGeoCoordinates;
    List<GeoCoordinates> geoCoordinatesList;

    List<Route> routeList;


    public MapObjects(Context context, MapView mapView, GeoCoordinates current, List<GeoCoordinates> list) {
        this.context = context;
        this.mapView = mapView;
        this.currrent_Location = current;
        this.geoCoordinatesList = list;
        MapCamera camera = mapView.getCamera();
        double distanceToEarthInMeters = 3000;

        startGeoCoordinates = this.currrent_Location;
        destinationGeoCoordinates = new GeoCoordinates(DeFile.GOAL_LOCATION_Latitude, DeFile.GOAL_LOCATION_Longitude);



        camera.zoomTo(distanceToEarthInMeters);
        camera.lookAt(currrent_Location, distanceToEarthInMeters);
        //camera.lookAt(new GeoCoordinates(52.54014, 13.37958), distanceToEarthInMeters);


        // Add circle to indicate map center.
        addCirclePolygon(current);
        addCirclePolygon_List(geoCoordinatesList);
       // addPOIMapMarker(current);
       // addPhotoMapMarker(this.currrent_Location);

        // chỉ đường

        try {
            routingEngine = new RoutingEngine();
        } catch (InstantiationErrorException e) {
            throw new RuntimeException("Initialization of RoutingEngine failed: " + e.error.name());
        }
        getListRoute();
    }


    public void showMapPolygon() {
        clearMap();
        mapPolygon = createPolygon();
        mapScene.addMapPolygon(mapPolygon);
    }

    public void showMapCircle() {
        clearMap();
        mapCircle = createMapCircle();
        mapScene.addMapPolygon(mapCircle);
    }

    public void clearMapButtonClicked() {
        clearMap();
    }

    private MapPolyline createPolyline() {
        ArrayList<GeoCoordinates> coordinates = new ArrayList<>();
        coordinates.add(new GeoCoordinates(52.53032, 13.37409));
        coordinates.add(new GeoCoordinates(52.5309, 13.3946));
        coordinates.add(new GeoCoordinates(52.53894, 13.39194));
        coordinates.add(new GeoCoordinates(52.54014, 13.37958));

        GeoPolyline geoPolyline;
        try {
            geoPolyline = new GeoPolyline(coordinates);
        } catch (InstantiationErrorException e) {
            // Less than two vertices.
            return null;
        }

        float widthInPixels = 20;
        Color lineColor = Color.valueOf(0, 0.56f, 0.54f, 0.63f); // RGBA
        MapPolyline mapPolyline = new MapPolyline(geoPolyline, widthInPixels, lineColor);

        return mapPolyline;
    }

    private MapPolygon createPolygon() {
        ArrayList<GeoCoordinates> coordinates = new ArrayList<>();
        // Note that a polygon requires a clockwise order of the coordinates.
        coordinates.add(new GeoCoordinates(52.54014, 13.37958));
        coordinates.add(new GeoCoordinates(52.53894, 13.39194));
        coordinates.add(new GeoCoordinates(52.5309, 13.3946));
        coordinates.add(new GeoCoordinates(52.53032, 13.37409));

        GeoPolygon geoPolygon;
        try {
            geoPolygon = new GeoPolygon(coordinates);
        } catch (InstantiationErrorException e) {
            // Less than three vertices.
            return null;
        }

        Color fillColor = Color.valueOf(0, 0.56f, 0.54f, 0.63f); // RGBA
        MapPolygon mapPolygon = new MapPolygon(geoPolygon, fillColor);

        return mapPolygon;
    }

    private MapPolygon createMapCircle() {
        float radiusInMeters = 300;
        GeoCircle geoCircle = new GeoCircle(new GeoCoordinates(52.530932, 13.384915), radiusInMeters);

        GeoPolygon geoPolygon = new GeoPolygon(geoCircle);
        Color fillColor = Color.valueOf(0.5f, 0.56f, 0f, 0.63f); // RGBA
        MapPolygon mapPolygon = new MapPolygon(geoPolygon, fillColor);

        return mapPolygon;
    }

    private void clearMap() {
        if (mapPolyline != null) {
            mapScene.removeMapPolyline(mapPolyline);
        }

        if (mapPolygon != null) {
            mapScene.removeMapPolygon(mapPolygon);
        }

        if (mapCircle != null) {
            mapScene.removeMapPolygon(mapCircle);
        }
    }

    private void addCirclePolygon(GeoCoordinates geoCoordinates) {
        float radiusInMeters = 50;
        GeoCircle geoCircle = new GeoCircle(geoCoordinates, radiusInMeters);

        GeoPolygon geoPolygon = new GeoPolygon(geoCircle);
        com.here.sdk.core.Color fillColor =
                new com.here.sdk.core.Color((short) 0x00, (short) 0x90, (short) 0x8A, (short) 0xA0);
        MapPolygon mapPolygon = new MapPolygon(geoPolygon, fillColor);

        mapView.getMapScene().addMapPolygon(mapPolygon);
    }

    private void addCirclePolygon_List(List<GeoCoordinates> list) {
        for (int i = 0; i < list.size(); i++) {
            GeoCoordinates geoCoordinates = list.get(i);
            addCirclePolygon(geoCoordinates);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addRoute() {
        clearMap();
        double min = getMinRoute();
        for(int i = 0; i<routeList.size(); i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if(routeList.get(i).getLengthInMeters() == min){
                    showRouteDetails(routeList.get(i));
                    showRouteOnMap(routeList.get(i));
                    return;
                }
            }
        }

//        Waypoint startWaypoint = new Waypoint(startGeoCoordinates);
//        for (int i = 0; i < geoCoordinatesList.size(); i++) {
//            Waypoint destinationWaypoint = new Waypoint(geoCoordinatesList.get(i));
//
//            List<Waypoint> waypoints =
//                    new ArrayList<>(Arrays.asList(startWaypoint, destinationWaypoint));
//
//            routingEngine.calculateRoute(
//                    waypoints,
//                    new CarOptions(),
//                    new CalculateRouteCallback() {
//                        @RequiresApi(api = Build.VERSION_CODES.N)
//                        @Override
//                        public void onRouteCalculated(@Nullable RoutingError routingError, @Nullable List<Route> routes) {
//                            if (routingError == null) {
//                                Route route = routes.get(0);
//                                if(route.getLengthInMeters() == getMinRoute()){
//                                    showRouteDetails(route);
//                                    showRouteOnMap(route);
//                                    return;
//                                }
//
//                            } else {
//                                showDialog("Error while calculating a route:", routingError.toString());
//                            }
//                        }
//                    });
//        }

    }

    private void getListRoute() {
        routeList = new ArrayList<>();
        Waypoint startWaypoint = new Waypoint(startGeoCoordinates);
        for (int i = 0; i < geoCoordinatesList.size(); i++) {
            Waypoint destinationWaypoint = new Waypoint(geoCoordinatesList.get(i));

            List<Waypoint> waypoints =
                    new ArrayList<>(Arrays.asList(startWaypoint, destinationWaypoint));

            routingEngine.calculateRoute(
                    waypoints,
                    new CarOptions(),
                    new CalculateRouteCallback() {
                        @Override
                        public void onRouteCalculated(@Nullable RoutingError routingError, @Nullable List<Route> routes) {
                            if (routingError == null) {
                                Route route = routes.get(0);
                                routeList.add(route);
                            } else {
                                showDialog("Error while calculating a route:", routingError.toString());
                                return;
                            }
                        }
                    });
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Double getMinRoute() {

        double min = routeList.get(0).getLengthInMeters();
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).getLengthInMeters() <= min) {
                min = routeList.get(i).getLengthInMeters();
            }
        }
        return min;

    }


    private void showRouteDetails(Route route) {
        long estimatedTravelTimeInSeconds = route.getDurationInSeconds();
        int lengthInMeters = route.getLengthInMeters();

        String routeDetails =
                "Travel Time: " + formatTime(estimatedTravelTimeInSeconds)
                        + ", Length: " + formatLength(lengthInMeters);

        showDialog("Route Details", routeDetails);
    }

    private String formatTime(long sec) {
        int hours = (int) (sec / 3600);
        int minutes = (int) ((sec % 3600) / 60);

        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
    }

    private String formatLength(int meters) {
        int kilometers = meters / 1000;
        int remainingMeters = meters % 1000;

        return String.format(Locale.getDefault(), "%02d.%02d km", kilometers, remainingMeters);
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void showRouteOnMap(Route route) {
        // Show route as polyline.
        GeoPolyline routeGeoPolyline;
        try {
            routeGeoPolyline = new GeoPolyline(route.getPolyline());
        } catch (InstantiationErrorException e) {
            // It should never happen that a route polyline contains less than two vertices.
            return;
        }

        float widthInPixels = 20;
        MapPolyline routeMapPolyline = new MapPolyline(routeGeoPolyline,
                widthInPixels,
                Color.valueOf(0, 0.56f, 0.54f, 0.63f)); // RGBA

        mapView.getMapScene().addMapPolyline(routeMapPolyline);
        mapPolylines.add(routeMapPolyline);

        // Draw a circle to indicate starting point and destination.
//        addCircleMapMarker(startGeoCoordinates, R.drawable.green_dot);
//        addCircleMapMarker(destinationGeoCoordinates, R.drawable.green_dot);

        // Log maneuver instructions per route section.
        List<Section> sections = route.getSections();
        for (Section section : sections) {
            logManeuverInstructions(section);
        }
    }

    private void addPOIMapMarker(GeoCoordinates geoCoordinates) {
        MapImage mapImage = MapImageFactory.fromResource(context.getResources(), R.drawable.green_dot);

        // The bottom, middle position should point to the location.
        // By default, the anchor point is set to 0.5, 0.5.
        Anchor2D anchor2D = new Anchor2D(0.5f, 1);
        MapMarker mapMarker = new MapMarker(geoCoordinates, mapImage, anchor2D);

        Metadata metadata = new Metadata();
        metadata.setString("key_poi", "This is a POI.");
        mapMarker.setMetadata(metadata);

        mapView.getMapScene().addMapMarker(mapMarker);
    }

    private void logManeuverInstructions(Section section) {
        Log.d(TAG, "Log maneuver instructions per route section:");
        List<Maneuver> maneuverInstructions = section.getManeuvers();
        for (Maneuver maneuverInstruction : maneuverInstructions) {
            ManeuverAction maneuverAction = maneuverInstruction.getAction();
            GeoCoordinates maneuverLocation = maneuverInstruction.getCoordinates();
            String maneuverInfo = maneuverInstruction.getText()
                    + ", Action: " + maneuverAction.name()
                    + ", Location: " + maneuverLocation.toString();
            Log.d(TAG, maneuverInfo);
        }
    }


    public void clearWaypointMapMarker() {
        for (MapMarker mapMarker : mapMarkerList) {
            mapView.getMapScene().removeMapMarker(mapMarker);
        }
        mapMarkerList.clear();
    }

    public void clearRoute() {
        for (MapPolyline mapPolyline : mapPolylines) {
            mapView.getMapScene().removeMapPolyline(mapPolyline);
        }
        mapPolylines.clear();
    }

    public void cleanMap() {
        clearRoute();
        clearWaypointMapMarker();
    }
    private void addPhotoMapMarker(GeoCoordinates geoCoordinates) {
        MapImage mapImage = MapImageFactory.fromResource(context.getResources(), R.drawable.green_dot);
        MapMarker mapMarker = new MapMarker(geoCoordinates, mapImage);

        mapView.getMapScene().addMapMarker(mapMarker);
        mapMarkerList.add(mapMarker);
    }


}
