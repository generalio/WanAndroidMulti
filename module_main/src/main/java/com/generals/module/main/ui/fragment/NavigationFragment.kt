package com.generals.module.main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generals.module.main.R
import com.generals.module.main.model.bean.NavigationInfo
import com.generals.module.main.ui.activity.MainActivity
import com.generals.module.main.ui.adapter.NavigationContentAdapter
import com.generals.module.main.ui.adapter.NavigationListAdapter
import com.generals.module.main.viewmodel.NavigationViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NavigationFragment : Fragment() , NavigationListAdapter.OnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var listRecyclerView: RecyclerView
    lateinit var mTvNavigation: TextView
    lateinit var contentRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    val navigationViewModel : NavigationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity
        progressBar = view.findViewById(R.id.navigation_progressbar)
        listRecyclerView = view.findViewById(R.id.navigation_recyclerview)
        contentRecyclerView = view.findViewById(R.id.navigation_content_recyclerView)
        mTvNavigation = view.findViewById(R.id.tv_navigation)

        navigationViewModel.getNavigationInfo()

        initEvent()
    }

    fun scrollToTop() {
        contentRecyclerView.smoothScrollToPosition(0)
    }

    fun initEvent() {
        navigationViewModel.navigationInfo.observe(viewLifecycleOwner) { navigationInfo ->
            progressBar.visibility = View.GONE
            listRecyclerView.layoutManager = LinearLayoutManager(mainActivity)
            listRecyclerView.adapter = NavigationListAdapter(mainActivity, navigationInfo, this)

            mTvNavigation.text = navigationInfo[0].name

            //设置流式布局
            contentRecyclerView.layoutManager = FlexboxLayoutManager(mainActivity)
            contentRecyclerView.adapter =
                NavigationContentAdapter(mainActivity, navigationInfo[0].articles)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(navigationItem: NavigationInfo) {
        mTvNavigation.text = navigationItem.name
        val adapter = NavigationContentAdapter(mainActivity, navigationItem.articles)
        contentRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}