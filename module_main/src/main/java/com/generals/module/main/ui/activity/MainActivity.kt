package com.generals.module.main.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.generals.lib.base.ui.BaseActivity
import com.generals.module.main.ui.fragment.NavigationFragment
import com.generals.module.main.R
import com.generals.module.main.ui.adapter.ViewPager2Adapter
import com.generals.module.main.ui.fragment.HomeFragment
import com.generals.module.main.ui.fragment.PublicFragment
import com.generals.module.main.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.ArrayList

@Route(path = "/main/activity")
class MainActivity : BaseActivity() {

    val mainViewModel : MainViewModel by viewModels()

    lateinit var toggle: ActionBarDrawerToggle
    private var isLogin = false

    lateinit var mNavBottom: BottomNavigationView
    lateinit var mBottomViewpager2: ViewPager2
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var floatButton: FloatingActionButton

    companion object {
        const val REQUEST_CODE_LOGIN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerlayout)
        toolbar = findViewById(R.id.toolbar)
        floatButton = findViewById(R.id.floatButton)

        setNavigationColumn()
        setBottomNavigation()

        mainViewModel.logoutLivedata.observe(this) {
            showInfo("未登录", -1)
        }

        // 注册 ActivityResultLauncher
        /*startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) { //如果接收到LoginActivity返回的数据
                    val data: Intent? = result.data
                    val getCoinCount = data?.getIntExtra("coinCount",-1)
                    val getUsername = data?.getStringExtra("username").toString()
                    getCoinCount?.let {
                        isLogin = true
                        val homeFragment = supportFragmentManager.findFragmentByTag("f0") as? HomeFragment
                        val publicFragment = supportFragmentManager.findFragmentByTag("f1") as? PublicFragment
                        homeFragment?.update()
                        publicFragment?.update()
                        showInfo(getUsername, getCoinCount)
                    }
                }
            }*/
        setButton()
    }

    fun showInfo(username: String, coinCount: Int) {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        val headerUsername: TextView = headerView.findViewById(R.id.tv_head_username)
        val headerButton: Button = headerView.findViewById(R.id.btn_head_login)
        val headerCollectCoins: TextView = headerView.findViewById(R.id.tv_head_collectCoins)
        headerUsername.setText(username)
        if (coinCount == -1) {
            headerCollectCoins.setText("积分:--")
        } else {
            headerCollectCoins.setText("积分:$coinCount")
        }
        //更改登录按钮名称
        if (headerButton.text.toString() == "点击登录") {
            headerButton.setText("退出登录")
        } else {
            if (headerButton.text.toString() == "退出登录") {
                headerButton.setText("点击登录")
                headerCollectCoins.setText("积分:--")
            }
        }
    }

    /*override fun showLogout() {
        isLogin = false
        val homeFragment = supportFragmentManager.findFragmentByTag("f0") as? HomeFragment
        val publicFragment = supportFragmentManager.findFragmentByTag("f1") as? PublicFragment
        homeFragment?.update()
        publicFragment?.update()
        Toast.makeText(this, "已退出登录!", Toast.LENGTH_SHORT).show()
    }*/

    //对toolbar+drawerlayout的基本设置
    private fun setNavigationColumn() {

        toolbar.title = "首页"
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        fun closeDrawer() {
            drawerLayout.closeDrawer(findViewById(R.id.nav_view))
        }

        //对抽屉懒里面的列表进行点击监听
        /*findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                }

            }
            closeDrawer()
            true
        }*/
        toggle = object :
            ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        toggle.syncState()
        drawerLayout.addDrawerListener(toggle)
    }

    // 处理toolbar上抽屉开关等图标的点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return when (item.itemId) {
            R.id.search -> {
                /*val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)*/
                // TODO : 搜索界面跳转以及实现
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //加载toolbar的搜索按钮
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return true
    }

    //前往登录/登出
    private fun setButton() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        val mBtnLogin: Button = headerView.findViewById(R.id.btn_head_login)
        mBtnLogin.setOnClickListener {
            if (mBtnLogin.text.toString() == "点击登录") {
                ARouter.getInstance().build("/login/activity").navigation(this, REQUEST_CODE_LOGIN)
            }
            if (mBtnLogin.text.toString() == "退出登录") {
                "是否退出登录".showCancelDialog("") {
                    mainViewModel.logout()
                }
            }
        }
    }

    //设置底部导航按钮
    private fun setBottomNavigation() {

        //绑定视图
        mNavBottom = findViewById(R.id.homepage_navigation_bottom)
        mBottomViewpager2 = findViewById(R.id.bottom_navigation_viewpage2)

        mNavBottom.itemIconTintList = null
        //将fragment添加进适配器
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(HomeFragment())
        fragmentList.add(PublicFragment())
        fragmentList.add(NavigationFragment())
        mBottomViewpager2.adapter = ViewPager2Adapter(this, fragmentList)

        mBottomViewpager2.isUserInputEnabled = false

        floatButton.setOnClickListener {
            val nowFragment = fragmentList[mBottomViewpager2.currentItem]
            when (nowFragment) {
                is HomeFragment -> {
                    nowFragment.scrollToTop()
                }
                is PublicFragment -> {
                    nowFragment.scrollToTop()
                }
                is NavigationFragment -> {
                    nowFragment.scrollToTop()
                }
            }
        }

        //fragment切换时底部导航跟着切换
        mBottomViewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mNavBottom.menu.getItem(position).isChecked = true
            }
        })

        //底部导航切换时fragment跟着切换
        mNavBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    toolbar.title = "首页"
                    mBottomViewpager2.currentItem = 0
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_public -> {
                    toolbar.title = "公众号"
                    mBottomViewpager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_navigation -> {
                    toolbar.title = "导航"
                    mBottomViewpager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_LOGIN) {
            val username = data?.getStringExtra("username")
            val coinCount = data?.getIntExtra("coinCount", -1)
            if(username != null && coinCount != null) {
                showInfo(username, coinCount)
            }
        }
    }

}

