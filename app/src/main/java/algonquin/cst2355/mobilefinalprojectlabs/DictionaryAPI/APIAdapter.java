package algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.TermAndMeaningStorage;

/**
 * Will use the list of definitions from the application (for a word) and connects it to the RecyclerView
 * on the corresponding xml layout. Displays the data on the UI.
 * of any display changes.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 01/04/2024
 */
public class APIAdapter extends RecyclerView.Adapter<APIAdapter.ViewHolder>{

    private final List<TermAndMeaningStorage> definitionsList; //holds list of definitions that adapter will display

    /**
     * Constructor for this class.
     * @param definitionsList Takes a list of definitions of type TermAndMeaningStorage
     */
    public APIAdapter(List<TermAndMeaningStorage> definitionsList){ //constructor for adapter. takes list of definitions as param
        this.definitionsList = definitionsList;
    }

    /**
     * This method id called when the RecyclerView needs a new ViewHolder to display an item.
     * Inside, it inflates the layout (R.layout.whatever_layout in xml res/layout folder) for each item.
     * The xml in the res/layout folder defines how each item in the RecyclerView should look.
     * After inflating the layout, it returns a new instance of the ViewHolder class, passing the
     * inflated view to its constructor.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new instance of the ViewHolder class
     */
    @NonNull
    @Override
    public APIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //called when RecyclerView needs a new ViewHolder to display an item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_definition,parent, false); //inflate layout for EACH item/definition
        return new APIAdapter.ViewHolder(view); //create new view in recyclerview. return new ViewHolder instance
    }

    /**
     * Used to display the data at the specified position. Where you take data for the item (definition
     * from list of definitions) and bind it to the views defined in ViewHolder.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull APIAdapter.ViewHolder holder, int position) { //binds data to each item. sets the text of a TextView to corresponding definition
        String singleDefinition = String.valueOf(definitionsList.get(position)); //get a definition string from API list
        holder.eachDefinitionTextView.setText(singleDefinition); //set text of TextView to definition
    }

    /**
     * Returns total number of items in the list to be displayed
     * @return Number of items on list to be displayed
     */
    @Override
    public int getItemCount() {
        return definitionsList.size();
    }

    /**
     *A ViewHolder represents a single list item view within the RecyclerView. It contains
     * references to the individual views within the item layout (ex. TextView, ItemView..) to allow
     * for data binding in onBindViewHolder.
     * It holds references to any view that will be set or updated based on the data for an item.
     * By storing these references, the RecyclerView can update the view without needing to
     * find the view each time by calling findViewById.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{ //ViewHolder class represents a single list item view within the RecyclerView
        TextView eachDefinitionTextView; //TextView for displaying a single definition. each single definition added to recyclerview

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            eachDefinitionTextView = itemView.findViewById(R.id.eachDefinitionTextView); //initialize TextView for definition
        }
    }
}

