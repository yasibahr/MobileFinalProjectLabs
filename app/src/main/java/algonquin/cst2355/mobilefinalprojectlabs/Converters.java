package algonquin.cst2355.mobilefinalprojectlabs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/*Convert list of meanings to json to store in room database*/
public class Converters {
    private static Gson gson = new Gson(); //converts java objects into JSON representation and back

    /*Convert JSON string to list of meanings. Takes JSON string as a parameter and gives back a list of meanings*/
    @androidx.room.TypeConverter
    public static List<TermAndMeaningStorage.Meanings> listToJSON(String data){
        if (data==null){
            return Collections.emptyList(); //empty list if there is no JSON list
        }

        /*Using a TypeToken so converter knows that the list is of type Meanings. Then use getType to retrieve
        the type information captured by the TypeToken instance. This info is used by Gson to deserialize the
        JSON string into a list of Meaning objects */
        Type listType = new TypeToken<List<TermAndMeaningStorage.Meanings>>() {}.getType();
        return gson.fromJson(data, listType); //now that Gson has type of list, it can convert data to a list of Meanings
    }

    /*Meanings list to JSON string*/
    @androidx.room.TypeConverter
    public static String JSONToList(List<TermAndMeaningStorage.Meanings> meaningsList){
        return gson.toJson(meaningsList);
    }
}
