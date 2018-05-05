package embeddedproject.takethepill;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_select_drug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_drug);

        FloatingActionButton newDrug =  findViewById(R.id.new_drug);
        newDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (activity_select_drug.this, AddEditDrugActivity.class);
                startActivity(intent);
            }
        });
    }

}
