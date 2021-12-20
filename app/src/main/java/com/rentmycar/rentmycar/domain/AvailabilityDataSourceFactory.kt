package com.rentmycar.rentmycar.domain

import androidx.paging.DataSource
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.repository.AvailabilityRepository
import kotlinx.coroutines.CoroutineScope

class AvailabilityDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: AvailabilityRepository,
    private val carId: Int
): DataSource.Factory<Int, GetAvailabilityResponse>() {

    override fun create(): DataSource<Int, GetAvailabilityResponse> {
        return AvailabilityDataSource(coroutineScope, repository, carId)
    }
}