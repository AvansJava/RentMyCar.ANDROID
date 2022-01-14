package com.rentmycar.rentmycar

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rentmycar.rentmycar.network.response.GetCarResponse
import com.rentmycar.rentmycar.network.service.CarService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CarServiceTest {
    private val carService = Mockito.mock(CarService::class.java)

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Test
    @Throws(Exception::class)
    fun carServiceTest() {
        testScope.runBlockingTest {
            val car = createCarResponse()
            Mockito.`when`(carService.getCarById(1)).thenReturn(Response.success(car))

            val carToTest = carService.getCarById(1)
            assertEquals(carToTest.body()?.id, car.id)
        }
    }

    private fun createCarResponse(): GetCarResponse {
        return GetCarResponse(
            1,
            "Volkswagen",
            "Polo",
            "TDI 1.5",
            "ABC-123",
            3.6,
            21500,
            "ICE",
            1,
            null,
            "2022-01-03T09:25:24.863252",
            null)
    }
}