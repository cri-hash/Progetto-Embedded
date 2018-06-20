package embeddedproject.takethepillkv

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TodayFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TodayFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TodayFragment : Fragment() {
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

    lateinit var listAssunzioni: ArrayList<AssumptionEntity>
    lateinit var customAdapter: CustomAdapterMain
    lateinit var db: DatabaseHelper




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today, container, false)

        // BOTTONE AGGIUNGI-TERAPIA
        val fab = view.findViewById(R.id.fab_main) as FloatingActionButton
        fab.setOnClickListener { view ->
            // Richiama AddEditTherapyActivity
            //val intent = Intent(view.context, AddEditTherapyActivity::class.java)
            //startActivity(intent)
            Log.d("TodayFragment","bottone premuto")
        }



        // LISTA ASSUNZIONI
        val listView = view.findViewById(R.id.listAssumptions) as ListView

        listAssunzioni = ArrayList<AssumptionEntity>()

        db = DatabaseHelper(context)
        listAssunzioni = db.getAssumptionByDate(Calendar.getInstance().time) as ArrayList<AssumptionEntity>


        customAdapter = CustomAdapterMain(listAssunzioni, context)
        listView.adapter = customAdapter

        // QUANDO SI CLICCA SU UN ELEMENTO
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val assunzione = listAssunzioni[position]

            // Messaggio Preso/non preso
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage("Hai assunto " + assunzione.nomeFarmaco + "?")
            builder.setPositiveButton("Si") { dialog, which ->
                // Funzione database Aggiorna stato assunzione
                db.setAssumption(assunzione, true)

                // Aggiornare le scorte del farmaco
                if (!assunzione.stato) {
                    val farmaco = db.getDrugByName(assunzione.nomeFarmaco)
                    farmaco!!.setScorte(farmaco.getScorte() - assunzione.dosaggio)
                    db.updateDrug(farmaco)
                }

                // Ricarico la lista degli elementi
                listAssunzioni.clear()
                listAssunzioni.addAll(db.getAssumptionByDate(Calendar.getInstance().time) as ArrayList<AssumptionEntity>)

                customAdapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("No") { dialog, which ->
                // Funzione database Aggiorna stato assunzione
                db.setAssumption(assunzione, false)

                // Aggiornare le scorte del farmaco
                if (assunzione.stato) {
                    val farmaco = db.getDrugByName(assunzione.nomeFarmaco)
                    farmaco!!.setScorte(farmaco.getScorte() + assunzione.dosaggio)
                    db.updateDrug(farmaco)
                }

                // Ricarico la lista degli elementi
                listAssunzioni.clear()
                listAssunzioni.addAll(db.getAssumptionByDate(Calendar.getInstance().time) as ArrayList<AssumptionEntity>)

                customAdapter.notifyDataSetChanged()
            }
            builder.setCancelable(false)
            builder.show()
        }

        return view
    }


    override fun onResume() {
        super.onResume()
        listAssunzioni.clear()
        listAssunzioni.addAll(db.getAssumptionByDate(Calendar.getInstance().time) as ArrayList<AssumptionEntity>)

        customAdapter.notifyDataSetChanged()
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TodayFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
