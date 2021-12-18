package com.rentmycar.rentmycar.domain

import androidx.paging.PageKeyedDataSource
import com.rentmycar.rentmycar.config.Config.DEFAULT_PAGE_SIZE
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.repository.AvailabilityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AvailabilityDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: AvailabilityRepository,
    private val carId: Int
): PageKeyedDataSource<Int, GetAvailabilityResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetAvailabilityResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCarAvailability(carId, DEFAULT_PAGE_SIZE, 0)

            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.content, null, page.number + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetAvailabilityResponse>
    ) {
        // noting to do
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetAvailabilityResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCarAvailability(carId, DEFAULT_PAGE_SIZE, params.key)

            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }

            callback.onResult(page.content, page.number + 1)
        }
    }
}