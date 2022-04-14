package com.singleparentlife.app.Util;

import com.singleparentlife.app.service.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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
            log.error("lat or lon can't be null");
            return null;
        }
        try {
            String uri = String.format("https://nominatim.openstreetmap.org/reverse.php" +
                    "?lat=%.10f&lon=%.10f&zoom=18&format=jsonv2", lat, lon);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForEntity(new URI(uri), String.class).getBody();
            //String res = result.substring(1, result.length()-1);

            JSONObject obj = new JSONObject(result);

            JSONObject addressObj = obj.getJSONObject("address");

            String road = addressObj.getString("road");
            String city = addressObj.getString("city");
            String province = addressObj.getString("state");
            String country = addressObj.getString("country");
            String postCode = "";
            try {
                postCode = addressObj.getString("postcode");
            } catch (Exception e) {
                // Sometimes response doesn't have postcode, if so, ignore it.
            }

            String latStr = obj.getString("lat");
            String lonStr = obj.getString("lon");
            lat = Double.parseDouble(latStr);
            lon = Double.parseDouble(lonStr);

            Location location = new Location();
            location.setLat(lat);
            location.setLon(lon);
            location.setCountry(country);
            location.setProvince(province);
            location.setCity(city);
            location.setStreet(road);
            location.setPostcode(postCode);

            return location;

        } catch (URISyntaxException e ) {
            log.error(e.getMessage());
        } catch (Exception e1) {
            log.error(e1.getMessage());
        }
        return null;
    }

    /**
     * Convert address to GPS
     *
     * @return Location that is store in database
     */
    public Location AddressToGPS (String street, String city, String province, String country) {
        street = street.replaceAll("\\s", "+");
        city = city.replaceAll("\\s", "+");
        province = province.replaceAll("\\s", "+");
        country = country.replaceAll("\\s", "+");

        try {
            String uri = String.format("https://nominatim.openstreetmap.org/search.php?street=%s&city=%s&state=%s&country=%s&format=jsonv2",
                    street, city, province, country);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForEntity(new URI(uri), String.class).getBody();

            JSONArray arr = new JSONArray(result);
            if (arr.isEmpty()) {
                log.error("No location for given address");
                return null;
            }
            JSONObject addObj = arr.getJSONObject(0);
            String address = addObj.getString("display_name");

            String[] addArr = address.split(", ");

            street = addArr[0];
            city = addArr[2];
            province = addArr[3];
            String postCode = "";
            if (addArr.length > 5) {
                country = addArr[5];
                postCode = addArr[4];
            }
            else {
                country = addArr[4];
            }

            String latStr = addObj.getString("lat");
            String lonStr = addObj.getString("lon");
            double lat = Double.parseDouble(latStr);
            double lon = Double.parseDouble(lonStr);

            Location location = new Location();
            location.setLat(lat);
            location.setLon(lon);
            location.setCountry(country);
            location.setProvince(province);
            location.setCity(city);
            location.setStreet(street);
            location.setPostcode(postCode);

            return location;

        } catch (URISyntaxException e ) {
            log.error(e.getMessage());
        } catch (Exception e1) {
            log.error(e1.getMessage());
        }
        return null;
    }
}
