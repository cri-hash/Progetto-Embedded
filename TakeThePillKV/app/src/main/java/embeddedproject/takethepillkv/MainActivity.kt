package embeddedproject.takethepillkv

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Select Home by default
        nav_view.setCheckedItem(R.id.nav_today)
        val fragment = TodayFragment()
        displaySelectedFragment(fragment)

        // GESTIONE DELLE NOTIFICHE:
        //...................... RICOPIAREEEE


        // GESTIONE DELLE TERAPIE DI DURATA SENZA LIMITI:
        //...................... RICOPIAREEEE


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }*/



    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        val id = item.itemId

        var fragment: Fragment? = null

        if (id == R.id.nav_today) {
            supportActionBar!!.setTitle("OGGI")

            fragment = TodayFragment()
            displaySelectedFragment(fragment)

        } else if (id == R.id.nav_therapy) {
            supportActionBar!!.setTitle("TERAPIA")
            fragment = TherapyFragment()
            displaySelectedFragment(fragment)

        } else if (id == R.id.nav_drugs) {
            supportActionBar!!.setTitle("FARMACI")
            fragment = DrugsFragment()
            displaySelectedFragment(fragment)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    // Metodo per visualizzare i fragment
    private fun displaySelectedFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }



}
