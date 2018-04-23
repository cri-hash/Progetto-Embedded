package embeddedproject.takethepill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterMain extends ArrayAdapter<AssumptionEntity> {

    private ArrayList<AssumptionEntity> dataSet;
    Context mContext;

    public CustomAdapterMain(ArrayList<AssumptionEntity> data, Context context) {
        super(context, R.layout.row_item_assumption, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_item_assumption, null);

        TextView drug = (TextView)convertView.findViewById(R.id.drug);
        TextView hour = (TextView)convertView.findViewById(R.id.hour);

        AssumptionEntity actual = getItem(position);
        drug.setText(actual.getNomeFarmaco());
        hour.setText(/*actual.getOra().toString()*/ "12.00");

        //???? Gestire dosaggio
        //???? Gestire tipofarmaco


        return convertView;
    }

}
