package embeddedproject.takethepillkv

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import database.DatabaseHelper
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TherapyFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TherapyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TherapyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    lateinit var listaTerapie: ArrayList<TherapyEntityDB>
    lateinit var customAdapter: CustomAdapterTherapy
    lateinit var listView: ListView
    lateinit var db: DatabaseHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_therapy, container, false)

        // BOTTONE AGGIUNGI TERAPIA
        val fab = view.findViewById(R.id.fab_therapy) as FloatingActionButton
        fab.setOnClickListener { view ->
            /*val intent = Intent(view.context, AddEditTherapyActivity::class.java)
            intent.putExtra("nuova", true)
            intent.putExtra("id", "")
            startActivity(intent)*/
            Log.d("TherapyFragment","bottone premuto")
        }


        // LISTA DELLE  TERAPIE
        listView = view.findViewById(R.id.listTherapies) as ListView

        listaTerapie = ArrayList()
        db = DatabaseHelper(context)
        listaTerapie = db.getAllTherapies() as ArrayList<TherapyEntityDB>

        customAdapter = CustomAdapterTherapy(listaTerapie, context)
        listView.adapter = customAdapter

        // Quando si clicca su un elemento
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val terapia = listaTerapie[position]
            val idTerapia = terapia.getID()

            /*val intent = Intent(view.context, AddEditTherapyActivity::class.java)
            intent.putExtra("nuova", false)
            intent.putExtra("id", idTerapia)
            startActivity(intent)*/
            Log.d("TherapyFragment","elemento cliccato")
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        listaTerapie.clear()
        listaTerapie.addAll(db.getAllTherapies() as ArrayList<TherapyEntityDB>)
        customAdapter.notifyDataSetChanged()
    }











    /*// TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }*/

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    /*interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TherapyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TherapyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
