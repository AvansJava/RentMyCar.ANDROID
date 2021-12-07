package com.rentmycar.rentmycar

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.lang.Math.log


class NavGraphActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val preference = AppPreference(RentMyCarApplication.context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_graph)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.welcomeScreenFragment, R.id.userDashboardFragment, R.id.carListFragment),
            drawerLayout = drawerLayout
        )
        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
        findViewById<NavigationView>(R.id.nav_view).setCheckedItem(navController.graph.startDestination)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
