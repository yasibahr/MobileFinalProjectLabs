package algonquin.cst2355.mobilefinalprojectlabs.DictionaryAPI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * For recyclerView in SavedTerms class. Holds list of saved terms.
 * @author Yasaman Bahramifarid
 * @section CST2355 012
 * @creationDate 01/04/2024
 */
public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {
    private List<String> wordsList;

    /**
     * Constructor for this class
     * @param wordsList takes a list of words of type String
     */
    public TermsAdapter(List<String> wordsList) {
        this.wordsList = wordsList;
    }

    /**
     * Creates a viewholder object.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return Viewholder object
     */
    @NonNull
    @Override
    public TermsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_term, parent, false);
        return new TermsAdapter.ViewHolder(view);
    }

    /**
     * Initalizes a ViewHolder to go to a specific position in the list
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.ViewHolder holder, int position) {
        String word = wordsList.get(position); //get the word
        holder.eachTermTextView.setText(word); //set the text of your TextView to the word
    }

    /**
     * Returns an int specifying how many items to draw.
     * @return int of number of items
     */
    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eachTermTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eachTermTextView = itemView.findViewById(R.id.eachTermTextView);
        }
    }

    /**
     * Update the words list
     * @param newWordsList
     */
    public void updateWords(List<String> newWordsList) {
        this.wordsList.clear();
        this.wordsList.addAll(newWordsList);
        notifyDataSetChanged();
    }
}
