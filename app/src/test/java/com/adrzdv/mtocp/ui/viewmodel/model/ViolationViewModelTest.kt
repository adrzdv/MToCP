package com.adrzdv.mtocp.ui.viewmodel.model

import app.cash.turbine.test
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.data.repository.refcache.ReferencesCache
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViolationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val cacheRepository: CacheRepository = mockk()
    private val violationCache: ReferencesCache<Int, ViolationEntity> = mockk()

    private lateinit var viewModel: ViolationViewModel

    private val mockEntities = listOf(
        ViolationEntity(101, "Violation One", "V1", true, true, false, false, false),
        ViolationEntity(102, "Violation Two", "V2", true, false, true, false, false),
        ViolationEntity(201, "Another Issue", "AI", true, false, false, true, false)
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // GIVEN
        every { cacheRepository.violationCache } returns violationCache
        every { violationCache.getAll() } returns mockEntities

        viewModel = ViolationViewModel(cacheRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onQueryChange_updatesQueryState() = runTest {
        // WHEN
        viewModel.onQueryChange("search")

        // THEN
        assertThat(viewModel.query.value).isEqualTo("search")
    }

    @Test
    fun resetQuery_clearsQueryState() = runTest {
        // GIVEN
        viewModel.onQueryChange("search")

        // WHEN
        viewModel.resetQuery()

        // THEN
        assertThat(viewModel.query.value).isEmpty()
    }

    @Test
    fun setRevisionType_updatesRevisionTypeState() = runTest {
        // WHEN
        viewModel.setRevisionType(RevisionType.IN_TRANSIT)

        // THEN
        assertThat(viewModel.revisionType.value).isEqualTo(RevisionType.IN_TRANSIT)
    }

    @Test
    fun observeFilters_searchByName_updatesItems() = runTest {
        // WHEN
        viewModel.onQueryChange("Violation")

        // THEN
        viewModel.items.test {
            // Initial empty list from the Flow
            assertThat(awaitItem()).isEmpty()

            advanceTimeBy(400) // Trigger debounce and mapping

            val result = awaitItem()
            assertThat(result).hasSize(2)
            assertThat(result.map { it.name }).containsExactly("Violation One", "Violation Two")
        }
    }

    @Test
    fun observeFilters_searchByCode_updatesItems() = runTest {
        // WHEN
        viewModel.onQueryChange("201")

        // THEN
        viewModel.items.test {
            assertThat(awaitItem()).isEmpty()

            advanceTimeBy(400)

            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].code).isEqualTo(201)
        }
    }

    @Test
    fun observeFilters_filterByRevisionType_updatesItems() = runTest {
        // WHEN
        viewModel.setRevisionType(RevisionType.AT_START_POINT)

        // THEN
        viewModel.items.test {
            assertThat(awaitItem()).isEmpty()

            advanceTimeBy(400)

            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].name).isEqualTo("Violation Two")
        }
    }

    @Test
    fun observeFilters_combinedFilter_updatesItems() = runTest {
        // GIVEN
        viewModel.setRevisionType(RevisionType.IN_TRANSIT) // Matches 101

        // WHEN
        viewModel.onQueryChange("Violation") // Matches 101, 102

        // THEN
        viewModel.items.test {
            assertThat(awaitItem()).isEmpty()

            advanceTimeBy(400)

            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].code).isEqualTo(101)
        }
    }

    @Test
    fun suggestions_mapsFromItems_updatesUiState() = runTest {
        // WHEN
        viewModel.onQueryChange("Another")

        // THEN
        viewModel.suggestions.test {
            // Initial empty list
            assertThat(awaitItem()).isEmpty()

            advanceTimeBy(400)

            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].description).isEqualTo("Another Issue")
        }
    }

    @Test
    fun getDomainByCode_validCode_returnsCorrectDomain() = runTest {
        // GIVEN
        viewModel.onQueryChange("") // Trigger initial load

        // We need to wait for the flow to collect and update the internal _items StateFlow.
        // Since getDomainByCode reads from _items.value directly, we must ensure it's populated.
        viewModel.items.test {
            assertThat(awaitItem()).isEmpty()
            advanceTimeBy(400)
            assertThat(awaitItem()).hasSize(3) // All items should be present when query is empty

            // WHEN
            val result = viewModel.getDomainByCode(102)

            // THEN
            assertThat(result.code).isEqualTo(102)
            assertThat(result.name).isEqualTo("Violation Two")
        }
    }
}
