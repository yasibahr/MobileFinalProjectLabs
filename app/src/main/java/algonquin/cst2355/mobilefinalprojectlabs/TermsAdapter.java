package algonquin.cst2355.mobilefinalprojectlabs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * For recyclerView in SavedTerms class. Holds list of saved terms.
 */
public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {
    private List<String> wordsList;

    public TermsAdapter(List<String> wordsList) {
        this.wordsList = wordsList;
    }

    @NonNull
    @Override
    public TermsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_term, parent, false);
        return new TermsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.ViewHolder holder, int position) {
        String word = wordsList.get(position); //get the word
        holder.eachTermTextView.setText(word); //set the text of your TextView to the word
    }

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

    // Method to update the words list
    public void updateWords(List<String> newWordsList) {
        this.wordsList.clear();
        this.wordsList.addAll(newWordsList);
        notifyDataSetChanged();
    }
}
