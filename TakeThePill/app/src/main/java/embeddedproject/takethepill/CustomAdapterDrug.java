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

        TextView info = (TextView)convertView.findViewById(R.id.drugInfo);
        TextView descr = (TextView)convertView.findViewById(R.id.drugDescr);

        DrugEntity actual = getItem(position);

        info.setText(actual.getNome()+", "+actual.getTipo()+", "+actual.getScorte());
        descr.setText(actual.getDescrizione());

        return convertView;
    }


}
