package embeddedproject.takethepill;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AddEditTherapyActivity extends AppCompatActivity {

    String titleSelectDrug =  "Seleziona farmaco";
    ArrayList<DrugEntity> lista;
    String selectedDrug;
    boolean[] giorniSelezionati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_therapy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        // BOTTONI TOOLBAR SALVA E ANNULLA
        TextView tvSave = (TextView) findViewById(R.id.toolbar_save2);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hai cliccato su salva", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // AGGIUNGI CODICE DATABASE
            }
        });
        TextView tvAnnulla = (TextView) findViewById(R.id.toolbar_annulla2);
        tvAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   // Chiude l'aactivity e riapre la precedente
            }
        });


        // Funzione DATABASE lista farmaci.....
        final String[] drugList = {"Farmaco 1", "Farmaco 2", "Farmaco 3",
                "Farmaco 1", "Farmaco 2", "Farmaco 3","Farmaco 1", "Farmaco 2", "Farmaco 3","Farmaco 1", "Farmaco 2", "Farmaco 3"};
        selectedDrug=null;
        double quantity = 200;
        giorniSelezionati=new boolean[]{true, false, false, false, false, false, false};

        final TextView tvDrugName=(TextView)findViewById(R.id.tvDrugName);
        tvDrugName.setText("Seleziona farmaco ...");

        EditText etQuantity=(EditText)findViewById(R.id.etDrugQuantity2);
        etQuantity.setText(String.valueOf(quantity));

        final TextView tvDays=(TextView)findViewById(R.id.tvDays);
        tvDrugName.setText("Giorni: ...");



        // Bottone "Nome farmaco"
        ImageButton drugName = (ImageButton) findViewById(R.id.ibEditDrugName);
        drugName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditTherapyActivity.this);
                builder.setTitle("Seleziona Farmaco");

                builder.setItems(drugList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvDrugName.setText(drugList[i]);
                        selectedDrug=drugList[i];
                    }
                });
                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Niente
                    }
                });

                builder.show();

            }
        });


        // BOTTONE DURATA
        //....



        // BOTTONE GIORNI
        ImageButton btnDays = (ImageButton) findViewById(R.id.ibEditDays);
        btnDays.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditTherapyActivity.this);
                builder.setTitle("Seleziona i giorni");

                final String giorni[] = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
                builder.setMultiChoiceItems(giorni, giorniSelezionati, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int itemIndex, boolean checked) {

                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // giorniSelezionati viene modificato in automatico

                        ListView list = ((AlertDialog) dialogInterface).getListView();

                        // make selected item in the comma separated string
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);

                            if (checked) {
                                if (stringBuilder.length() > 0) stringBuilder.append(", ");
                                stringBuilder.append(list.getItemAtPosition(i));

                            }
                        }

                        if (stringBuilder.toString().trim().equals("")) {
                            tvDays.setText("Giorni: nessuno");
                            stringBuilder.setLength(0);
                        } else {
                            tvDays.setText("Giorni: " + stringBuilder);
                        }
                    }

                });

                builder.show();

            }
        });


        // BOTTONE ORA
        //.....


        // BOTTONE NOTIFICHE
        //......



        // Bottone ELIMINA
        Button btnElimina = (Button) findViewById(R.id.btnDeleteTherapy);
        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Messaggio "SICURO? SI/NO"
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                builder.setTitle("Sei sicuro?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // AGGIUNGI CODICE DATABASE CHE CANCELLA LA TERAPIA
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Niente
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }
        });


    }


}
