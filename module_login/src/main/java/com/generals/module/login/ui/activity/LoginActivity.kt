package com.generals.module.login.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.generals.lib.base.ui.BaseActivity
import com.generals.module.login.CustomDialog
import com.generals.module.login.R
import com.generals.module.login.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

@Route(path = "/login/activity")
class LoginActivity : BaseActivity() {
    lateinit var mEtAccount: EditText
    lateinit var mEtPassword: EditText
    lateinit var mTilAccount: TextInputLayout
    lateinit var mTilPassword: TextInputLayout
    lateinit var mBtnLogin: Button
    lateinit var mBtnSign: Button
    lateinit var mCbRemember: CheckBox
    lateinit var mIvClose: ImageView

    lateinit var signView: View
    lateinit var mEtSignUsername: EditText
    lateinit var mEtSignPassword: EditText
    lateinit var mEtSignRePassword: EditText

    val viewmodel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ARouter.getInstance().inject(this)

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

        viewmodel.livedataLogin.observe(this) {result ->
            if(result != null) {
                if(result.errorCode == -1) {
                    result.errorMsg.showDialog("登录失败!") {
                        clearInput()
                    }
                } else {
                    "登录成功".showToast()
                    //TODO : 登录成功逻辑 -> 传递数据
                    ARouter.getInstance().build("/main.activity").navigation()
                }
            }
        }

        viewmodel.livedataSign.observe(this) { result ->
            if(result != null) {
                if(result.errorCode == -1) {
                    result.errorMsg.showDialog("注册失败") {
                        mEtSignUsername.setText("")
                        mEtSignPassword.setText("")
                        mEtSignRePassword.setText("")
                    }
                } else {
                    "注册成功,请返回登录!".showDialog("") {
                        mEtAccount.setText(mEtSignUsername.text)
                        mEtPassword.setText("")
                        signView.visibility = View.GONE
                    }
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
        signView = LayoutInflater.from(this).inflate(R.layout.sign_layout, null)!!
        val dialog = CustomDialog()
            .newInstance()
            .setDialogHeight(1000)
            .setCustomView(signView)
        val ft: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //设置过渡动画
        dialog.show(ft, "DialogMore") //开启bottomSheetDialog

        mEtSignUsername = signView.findViewById(R.id.et_sign_account)
        mEtSignPassword = signView.findViewById(R.id.et_sign_password)
        mEtSignRePassword = signView.findViewById(R.id.et_sign_rePassword)
        val mTilUsername : TextInputLayout= signView.findViewById(R.id.til_sign_account)
        val mTilPassword : TextInputLayout = signView.findViewById(R.id.til_sign_password)
        val mTilRePassword : TextInputLayout = signView.findViewById(R.id.til_sign_rePassword)
        val mBtnSign : Button= signView.findViewById(R.id.btn_sign_sign)

        mIvClose.setOnClickListener {
            finish()
        }
        mEtSignUsername.addTextChangedListener {
            mTilUsername.isErrorEnabled = false
        }
        mEtSignPassword.addTextChangedListener {
            mTilPassword.isErrorEnabled = false
        }
        mEtSignRePassword.addTextChangedListener {
            mTilRePassword.isErrorEnabled = false
        }
        mBtnSign.setOnClickListener {
            val username = mEtSignUsername.text.toString()
            val password = mEtSignPassword.text.toString()
            val rePassword = mEtSignRePassword.text.toString()
            if (username == "") {
                mTilUsername.setError("用户名不能为空!")
            } else {
                if (password == "") {
                    mTilPassword.setError("密码不能为空!")
                } else {
                    if (rePassword == "") {
                        mTilRePassword.setError("请再次输入密码!")
                    } else {
                        if (password != rePassword) {
                            mTilRePassword.setError("两次密码输入不相同!")
                        } else {
                            viewmodel.sign(username, password, rePassword)
                        }
                    }
                }
            }
        }



    }

}