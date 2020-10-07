package com.company.weathervietnamongooglemap.ui.main;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;

public class KMLParser {

    public void accessContainers(Iterable<KmlContainer> containers) {
        for (KmlContainer container : containers) {
            if (container != null) {
                if (container.hasContainers()) {
                    accessContainers(container.getContainers());
                } else {
                    if (container.hasPlacemarks()) {
                        accessPlacemarks(container.getPlacemarks());
                    }
                }
            }
        }
    }

    public void accessPlacemarks(Iterable<KmlPlacemark> placemarks) {
        for (KmlPlacemark placemark : placemarks) {
            if (placemark != null) {
                Iterable<String> iterable =   placemark.getPropertyKeys();
//              for (String s : iterable) {
//                  System.out.println(s + "sout");
//              }
                System.out.println(placemark.toString());
                Geometry<LatLng> geometry = placemark.getGeometry();
                if (geometry instanceof KmlPolygon) {
                    KmlPolygon polygon = (KmlPolygon) geometry;
                  //  mLatLngList.addAll(polygon.getOuterBoundaryCoordinates());
                }


            }
        }
    }
}
