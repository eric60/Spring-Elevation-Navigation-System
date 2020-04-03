package com.EleNa;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ElevationFinder {
    final String BASE_URL = "https://nationalmap.gov/epqs/pqs.php";
    final String units = "Meters";
    final String outputType = "json";

    public double getElevation(double longitude, double latitude) throws Exception{

        try {
            String query = String.format("%s?x=%f&y=%f&units=%s&output=%s", BASE_URL, longitude, latitude, units, outputType);
            URL url = new URL(query);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            //request.setRequestMethod("GET");

            if(request.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject responseJSON = new JSONObject(response.toString());
                if(responseJSON.has("USGS_Elevation_Point_Query_Service")) {
                    JSONObject queryJSON = responseJSON.getJSONObject("USGS_Elevation_Point_Query_Service");
                    if (queryJSON.has("Elevation_Query")) {
                        JSONObject dataJSON = queryJSON.getJSONObject("Elevation_Query");
                        if (dataJSON.has("Elevation")) {
                            double elevation = (double) dataJSON.get("Elevation");
                            return elevation;
                        }
                    }
                }
                return Double.MAX_VALUE;
            }
        }
        catch (Exception e) {
            throw new Exception("ERROR in ElevationFinder: "+e.getMessage());
        }

        return Double.MAX_VALUE;
    }
}