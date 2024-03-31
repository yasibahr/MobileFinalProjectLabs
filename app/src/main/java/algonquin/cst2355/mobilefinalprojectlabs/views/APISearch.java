package algonquin.cst2355.mobilefinalprojectlabs.views;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;


import com.android.volley.Response;

public class APISearch extends AppComp{
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public static void getDefinitions(String term, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        String url = API_URL + term;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, responseListener);

        //add request to RequestQueue
        Volley.newRequestQueue(MainA.getAppContext()).add(jsonArrayRequest);
    }
}
