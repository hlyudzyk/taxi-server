package com.taxiapp.main.services.util.positioning;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    @Value("${maps_api_key}")
    private String mapsApiKey;

    public Optional<String> getGeocodedLocation(double latitude, double longitude) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey(mapsApiKey).build();
            LatLng latLng = new LatLng(latitude, longitude);
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latLng).await();

            if (results != null && results.length > 0) {
                return Optional.of(results[0].formattedAddress);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public double calculateDistance(double startLat, double startLong, double endLat, double endLong) {
        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371 * c;
    }

    private double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

}
