package com.rentmycar.rentmycar

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rentmycar.rentmycar.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.runner.RunWith
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import org.junit.Assert.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RentMyCarDatabaseTest {

    private lateinit var carDao: CarDao
    private lateinit var locationDao: LocationDao
    private lateinit var db: RentMyCarDatabase

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(appContext, RentMyCarDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()

        carDao = db.carDao()
        locationDao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUpdateDeleteAndGetCarTest() {
        testScope.runBlockingTest {
            val car = Car(
                1,
                "Volkswagen",
                "Polo",
                "TDI 1.5",
                "ABC-123",
                3.6,
                21500,
                "ICE",
                null,
                null,
            )
            carDao.createCar(car)
            carDao.updateCar(1,1)
            val getCar = carDao.getCar(1)
            assertNotNull(getCar)
            assertEquals(getCar.brand, "Volkswagen")
            assertEquals(getCar.locationId,1)

            carDao.deleteCar(1)
            val getDeletedCar = carDao.getCar(1)
            assertNull(getDeletedCar)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertDeleteAndGetLocationTest() {
        testScope.runBlockingTest {
            val location = Location(
                1,
                "Veemarktstraat",
                "66",
                "4811ZJ",
                "Breda",
                "Netherlands",
                51.588896000000005,
                4.7796635
            )
            locationDao.createLocation(location)
            val getLocation = locationDao.getLocation(1)
            assertNotNull(getLocation)
            assertEquals(getLocation.street, "Veemarktstraat")

            locationDao.deleteLocation(1)
            val getDeletedLocation = locationDao.getLocation(1)
            assertNull(getDeletedLocation)
        }
    }
 }
