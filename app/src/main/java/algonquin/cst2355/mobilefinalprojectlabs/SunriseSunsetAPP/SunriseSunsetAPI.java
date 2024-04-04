package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class SunriseSunsetAPI {

    private static final String BASE_URL = "https://api.sunrisesunset.io/json";
    private static final String TAG = "SunriseSunsetAPI";

    private final RequestQueue requestQueue;

    public SunriseSunsetAPI(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void lookupSunriseSunset(double latitude, double longitude, Response.Listener<SunriseSunsetResponse> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "?lat=" + latitude + "&lng=" + longitude + "&timezone=UTC&date=today";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        SunriseSunsetResponse sunriseSunsetResponse = parseResponse(response);
                        listener.onResponse(sunriseSunsetResponse);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                        errorListener.onErrorResponse(new VolleyError(e));
                    }
                },
                errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    private SunriseSunsetResponse parseResponse(JSONObject jsonObject) throws JSONException {
        JSONObject results = jsonObject.getJSONObject("results");
        String sunrise = results.getString("sunrise");
        String sunset = results.getString("sunset");
        return new SunriseSunsetResponse(sunrise, sunset);
    }

}
