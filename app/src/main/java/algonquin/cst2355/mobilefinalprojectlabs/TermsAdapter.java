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

    private final List<TermAndMeaningStorage> termsList;

    public TermsAdapter(List<TermAndMeaningStorage> termsList) {
        this.termsList = termsList;
    }

    @NonNull
    @Override
    public TermsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_definition,parent,false);
        return new TermsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.ViewHolder holder, int position) {
        String singleTerm = String.valueOf(termsList.get(position));
        holder.eachTermTextView.setText(singleTerm);
    }

    @Override
    public int getItemCount() {
        return termsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView eachTermTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eachTermTextView = itemView.findViewById(R.id.eachTermTextView);
        }
    }
}
