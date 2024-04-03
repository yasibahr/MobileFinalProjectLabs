//package algonquin.cst2355.mobilefinalprojectlabs;
//
//import android.app.AlertDialog;
//import android.view.View;
//import android.widget.TextView;
//
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelStoreOwner;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import algonquin.cst2355.mobilefinalprojectlabs.data.DictionaryViewModel;
//
//public class MyRowHolder extends RecyclerView.ViewHolder{
//    TextView messageText; // Assuming you have a TextView to display the term
//
//    public MyRowHolder(@NonNull View itemView, TermsAdapter.OnTermDeleteListener listener, List<TermAndMeaningStorage> terms) {
//        super(itemView);
//        messageText = itemView.findViewById(R.id.eachTermTextView);
//
//        // Set the click listener for the entire row
//        itemView.setOnClickListener(view -> {
//            int position = getAbsoluteAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                TermAndMeaningStorage termToDelete = terms.get(position);
//                listener.onDeleteTerm(termToDelete);
//            }
//        });
//    }
//
////    DictionaryViewModel dictionaryViewModel;
////    LiveData<List<TermAndMeaningStorage>> liveTerms;
////    ArrayList<TermAndMeaningStorage> allTerms = new ArrayList<>();
////
////    public MyRowHolder(@NonNull View itemView, int viewType) {
////        super(itemView);
////
////        dictionaryViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(DictionaryViewModel.class);
////        allTerms = (ArrayList<TermAndMeaningStorage>) dictionaryViewModel.getAllTerms().getValue();
////
////        itemView.setOnClickListener( click -> {
////            int rowNum = getAbsoluteAdapterPosition(); //tells you which row you're on
////            TermAndMeaningStorage toDelete = allTerms.get(rowNum);
////            AlertDialog.Builder builder = new AlertDialog.Builder(TermsAdapter.this);
////
////        });
////    }
//}
