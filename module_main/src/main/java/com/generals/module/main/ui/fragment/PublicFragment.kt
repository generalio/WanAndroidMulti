package com.generals.module.main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generals.module.main.R
import com.generals.module.main.model.bean.PassageInfo
import com.generals.module.main.ui.activity.MainActivity
import com.generals.module.main.ui.adapter.PassageAdapter
import com.generals.module.main.viewmodel.PublicViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class PublicFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    lateinit var mainActivity: MainActivity
    lateinit var adapter: PassageAdapter
    var page: Int = 1
    var passageInfo: MutableList<PassageInfo> = mutableListOf()
    lateinit var progressBar: ProgressBar

    val publicViewModel : PublicViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        progressBar = view.findViewById(R.id.public_progressbar)
        tabLayout = view.findViewById(R.id.tab_public)
        recyclerView = view.findViewById(R.id.public_recyclerView)

        publicViewModel.getPublicAuthor()

        initEvent()

    }

    fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    private fun initEvent() {
        publicViewModel.authorInfoLiveData.observe(viewLifecycleOwner) { authorInfo ->
            //加载公众号列表
            for (author in authorInfo) {
                tabLayout.addTab(tabLayout.newTab().setText(author.name))
            }

            publicViewModel.getPublicPassage(0, authorInfo[0].id)

            //对tab选择的监听
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        adapter.submitList(listOf())
                        progressBar.visibility = View.VISIBLE
                        publicViewModel.getPublicPassage(0, authorInfo[tab.position].id)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }

        publicViewModel.publicPassageLiveData.observe(viewLifecycleOwner) { passageData ->

            page = 1
            passageInfo = passageData.toMutableList()
            progressBar.visibility = View.GONE
            adapter = PassageAdapter(mainActivity)
            recyclerView.layoutManager = LinearLayoutManager(mainActivity)
            recyclerView.adapter = adapter
            adapter.submitList(passageData)

        }
    }
}