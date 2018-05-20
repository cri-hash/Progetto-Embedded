package embeddedproject.takethepill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterDrug extends ArrayAdapter<DrugEntity> {

    private ArrayList<DrugEntity> dataSet;
    Context mContext;

    public CustomAdapterDrug(ArrayList<DrugEntity> data, Context context) {
        super(context, R.layout.row_item_drug, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_item_drug, null);

        TextView name = (TextView)convertView.findViewById(R.id.drugName);
        TextView type = (TextView)convertView.findViewById(R.id.tvType);
        TextView scorte = (TextView)convertView.findViewById(R.id.tvScorte);
        TextView prezzo = (TextView)convertView.findViewById(R.id.tvPrezzo);
        TextView descr = (TextView)convertView.findViewById(R.id.drugDescr);

        DrugEntity actual = getItem(position);

        name.setText(actual.getNome());
        type.setText("Tipo: "+actual.getTipo());
        scorte.setText("Scorte: " + actual.getScorte());
        prezzo.setText("Prezzo: " +actual.getPrezzo()+ "€");
        descr.setText("Descrizione: " + actual.getDescrizione());

        return convertView;
    }


}
