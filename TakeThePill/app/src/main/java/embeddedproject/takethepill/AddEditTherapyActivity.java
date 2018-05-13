package embeddedproject.takethepill;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;

public class AddEditTherapyActivity extends AppCompatActivity {

    private therapyEntityDB terapia;    // Rappresenta la terapia in considerazione
    private String drugList[];  // Rappresenta la lista dei farmaci
    private boolean[] giorniSelezionati;    // Usata nella selezione dei giorni
    private final String giorni[] = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_therapy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        // Database
        db=new DatabaseHelper(this);

        // Lista dei farmaci
        List<DrugEntity> l=db.getAllDrugs();
        drugList=new String[l.size()];
        for(int i=0;i<l.size();i++) drugList[i]=l.get(i).getNome();

        // TextView ed EditText del layout
        final TextView tvDrugName=(TextView)findViewById(R.id.tvDrugName);
        final EditText etQuantity=(EditText)findViewById(R.id.etDrugQuantity2);
        final TextView tvDuration=(TextView)findViewById(R.id.tvDuration);
        final TextView tvDays=(TextView)findViewById(R.id.tvDays);
        final TextView tvNotify=(TextView)findViewById(R.id.tvNotify);

        // Stiamo creando una nuova terapia?
        final boolean nuova=getIntent().getBooleanExtra("nuova",true);
        final int id=getIntent().getIntExtra("id",-1);

        // Impostare le variabili:
        if(nuova) { // Se è una NUOVA terapia
            terapia=new therapyEntityDB(null,-1,null,true,false,false,false,false,false,false,1,null);
            tvDrugName.setText("Seleziona farmaco ...");
        }
        else{   // Se è MODIFICA terapia
            terapia=db.getTherapy(id);
            tvDrugName.setText(terapia.getDrug()+" "+terapia.getID());
        }

        // TextView Quantità
        etQuantity.setText(String.valueOf(terapia.getDosaggio()));

        // TextView Durata
        final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(terapia.getDays()==-1)tvDuration.setText("Durata: Senza Limiti");
        else if(terapia.getDays()!=null)tvDuration.setText("Durata: per "+terapia.getDays().toString()+ " giorni");
        else tvDuration.setText("Durata: fino al "+formatter.format(terapia.getDateEnd()));

        giorniSelezionati = new boolean[]{terapia.isMon(), terapia.isTue(), terapia.isWed(), terapia.isThu(), terapia.isFri(), terapia.isSat(), terapia.isSun()};

        // TextView Giorni
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < giorniSelezionati.length; i++) {
            if (giorniSelezionati[i]) {
                if (s.length() > 0) s.append(", ");
                s.append(giorni[i]);
            }
        }
        if (s.toString().trim().equals("")) {
            tvDays.setText("Giorni: nessuno");
            s.setLength(0);
        } else {
            tvDays.setText("Giorni: " + s);
        }

        // TextView Notifica
        if(terapia.getNotify()==null) tvNotify.setText("Notifiche: nessuna");
        else tvNotify.setText("Notifiche: "+terapia.getNotify().toString()+" min prima");


        // BOTTONI TOOLBAR SALVA E ANNULLA
        TextView tvSave = (TextView) findViewById(R.id.toolbar_save2);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nuova){

                    if(terapia.getDrug()==null){    //Se l'utente non ha inserito il farmaco
                        Snackbar snackbar = Snackbar
                                .make(v, "Devi inserire un Farmaco!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        // Operazione database inserisci nuova terapia
                        // Operazione orari
                    }

                }else{
                    // Operazione database modifica terapia di id=...
                }
                finish();
            }
        });
        TextView tvAnnulla = (TextView) findViewById(R.id.toolbar_annulla2);
        tvAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   // Chiude l'aactivity e riapre la precedente
            }
        });

        // BOTTONE "Nome farmaco"
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
                        terapia.setDrug(drugList[i]);
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

                if(terapia.getDays()==null){    // DataFine
                    rdbtNoLimits.setChecked(false);
                    rdbtUntil.setChecked(true);
                    rdbtNumbDays.setChecked(false);
                    etUntil.setText(terapia.getDateEnd().toString());
                    etDaysNumb.setText("");
                }else if(terapia.getDays()==-1){ //Senza Limiti
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
                    etDaysNumb.setText(terapia.getDays().toString());
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
                        if(rdbtNoLimits.isChecked()){
                            terapia.setDays(-1);
                            terapia.setDateEnd(null);
                            tvDuration.setText("Durata: Senza Limiti");
                        }
                        else if(rdbtNumbDays.isChecked()) {
                            terapia.setDays(Integer.valueOf(etDaysNumb.getText().toString()));
                            terapia.setDateEnd(null);
                            tvDuration.setText("Durata: per "+terapia.getDays().toString()+ " giorni");
                        }else {
                            terapia.setDays(null);
                            Date date = null;
                            try {
                                date = formatter.parse(etUntil.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            terapia.setDateEnd(date);
                            tvDuration.setText("Durata: fino al "+formatter.format(terapia.getDateEnd()));
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
        ImageButton btnHour = (ImageButton) findViewById(R.id.ibEditHour);
        btnHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddHourActivity.class);
                intent.putExtra("id",id);   // Passo l'id della terapia (se è nuova id="nuova")
                startActivity(intent);
            }
        });



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

                if(terapia.getNotify()==null){
                    rbNotNotify.setChecked(true);
                    rbNotify.setChecked(false);
                    etNotify.setText("");
                }
                else {
                    rbNotNotify.setChecked(false);
                    rbNotify.setChecked(true);
                    etNotify.setText(terapia.getNotify().toString());
                }

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Fai qualcosa quando premi il pulsante "ok"
                        if(rbNotify.isChecked()){
                            if(etNotify.getText().toString().equals(""))terapia.setNotify(0);
                            else terapia.setNotify(Integer.valueOf(etNotify.getText().toString()));
                            tvNotify.setText("Notifiche: "+terapia.getNotify().toString() + " min prima");
                        } else {
                            terapia.setNotify(null);
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



        // BOTTONE ELIMINA
        Button btnElimina = (Button) findViewById(R.id.btnDeleteTherapy);
        if(nuova) btnElimina.setVisibility(View.GONE);  //Se si sta inserendo una nuova terapia il bottone non deve essere visibile
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
                        db.removeTherapyBYId(terapia.getID());
                        finish();
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
