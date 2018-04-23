package embeddedproject.com.takethepillow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cristian on 10/04/2018.
 */


    public class CustomAdapter extends ArrayAdapter<Impegno>{

        public CustomAdapter(Context context, int textViewResourceId,
                             List<Impegno> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note, null);

            TextView drug = (TextView)convertView.findViewById(R.id.drug);
            TextView hour = (TextView)convertView.findViewById(R.id.hour);

            Impegno actual = getItem(position);
            drug.setText((CharSequence) actual.getDrug());
            hour.setText(actual.getHour());

            return convertView;
        }

    }


