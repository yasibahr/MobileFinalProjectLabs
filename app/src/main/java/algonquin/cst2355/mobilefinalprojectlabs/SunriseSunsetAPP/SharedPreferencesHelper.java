package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_TEXT = "text_key";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveText(String text) {
        editor.putString(KEY_TEXT, text);
        editor.apply();
    }

    public String getText() {
        return sharedPreferences.getString(KEY_TEXT, "");
    }

    public void clearText() {
        editor.remove(KEY_TEXT);
        editor.apply();
    }

}
