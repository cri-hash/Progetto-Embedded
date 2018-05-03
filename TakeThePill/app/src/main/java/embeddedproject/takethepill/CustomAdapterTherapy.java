package embeddedproject.takethepill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterTherapy extends ArrayAdapter<therapyEntityDB> {

    private ArrayList<therapyEntityDB> dataSet;
    Context mContext;

    public CustomAdapterTherapy(ArrayList<therapyEntityDB> data, Context context) {
        super(context, R.layout.row_item_therapy, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_item_therapy, null);

        TextView drug = (TextView)convertView.findViewById(R.id.drugName);
        TextView week = (TextView)convertView.findViewById(R.id.weekDays);
        TextView hours = (TextView)convertView.findViewById(R.id.hoursTV);

        therapyEntityDB actual = getItem(position);

        drug.setText(actual.getDrug());
        week.setText(Integer.toString(actual.getDays()));
        //hours.setText(actual.getOre()); ??

        //???? Gestire immagine Pillola
        //???? Gestire immagine notifica


        return convertView;
    }

}
