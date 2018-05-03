package embeddedproject.takethepill;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;

import java.util.ArrayList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<AssumptionEntity> listAssunzioni;

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }












    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_today, container, false);

        // BOTTONE AGGIUNGI-TERAPIA
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Richiama AddEditTherapyActivity
                Intent intent = new Intent(view.getContext(), AddEditTherapyActivity.class);
                startActivity(intent);
            }
        });

    // LISTA ASSUNZIONI
        ListView listView = (ListView) view.findViewById(R.id.listAssumptions);

        listAssunzioni = new ArrayList<AssumptionEntity>();

        // Mettere query che restituisce la lista della ssunzioni con :data, ora, nomeFarmaco, stato, dosaggio, tipoFarmaco
        for(int i=0; i<20;i++) {
            listAssunzioni.add(new AssumptionEntity(null, null, "NomeFarmaco",
                    true, 5, "Pillole"));
        }

        CustomAdapterMain customAdapter = new CustomAdapterMain(listAssunzioni, getContext());
        listView.setAdapter(customAdapter);

    // QUANDO SI CLICCA SU UN ELEMENTO
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AssumptionEntity assunzione= listAssunzioni.get(position);

                // Messaggio Preso/non preso
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Hai assunto "+assunzione.getNomeFarmaco()+"?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ////AGGIUNGERE CODICE CHE MODIFICA L'ELEMENTO ASSUNZIONE DEL DATABASE
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///AGGIUNGERE CODICE CHE MODIFICA L'ELEMENTO ASSUNZIONE DEL DATABASE
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }
        });

        return view;
    }
















    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
