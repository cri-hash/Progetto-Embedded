package embeddedproject.takethepill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import database.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<AssumptionEntity> listAssunzioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Select Home by default
        navigationView.setCheckedItem(R.id.nav_today);
        Fragment fragment = new TodayFragment();
        displaySelectedFragment(fragment);


        DatabaseHelper db=new DatabaseHelper(this); //per testare il db in fase di creazione
        db.popolaDB();

        // Terapie di durata Senza Fine

        // Cerco terapie con getDays==-1
        List<TherapyEntityDB> listaTerapie = db.getAllTherapies();

        // Per ogni terapia:
        for(int i=0;i<listaTerapie.size();i++){
            if(listaTerapie.get(i).getDays()==-1){
                // n° Assunzioni della terapia da oggi in poi
                // Data di oggi
                SimpleDateFormat myFormat=new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                String oggi = myFormat.format(calendar.getTime());
            }
        }
            // conto il numero di assunzioni da oggi in poi
            // Se <10 ne aggiungo 10





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_today) {
            getSupportActionBar().setTitle("OGGI");

            fragment=new TodayFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_therapy) {
            getSupportActionBar().setTitle("TERAPIA");
            fragment=new TherapyFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_drugs) {
            getSupportActionBar().setTitle("FARMACI");
            fragment=new DrugsFragment();
            displaySelectedFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    // Metodo per visualizzare i fragment
    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }


}
