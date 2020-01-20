package dang.marty.roomatechores.presentation.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dang.marty.roomatechores.presentation.adapters.MyChoresAdapter
import dang.marty.roomatechores.R
import dang.marty.roomatechores.databinding.FragmentMyChoresBinding
import dang.marty.roomatechores.presentation.viewModels.MyChoresViewModel

class MyChoresFrag : Fragment() {

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
        recyclerView.adapter =
            MyChoresAdapter(listOf(""))
        binding.lifecycleOwner = this

        viewModel.getChoreListFromRemote()
        return binding.root
    }


    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyChoresViewModel::class.java)
        viewModel.choresObservable.observe(this, Observer<List<String>>{
            recyclerView.adapter =
                MyChoresAdapter(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
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
