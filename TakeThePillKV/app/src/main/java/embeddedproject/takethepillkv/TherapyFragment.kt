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


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TherapyFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
            val intent = Intent(view.context, AddEditTherapyActivity::class.java)
            intent.putExtra("nuova", true)
            intent.putExtra("id", "")
            startActivity(intent)
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

            val intent = Intent(view.context, AddEditTherapyActivity::class.java)
            intent.putExtra("nuova", false)
            intent.putExtra("id", idTerapia)
            startActivity(intent)
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





    companion object {
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
