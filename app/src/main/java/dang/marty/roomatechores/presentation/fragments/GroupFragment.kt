package dang.marty.roomatechores.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dang.marty.roomatechores.R
import dang.marty.roomatechores.presentation.adapters.GroupFragAdapter
import timber.log.Timber


class GroupFragment : Fragment() {

    private lateinit var gridView: GridView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_admin, container, false)

        gridView = view.findViewById(R.id.members_gridview)
        gridView.adapter = GroupFragAdapter(context!!, listOf("David", "August", "marty"))
        gridView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
//                findNavController().navigate(action)

                val member = view.findViewById<TextView>(R.id.member_name)
                Timber.d("Stuff %s", member.text )
            }

        return view
    }


}
