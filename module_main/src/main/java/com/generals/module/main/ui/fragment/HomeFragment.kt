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
import com.generals.module.main.model.bean.CarouselInfo
import com.generals.module.main.model.bean.PassageInfo
import com.generals.module.main.ui.activity.MainActivity
import com.generals.module.main.ui.adapter.HomePassageAdapter
import com.generals.module.main.viewmodel.HomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    lateinit var passageRecyclerView2: RecyclerView
    var passageCard: MutableList<PassageInfo> = mutableListOf()
    var finalCarouselPassage: MutableList<CarouselInfo> = mutableListOf()
    lateinit var passageAdapter: HomePassageAdapter
    var page: Int = 1
    lateinit var progressBar: ProgressBar
    lateinit var mainActivity: MainActivity

    val homeViewmodel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        homeViewmodel.getCarousel()
        homeViewmodel.getPassage(0)

        passageRecyclerView2 = view.findViewById(R.id.home_recyclerView)
        progressBar = view.findViewById(R.id.home_progressbar)

        initEvent()
    }

    fun scrollToTop() {
        passageRecyclerView2.smoothScrollToPosition(0)
    }

    private fun initEvent() {

        homeViewmodel.carouselLiveData.observe(viewLifecycleOwner) { carouselPassage ->
            //多添加两张，模拟最后一张到第一张
            finalCarouselPassage.add(0, carouselPassage[carouselPassage.size - 1])
            finalCarouselPassage.addAll(carouselPassage)
            finalCarouselPassage.add(carouselPassage.size + 1, carouselPassage[0])
        }

        homeViewmodel.homePassageLiveData.observe(viewLifecycleOwner) { passageData ->
            progressBar.visibility = View.GONE
            homeViewmodel.getPersonalInfo()
            passageCard.addAll(passageData)
            passageAdapter =
                HomePassageAdapter(mainActivity, finalCarouselPassage.toList())
            passageRecyclerView2.layoutManager = LinearLayoutManager(mainActivity)
            passageRecyclerView2.adapter = passageAdapter
            passageAdapter.submitList(passageData)
            //listenAddPassage() -> 需优化成paging3监听
        }

        homeViewmodel.personalInfo.observe(viewLifecycleOwner) { result ->
            if(result.errorCode != -1001) {
                mainActivity.showInfo(result.data.userInfo.username, result.data.userInfo.coinCount)
            }
        }
    }
}