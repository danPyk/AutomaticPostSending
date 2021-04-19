package com.danpyk.automaticpostsending.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.danpyk.automaticpostsending.R
import com.danpyk.automaticpostsending.databinding.MainFragmentBinding
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var binding: MainFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


           applyWork()

        //
           //viewModel.sendMSG()

        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
        
    }

     fun applyWork() {

        //val data = Data.Builder().putAll(mapOf(MessageWork.MESSAGE to txt)).build()
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
        }.build()
        val request =
            OneTimeWorkRequestBuilder<MessageWork>()
                .setConstraints(constraints)
                .setInitialDelay(1000, TimeUnit.MILLISECONDS)
                .build()
        val workManager = WorkManager.getInstance(context!!)
        workManager.enqueue(request)
    }

}