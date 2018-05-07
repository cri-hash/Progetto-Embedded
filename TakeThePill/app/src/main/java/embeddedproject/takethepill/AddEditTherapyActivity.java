package embeddedproject.takethepill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

public class AddEditTherapyActivity extends AppCompatActivity {

    String titleSelectDrug =  "Seleziona farmaco";
    ArrayList<DrugEntity> lista;
    String selectedDrug;
    double quantity;
    boolean[] giorniSelezionati;
    Integer minNotifica;
    Integer nGiorni;
    String dataFine;

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



        String id=getIntent().getStringExtra("id");

        if(id.equals("nuova")) { // Se è una NUOVA terapia
            selectedDrug = null;
            quantity = 1;
            giorniSelezionati = new boolean[]{true, false, false, false, false, false, false};
            minNotifica = null;
            nGiorni=-1;
            dataFine="";
        }
        else{   // Se è MODIFICA terapia
            // Funzione DATABASE informazioni sul farmaco di id=1234...
            // Parametri da impostare...:
            selectedDrug = null;
            quantity = 1;
            giorniSelezionati = new boolean[]{true, false, false, false, false, false, false};
            minNotifica = null;
            nGiorni=10;
            dataFine="";
        }

        final TextView tvDrugName=(TextView)findViewById(R.id.tvDrugName);
        tvDrugName.setText("Seleziona farmaco ...");

        final EditText etQuantity=(EditText)findViewById(R.id.etDrugQuantity2);
        etQuantity.setText(String.valueOf(quantity));

        final TextView tvDuration=(TextView)findViewById(R.id.tvDuration);
        tvDuration.setText("Durata: ...");

        final TextView tvDays=(TextView)findViewById(R.id.tvDays);
        tvDays.setText("Giorni: ...");

        final TextView tvNotify=(TextView)findViewById(R.id.tvNotify);
        if(minNotifica==null) tvNotify.setText("Notifiche: nessuna");
        else tvNotify.setText("Notifiche: "+minNotifica+" min prima");



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
        ImageButton btnDuration = (ImageButton) findViewById(R.id.ibEditDuration);
        btnDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditTherapyActivity.this);

                final View durationView = getLayoutInflater().inflate(R.layout.alert_duration, null);
                builder.setView(durationView);

                final RadioButton rdbtNoLimits = (RadioButton) durationView.findViewById(R.id.noLimits);
                final RadioButton rdbtUntil= (RadioButton) durationView.findViewById(R.id.untilRdBtn);
                final RadioButton rdbtNumbDays= (RadioButton) durationView.findViewById(R.id.number_days_RdBtn);
                final EditText etUntil = (EditText) durationView.findViewById(R.id.etUntil);
                final EditText etDaysNumb = (EditText) durationView.findViewById(R.id.etDaysNumber);

                if(nGiorni==null){    // DataFine
                    rdbtNoLimits.setChecked(false);
                    rdbtUntil.setChecked(true);
                    rdbtNumbDays.setChecked(false);
                    etUntil.setText(dataFine);
                    etDaysNumb.setText("");
                }else if(nGiorni==-1){ //Senza Limiti
                    rdbtNoLimits.setChecked(true);
                    rdbtUntil.setChecked(false);
                    rdbtNumbDays.setChecked(false);
                    etUntil.setText("");
                    etDaysNumb.setText("");
                }
                else{   // n giorni
                    rdbtNoLimits.setChecked(false);
                    rdbtUntil.setChecked(false);
                    rdbtNumbDays.setChecked(true);
                    etUntil.setText("");
                    etDaysNumb.setText(nGiorni.toString());
                }

                rdbtNoLimits.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etUntil.setEnabled(false);
                        etDaysNumb.setEnabled(false);
                    }
                });
                rdbtUntil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etUntil.setEnabled(true);
                        etDaysNumb.setEnabled(false);
                    }
                });
                rdbtNumbDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etUntil.setEnabled(false);
                        etDaysNumb.setEnabled(true);
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Fai qualcosa quando si preme il pulsante "ok"
                        if(rdbtNoLimits.isChecked()){
                            nGiorni=-1;
                            dataFine=null;
                            tvDuration.setText("Durata: Senza Limiti");
                        }
                        else if(rdbtNumbDays.isChecked()) {
                            nGiorni = Integer.valueOf(etDaysNumb.getText().toString());
                            dataFine=null;
                            tvDuration.setText("Durata: per "+nGiorni.toString()+ " giorni");
                        }else {
                            nGiorni = null;
                            dataFine=etUntil.getText().toString();
                            tvDuration.setText("Durata: fino al "+dataFine);
                        }


                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Bottone annulla, nessuna azione
                    }
                });

                builder.show();
            }
        });



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
        ImageButton btnNotify = (ImageButton) findViewById(R.id.ibEditNotify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditTherapyActivity.this);

                final View notifyView = getLayoutInflater().inflate(R.layout.alert_notify, null);
                builder.setView(notifyView);

                final RadioButton rbNotNotify=(RadioButton)notifyView.findViewById(R.id.notNotify);
                final RadioButton rbNotify=(RadioButton)notifyView.findViewById(R.id.minBefore);
                final EditText etNotify=(EditText)notifyView.findViewById(R.id.etNotify);

                if(minNotifica==null){
                    rbNotNotify.setChecked(true);
                    rbNotify.setChecked(false);
                    etNotify.setText("");
                }
                else {
                    rbNotNotify.setChecked(false);
                    rbNotify.setChecked(true);
                    etNotify.setText(minNotifica.toString());
                }

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Fai qualcosa quando premi il pulsante "ok"
                        if(rbNotify.isChecked()){
                            if(etNotify.getText().toString().equals(""))minNotifica=0;
                            else minNotifica=Integer.valueOf(etNotify.getText().toString());
                            tvNotify.setText("Notifiche: "+minNotifica + " min prima");
                        } else {
                            minNotifica=null;
                            tvNotify.setText("Notifiche: nessuna");
                        }

                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Nessuna azione quando si preme il pulsante "annulla"
                    }
                });

                builder.show();
            }
        });



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
