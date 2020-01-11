package dang.marty.roomatechores.ui


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
import dang.marty.roomatechores.MyChoresAdapter
import dang.marty.roomatechores.R
import dang.marty.roomatechores.databinding.FragmentMyChoresBinding
import dang.marty.roomatechores.viewModels.MyChoresViewModel
import timber.log.Timber

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
        recyclerView.adapter = MyChoresAdapter(listOf("Item 1","ITEM 2", "ITEM 3"))
        binding.lifecycleOwner = this

        binding.button.setOnClickListener{ clickMe() }

        return binding.root
    }

    fun clickMe() {
        Timber.d("CLICK CALLED")
        viewModel.getChoresList()
    }


    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MyChoresViewModel::class.java)
        viewModel.choresObservable.observe(this, Observer<List<String>>{
            recyclerView.adapter = MyChoresAdapter(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }
}
