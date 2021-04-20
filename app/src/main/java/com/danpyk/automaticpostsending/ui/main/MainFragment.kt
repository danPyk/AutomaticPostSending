package com.danpyk.automaticpostsending.ui.main

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.*
import com.danpyk.automaticpostsending.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var binding: MainFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        createJob()

        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel

    }

    private fun createJob(){
        val jobScheduler = activity?.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(123, ComponentName(context!!, JobServ::class.java))
        val job = jobInfo.setPeriodic(900000)
               // .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)

                .build()

        jobScheduler.schedule(job)
    }

}

