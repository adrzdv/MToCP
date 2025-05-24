package com.adrzdv.mtocp.ui.activities;

import static com.adrzdv.mtocp.domain.model.enums.RevisionType.ALL;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl;
import com.adrzdv.mtocp.databinding.ActivityViolationCatalogBinding;
import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.ui.adapters.ViolationAdapter;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactory;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class ViolationCatalogActivity extends AppCompatActivity {

    private ActivityViolationCatalogBinding binding;
    private ViolationViewModel violationViewModel;
    private ViolationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityViolationCatalogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initSpinner();

        adapter = new ViolationAdapter(new ArrayList<>());
        RecyclerView recyclerView = binding.catalogViolationListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ViewModelFactory factory = ViewModelFactoryProvider.provideFactory();
        violationViewModel = new ViewModelProvider(this, factory).get(ViolationViewModel.class);
        violationViewModel.getFilteredViolations().observe(this, adapter::updateData);

        EditText searchEditText = binding.catalogCodeViolationSearchTextInput.getEditText();
        if (searchEditText != null) {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String query = editable.toString();
                    violationViewModel.filterDataByString(query);
                }
            });
        }
    }

    private void initSpinner() {
        List<String> typeList = RevisionType.getListOfTypes();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_selected_item,
                typeList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinnerRevisionType.setAdapter(arrayAdapter);

        binding.spinnerRevisionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = typeList.get(position);
                RevisionType type = RevisionType.fromString(selectedType);
                violationViewModel.filterDataByRevisionType(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                violationViewModel.filterDataByRevisionType(ALL);
            }
        });
    }
}