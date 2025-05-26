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
import androidx.compose.ui.platform.ComposeView;
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
import com.adrzdv.mtocp.ui.model.ViolationDto;
import com.adrzdv.mtocp.ui.screen.ViolationCatalogScreenWrapperKt;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactory;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.Unit;


public class ViolationCatalogActivity extends AppCompatActivity {

    ViolationViewModel violationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViolationViewModel viewModel = new ViewModelProvider(
                this,
                ViewModelFactoryProvider.provideFactory()
        ).get(ViolationViewModel.class);

        ComposeView composeView = new ComposeView(this);
        ViolationCatalogScreenWrapperKt.showViolationCatalogScreen(composeView,
                () -> {
                    return Unit.INSTANCE;
                },
                viewModel,
                RevisionType.getListOfTypes());

        setContentView(composeView);

    }
}