package embeddedproject.takethepill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class AddEditDrugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_drug);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        ImageButton btnTipo = (ImageButton) findViewById(R.id.ibEditType);
        btnTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hai cliccato su modifica tipo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // AGGIUNGERE ALERTDIALOG
                // AGGIUNGI CODICE DATABASE

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
