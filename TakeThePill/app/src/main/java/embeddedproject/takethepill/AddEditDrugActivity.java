package embeddedproject.takethepill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class AddEditDrugActivity extends AppCompatActivity {

    private String nome;    // Rappresenta il nome del farmaco
    private String tipo;    // Rappresenta il tipo di farmaco
    private String descrizione; // Rappresenta la descrizione del farmaco
    private float prezzo;   // Rappresenta il prezzo del farmaco;
    private float scorte;   // Rappresenta le scorte del farmaco;

    private int elemSelez,prev; //Gestiscono la selezione del tipo di farmaco

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_drug);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String tipiFarmaci[] = {"Applicazione/i", "Capsula/e", "Fiala/e", "Goccia/e",
                "Grammo/i", "Inalazione/i","Iniezione/i","Milligrammo/i","Millilitro/i",
                "Pezzo/i","Pillola/e","Supposta/e","Unità"};
        //final String tipiFarmaci[]=DATABASE.getTypeList()...

        // TextView e EditText del layout:
        final EditText etNome = (EditText) findViewById(R.id.etDrugName);
        final TextView tvTipo = (TextView) findViewById(R.id.tvDrugType);
        final EditText etDescr = (EditText) findViewById(R.id.etDescription);
        final EditText etPrezzo = (EditText) findViewById(R.id.etDrugPrice);
        final EditText etScorte = (EditText) findViewById(R.id.etDrugQuantity);

        final boolean nuovo=getIntent().getBooleanExtra("nuovo",true);
        if(nuovo){
            nome="";
            tipo="Applicazione/i";
            elemSelez=prev=0;   // elemento selezionato nella lista TIPO farmaco
            descrizione="";
            prezzo=6;
            scorte=20;
            tvTipo.setText("Tipo: seleziona...");
        }else {
            // OPERAZIONE DATABASE restituisce i dati del farmaco di nome=...
            // ...
            nome="nomefarmaco";
            tipo="Iniezione/i";
            for(int i=0; i<tipiFarmaci.length;i++){
                if(tipiFarmaci[i].equals(tipo))elemSelez=prev=i;   // elemento selezionato nella lista TIPO farmaco
            }
            descrizione="descrizione blabla";
            prezzo=3;
            scorte=12;
            tvTipo.setText("Tipo: "+tipiFarmaci[elemSelez]);
        }

        etNome.setText(nome);
        etDescr.setText(descrizione);
        etPrezzo.setText(String.valueOf(prezzo));
        etScorte.setText(String.valueOf(scorte));


        // BOTTONI TOOLBAR SALVA E ANNULLA
        TextView tvSave = (TextView) findViewById(R.id.toolbar_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nuovo){
                    // OPERAZIONE DATABASE aggiungi farmaco
                }else{
                    // OPERAZIONE DATABASE modifica farmaco di nome=...
                }

                finish();   // Chiude l'activity e riapre la precedente
            }
        });
        TextView tvAnnulla = (TextView) findViewById(R.id.toolbar_annulla);
        tvAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   // Chiude l'activity e riapre la precedente
            }
        });


        // Bottone seleziona Tipo di farmaco
        ImageButton btnTipo = (ImageButton) findViewById(R.id.ibEditType);
        btnTipo.setOnClickListener(new View.OnClickListener() {

            //private String chooseItem;
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Tipo di Farmaco");


                builder.setSingleChoiceItems(tipiFarmaci, elemSelez, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int itemIndex) {
                        elemSelez=itemIndex;
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prev=elemSelez;
                        tvTipo.setText("Tipo: "+tipiFarmaci[elemSelez]);
                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Niente
                        elemSelez=prev;
                        tvTipo.setText("Tipo: "+tipiFarmaci[elemSelez]);
                    }
                });

                builder.show();

            }
        });

        // BOTTONE ELIMINA
        Button btnElimina = (Button) findViewById(R.id.btnDeleteDrug);
        if(nuovo) btnElimina.setVisibility(View.GONE);  //Se si sta inserendo un nuovo farmaco il bottone non deve essere visibile
        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Messaggio "SICURO? SI/NO"
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Sei sicuro?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Operazione DATABASE CHE CANCELLA IL FARMACO di nome=...
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
