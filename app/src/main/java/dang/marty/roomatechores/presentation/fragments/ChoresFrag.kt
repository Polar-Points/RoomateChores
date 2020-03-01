package dang.marty.roomatechores.presentation.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dang.marty.roomatechores.presentation.adapters.ChoresAdapter
import dang.marty.roomatechores.R
import dang.marty.roomatechores.databinding.FragmentMyChoresBinding
import dang.marty.roomatechores.presentation.viewModels.MyChoresViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChoresFrag : Fragment() {

    private lateinit var viewModel: MyChoresViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMyChoresBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_chores, container, false)
        recyclerView = binding.myChoresRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ChoresAdapter(listOf(""))
        binding.lifecycleOwner = this

        viewModel.getChoreListFromRemote()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val param1 = it.getInt(ARG_PARAM1)
            val param2 = it.getString(ARG_PARAM2)

            if (param1 == MEMBER_DETAIL) {
                view.findViewById<TextView>(R.id.date_text_field).text = param2
                var addButton = view.findViewById<Button>(R.id.add_chores_button)
                addButton.setOnClickListener {
                    findNavController().navigate(R.id.indvididualChoreFrag)
                }
            } else {
                view.findViewById<Button>(R.id.add_chores_button).visibility = View.GONE
            }
        }

    }


    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyChoresViewModel::class.java)
        viewModel.choresObservable.observe(this, Observer<List<String>>{
            recyclerView.adapter =
                ChoresAdapter(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    companion object {

        const val MY_CHORES = 0
        const val MEMBER_DETAIL = 1
        private const val TYPE_TAG = "type"

        fun newInstance(type: Int, name: String) =
            ChoresFrag().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, type)
                    putString(ARG_PARAM2, name)
                }
            }
    }

    //    fun getUserInfo(){
//        val user = FirebaseAuth.getInstance().currentUser
//        user?.let {firebaseUser ->
//            firebaseUser.getIdToken(true).addOnCompleteListener {
//                if(it.isSuccessful){
//                    var idToken = it.result?.token
//
//                    var groupId = codeField.text.toString()
//
//                    val db = FirebaseFirestore.getInstance()
//
//                    db.document("groups/$groupId/users/$idToken").get().addOnSuccessListener {
//                        Timber.d("CHORES FOR %s %s",idToken,it.getDate("ChoreList"))
//                    }
//
//                } else {
//                    Snackbar.make(view!!, "Token not valid " + it.exception, Snackbar.LENGTH_LONG)
//                }
//            }
//        }
//    }
}
