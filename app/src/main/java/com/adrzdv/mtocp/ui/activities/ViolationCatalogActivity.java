package com.adrzdv.mtocp.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
import com.adrzdv.mtocp.mapper.ViolationMapper;
import com.adrzdv.mtocp.ui.adapters.ViolationAdapter;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

        ViolationDao dao = App.getInstance().getDatabase().violationDao();
        ViolationRepository repository = new ViolationRepositoryImpl(dao);
        ViolationViewModelFactory factory = new ViolationViewModelFactory(repository);
        violationViewModel = new ViewModelProvider(this, factory).get(ViolationViewModel.class);

        violationViewModel.getViolations().observe(this, violations -> {
            adapter.updateData(violations.stream().map(ViolationMapper::fromEntityToDomain)
                    .map(ViolationMapper::fromDomainToDto).collect(Collectors.toList()));
        });
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
                //filterDataByRevisionType(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //filterDataByRevisionType(RevisionType.ALL.getRevisionTypeTitle());
            }
        });


    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}