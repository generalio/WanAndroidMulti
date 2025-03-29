package com.generals.module.login

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * description ： 自定义BottomSheetDialog
 * date : 2025/3/28 21:23
 */
class CustomDialog : BottomSheetDialogFragment() {

    private var height: Int = 0
    private var customView: View? = null

    //工厂模式返回一个新的CustomDialog实例
    fun newInstance(): CustomDialog {
        return CustomDialog()
    }

    fun setCustomView(view: View): CustomDialog {
        this.customView = view
        return this
    }

    //设置初始高度
    fun setDialogHeight(height: Int) : CustomDialog {
        this.height = height
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.StyleBottomSheetDialogBg) //设置圆角样式
    }

    override fun onStart() {
        super.onStart()

        //拿到系统的bottom_sheet
        val bottomSheetDialog = (dialog as BottomSheetDialog)
        val view = bottomSheetDialog.delegate.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)!!
        val behavior = BottomSheetBehavior.from(view)

        //设置弹出高度
        behavior.peekHeight = height //设置第一次下划到达的高度
        behavior.state = BottomSheetBehavior.STATE_EXPANDED //默认为展开状态
        //behavior.peekHeight = resources.displayMetrics.heightPixels -> 不能手动下划
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT - 500 //设置dialog高度
        //behavior.isHideable = false 是否能被隐藏
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        customView?.let {
            dialog.setContentView(it)
            val mIvClose : ImageView = customView!!.findViewById(R.id.iv_sign_close)
            mIvClose.setOnClickListener {
                dismiss()
            }
        }
        return dialog
    }

}