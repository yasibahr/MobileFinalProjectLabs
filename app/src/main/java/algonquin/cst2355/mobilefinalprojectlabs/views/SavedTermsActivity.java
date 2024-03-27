package algonquin.cst2355.mobilefinalprojectlabs.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivitySavedTermsBinding;

public class SavedTermsActivity extends AppCompatActivity {

    ActivitySavedTermsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySavedTermsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_saved_terms);

        binding.definitionsRecycler.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    //obj for representing all that goes on a row in the list
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView Definition1Text;
        TextView Definition2Text;
        TextView Definition3Text;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            Definition1Text = itemView.findViewById(R.id.Definition1);
            Definition2Text = itemView.findViewById(R.id.Definition2);
            Definition3Text = itemView.findViewById(R.id.Definition3);

        }
    }


}