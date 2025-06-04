import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.asFlow
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.component.RevisionTypeDropdown
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel

@Composable
fun ViolationCatalogScreen(
    viewModel: ViolationViewModel,
    revisionTypes: List<String>
) {
    var searchText by remember { mutableStateOf("") }
    var selectedRevision by remember { mutableStateOf(revisionTypes.first()) }

    val violations by viewModel.filteredViolations
        .asFlow()
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.filterDataByString(it)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFCCCCCC),
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            label = {
                Text(
                    stringResource(R.string.search_text_hint),
                    style = CustomTypography.labelLarge
                )
            },
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        RevisionTypeDropdown(
            revisionTypes = revisionTypes,
            selectedRevision = selectedRevision,
            onRevisionSelected = {
                selectedRevision = it
                viewModel.filterDataByRevisionType(RevisionType.fromString(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(violations) { violation ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = violation.code.toString(),
                        style = CustomTypography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = violation.name,
                        style = CustomTypography.bodyLarge,
                        modifier = Modifier.weight(5f)
                    )
                }
            }
        }
    }
}
