package com.generals.module.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentTransaction
import com.generals.lib.base.ui.BaseActivity
import com.generals.module.login.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : BaseActivity() {
    lateinit var mEtAccount: EditText
    lateinit var mEtPassword: EditText
    lateinit var mTilAccount: TextInputLayout
    lateinit var mTilPassword: TextInputLayout
    lateinit var mBtnLogin: Button
    lateinit var mBtnSign: Button
    lateinit var mCbRemember: CheckBox
    lateinit var mIvClose: ImageView

    val viewmodel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initInfo()
        initEvent()
    }

    //初始化控件
    private fun initView() {
        mEtAccount = findViewById(R.id.et_login_account)
        mEtPassword = findViewById(R.id.et_login_password)
        mTilAccount = findViewById(R.id.til_login_account)
        mTilPassword = findViewById(R.id.til_login_password)
        mBtnLogin = findViewById(R.id.btn_login_login)
        mBtnSign = findViewById(R.id.btn_login_sign)
        mCbRemember = findViewById(R.id.cb_login_remember)
        mIvClose = findViewById(R.id.iv_login_close)
    }

    //检测是否点击登录/注册,对输入框的监听以及是否记住密码
    private fun initEvent() {
        initInfo()
        mEtAccount.addTextChangedListener {
            if (mEtAccount.text.toString() != "") {
                mTilAccount.setErrorEnabled(false)
            }
        }
        mEtPassword.addTextChangedListener {
            if (mEtPassword.text.toString() != "") {
                mTilPassword.setErrorEnabled(false)
            }
        }
        mBtnLogin.setOnClickListener {
            if (mEtAccount.text.toString() == "") {
                mTilAccount.setError("账户名不能为空")
            } else {
                if (mEtPassword.text.toString() == "") {
                    mTilPassword.setError("密码不能为空")
                } else {
                    val username = mEtAccount.text.toString()
                    val password = mEtPassword.text.toString()
                    if (mCbRemember.isChecked) {
                        viewmodel.passwordRemember(username, password)
                    }
                    viewmodel.login(username, password)
                }
            }
        }
        mBtnSign.setOnClickListener {
            sign()
        }
        mIvClose.setOnClickListener {
            finish()
        }

        // TODO : LoginLiveData && SignLiveData

        viewmodel.livedataLogin.observe(this) {result ->
            if(result != null) {
                if(result.errorCode == -1) {
                    /*result.errorMsg.showDialog("登录失败!") {
                        clearInput()
                    }*/
                    result.errorMsg.showToast()
                } else {
                    "登录成功".showToast()
                }
            }
        }

    }

    //初始化账号密码
    private fun initInfo() {
        val (username, password) = viewmodel.initPassword()
        mEtAccount.setText(username)
        mEtPassword.setText(password)
        if(username.isNotEmpty() && password.isNotEmpty()) {
            mCbRemember.isChecked = true
        }
    }

    //清除输入框的数据
    private fun clearInput() {
        mEtAccount.setText("")
        mEtPassword.setText("")
    }

    private fun sign() {
        val signView = LayoutInflater.from(this).inflate(R.layout.sign_layout, null)!!
        val dialog = CustomDialog()
            .newInstance()
            .setDialogHeight(1000)
            .setCustomView(signView)
        val ft: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //设置过渡动画
        dialog.show(ft, "DialogMore") //开启bottomSheetDialog
    }

}