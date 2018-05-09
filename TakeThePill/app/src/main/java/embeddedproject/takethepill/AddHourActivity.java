package embeddedproject.takethepill;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class AddHourActivity extends AppCompatActivity {

    private ArrayList<int[]> listaOre;
    private int mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hour);

        // BOTTONI TOOLBAR SALVA E ANNULLA
        TextView tvSave = (TextView) findViewById(R.id.toolbar_save3);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hai cliccato su salva", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // PASSARE ad AddEtitTherapy la lista delle ore
            }
        });
        TextView tvAnnulla = (TextView) findViewById(R.id.toolbar_annulla3);
        tvAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   // Chiude l'aactivity e riapre la precedente
            }
        });

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // LISTA DELLE ORE
        ListView listView = (ListView) findViewById(R.id.listHours);

        // Operazione DATABASE: lista ore della terapia di ID=...
        listaOre = new ArrayList<int[]>();
        listaOre.add(new int[]{8,30});
        listaOre.add(new int[]{12,50});

        final CustomAdapterHour customAdapter = new CustomAdapterHour(listaOre, this);
        listView.setAdapter(customAdapter);

        // Bottone Seleziona Orario
        final EditText etTime=(EditText)findViewById(R.id.etNewHour);
        Button btnTimePicker=(Button)findViewById(R.id.btnSelectHour);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                mHour=hourOfDay;
                                mMinute=minute;

                                etTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        // QUANDO SI CLICCA SU UN ELEMENTO
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int[] ora= listaOre.get(position);

                /////////////////

            }
        });

        // Bottone Aggiungi Orario
        Button btnAddHour = (Button)findViewById(R.id.btnAddHour);
        btnAddHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaOre.add(new int[]{mHour,mMinute});
                customAdapter.notifyDataSetChanged();
            }
        });




    }
}
