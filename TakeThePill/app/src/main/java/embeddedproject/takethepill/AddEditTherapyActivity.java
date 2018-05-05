package embeddedproject.takethepill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddEditTherapyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_therapy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //Azione pulsante salva
        TextView tvSave = (TextView) findViewById(R.id.toolbar_save2);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hai cliccato su salva", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        //Azione pressione pulsante "Nome farmaco"
        Button drugName = findViewById(R.id.name_drug);
        drugName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditTherapyActivity.this, activity_select_drug.class);
                startActivity(intent);
            }
        });

        //Azione pressione pulsante "Durata"
        Button duration = findViewById(R.id.edit_duration);
        duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditTherapyActivity.this, activity_duration.class);
                startActivity(intent);
            }
        });




    }


}
