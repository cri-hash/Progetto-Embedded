package embeddedproject.takethepill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class AddEditDrugActivity extends AppCompatActivity {

    int elemSelez,prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_drug);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // BOTTONI TOOLBAR SALVA E ANNULLA
        TextView tvSave = (TextView) findViewById(R.id.toolbar_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hai cliccato su salva", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // AGGIUNGI CODICE DATABASE
            }
        });
        TextView tvAnnulla = (TextView) findViewById(R.id.toolbar_annulla);
        tvAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   // Chiude l'activity e riapre la precedente
            }
        });

        // TextView Tipo farmaco
        final TextView tvTipo = (TextView) findViewById(R.id.tvDrugType);
        tvTipo.setText("Tipo: seleziona ...");


        elemSelez=prev=3;   // elemento selezionato TIPO farmaco


        // Bottone seleziona Tipo di farmaco
        ImageButton btnTipo = (ImageButton) findViewById(R.id.ibEditType);
        btnTipo.setOnClickListener(new View.OnClickListener() {

            //private String chooseItem;
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Tipo di Farmaco");

                final String listItemArr[] = {"Applicazione/i", "Capsula/e", "Fiala/e", "Goccia/e",
                        "Grammo/i", "Inalazione/i","Iniezione/i","Milligrammo/i","Millilitro/i",
                        "Pezzo/i","Pillola/e","Supposta/e","Unità"};
                builder.setSingleChoiceItems(listItemArr, elemSelez, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int itemIndex) {
                        elemSelez=itemIndex;
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prev=elemSelez;
                        tvTipo.setText("Tipo: "+listItemArr[elemSelez]);
                    }
                });

                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Niente
                        elemSelez=prev;
                        tvTipo.setText("Tipo: "+listItemArr[elemSelez]);
                    }
                });

                builder.show();

            }
        });

        Button btnElimina = (Button) findViewById(R.id.btnDeleteDrug);
        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Messaggio "SICURO? SI/NO"
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Sei sicuro?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // AGGIUNGI CODICE DATABASE CHE CANCELLA IL FARMACO
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
