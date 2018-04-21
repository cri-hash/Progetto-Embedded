package embeddedproject.com.takethepillow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.LinkedList;
import java.util.List;

import database.DatabaseHelper;
import database.Str;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.hello);

        ListView listView = (ListView) findViewById(R.id.listview1);

        List<Impegno> day = new LinkedList<Impegno>();

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.note, day);
        listView.setAdapter(customAdapter);
        Log.d("arrivato", "sopra");
        db=new DatabaseHelper(this);
        Log.d("arrivato", "sotto");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.start_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addEvent: {
                Log.d("menu", "activity da definire");
                break;
            }
            case R.id.modifyDrug: {
                Log.d("menu", "activity da definire");
                break;
            }

        }
        return false;

    }
}