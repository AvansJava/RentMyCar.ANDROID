package com.rentmycar.rentmycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView


class NavGraphActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GlobalNavigationHandler {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val preference = AppPreference(RentMyCarApplication.context)
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_graph)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.welcomeScreenFragment,
                R.id.userDashboardFragment,
                R.id.carListFragment,
                R.id.locationListFragment,
                R.id.userCarListFragment,
                R.id.rentalPlanListFragment,
                R.id.reservationOverviewFragment,
                R.id.carDetailsFragment),
            drawerLayout = drawerLayout
        )
        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

        navigationView = findViewById(R.id.nav_view)
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(navController.graph.startDestination)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (val id = item.itemId) {
            R.id.logout_item -> {
                preference.clearPreferences()
                navController.navigate(R.id.userLoginFragment)
            } else -> {
            navController.navigate(id)
            }
        }
        drawerLayout.closeDrawer(navigationView)
        return true
    }

    override fun logout() {
        runOnUiThread {
            navController.navigate(R.id.userLoginFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalNavigator.registerHandler(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalNavigator.unregisterHandler()
    }
}
