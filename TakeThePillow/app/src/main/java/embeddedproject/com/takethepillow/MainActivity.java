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

import database.Str;

public class MainActivity extends AppCompatActivity {

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

        Log.d("tabella drug ", Str.CREATE_DRUG_TABLE);
        Log.d("tabella moment ", Str.CREATE_MOMENT_TABLE);
        Log.d("tabella hour ", Str.CREATE_HOUR_TABLE);
        Log.d("tabella terapy ", Str.CREATE_TERAPY_TABLE);
        Log.d("tabella assumption ", Str.CREATE_ASSUMPTION_TABLE);
        Log.d("tabella type ", Str.CREATE_TYPE_TABLE);



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