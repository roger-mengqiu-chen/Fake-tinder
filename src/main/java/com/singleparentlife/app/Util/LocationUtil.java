package com.singleparentlife.app.Util;

import com.singleparentlife.app.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class LocationUtil {

    public double distanceBetweenLocations(Location l1, Location l2) {

        double lat1 = l1.getLat();
        double lon1 = l1.getLon();
        double lat2 = l2.getLat();
        double lon2 = l2.getLon();

        double earthR = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthR * c;
    }

    /**
     * Call api from open street map to get detailed address
     * @param lat latitude
     * @param lon longitude
     * @return Location detailed address with lat and lon
     */
    public Location GPSToLocation(Double lat, Double lon) {
        if (lat == null || lon == null) {
            return null;
        }
        try {
            String uri = String.format("https://nominatim.openstreetmap.org/search.php?q=%.10f%%2C+%.10f&format=jsonv2", lat, lon);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForEntity(new URI(uri), String.class).getBody();
            String res = result.substring(1, result.length()-1);
            if (res.length() > 0) {
                JSONObject obj = new JSONObject(res);
                String address = obj.getString("display_name");
                if (address == null) {
                    return null;
                }
                String[] addressArr = address.split(", ");

                Location location = new Location();
                location.setLat(lat);
                location.setLon(lon);
                location.setCountry(addressArr[5]);
                location.setProvince(addressArr[3]);
                location.setCity(addressArr[2]);
                location.setStreet(addressArr[0]);
                location.setPostcode(addressArr[4]);

                return location;
            }
        } catch (URISyntaxException e ) {
            log.error(e.getMessage());
        } catch (Exception e1) {
            log.error(e1.getMessage());
        }
        return null;
    }
}
