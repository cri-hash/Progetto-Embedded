package embeddedproject.takethepill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterHour extends ArrayAdapter<int[]> {

    private ArrayList<int[]> dataSet;
    Context mContext;

    public CustomAdapterHour(ArrayList<int[]> data, Context context) {
        super(context, R.layout.row_item_hour, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_item_hour, null);

        TextView hour = (TextView)convertView.findViewById(R.id.tvSingleHour);
        ImageButton ibDelete=(ImageButton)convertView.findViewById(R.id.ibDeleteHour);

        int[] actual = getItem(position);

        hour.setText(String.valueOf(actual[0])+" : "+String.valueOf(actual[1]));

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///????????????
            }
        });

        return convertView;
    }
}
