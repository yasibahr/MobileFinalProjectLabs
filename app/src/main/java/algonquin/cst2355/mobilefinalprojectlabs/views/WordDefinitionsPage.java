package algonquin.cst2355.mobilefinalprojectlabs.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.databinding.ActivityWordDefinitionsPageBinding;

//page2
public class WordDefinitionsPage extends AppCompatActivity {
    ActivityWordDefinitionsPageBinding binding;
    private RecyclerView.Adapter<SavedTerms> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_word_definitions_page);

        binding = ActivityWordDefinitionsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get searched term and definitions passed from mainactivity (page1)
        String term = getIntent().getStringExtra("SEARCH_TERM");
        ArrayList<String> definitionsList = getIntent().getStringArrayListExtra("DEFINITIONS");

        //set the term in the textview
        binding.searchedTermTextView.setText(term);

        //initialize recyclerview with the DefinitionAdapter
        DefinitionAdapter adapter = new DefinitionAdapter(definitionList);
        binding.definitions

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<SavedTerms>() {
            @NonNull
            @Override
            public SavedTerms onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                if(viewType==0){

                }


            }

            @Override
            public void onBindViewHolder(@NonNull SavedTerms holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });



//        EdgeToEdge.enable(this);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}