package com.riluq.testtwiscode.ui.base

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.riluq.testtwiscode.R
import com.riluq.testtwiscode.utils.CommonUtils
import com.riluq.testtwiscode.utils.NetworkUtils
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.dialog_no_internet.view.*
import timber.log.Timber

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel> : AppCompatActivity(),
    BaseFragment.Callback {

    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    private var mProgressDialog: ProgressDialog? = null

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V?

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)

        performDataBinding()

        backPressed()

//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Timber.w(task.exception, "getInstanceId failed")
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//                getViewModel()?.setDeviceToken(token)
//            })
    }

    private fun backPressed() {
        getViewModel()?.hasBackPressed?.observe(this, {
            if (it) {
                onBackPressed()
                getViewModel()?.backPressedComplete()
            }
        })
    }

    fun checkInternetConnection(doSomething: () -> Unit, noInternetConnection: () -> Unit) {
        if (NetworkUtils.isInternetAvailable(this)) {
            doSomething()
        } else {
            noInternetConnection()
            showDialogNoInternetConnection(doSomething, noInternetConnection)
        }
    }

    private fun showDialogNoInternetConnection(doSomething: () -> Unit, noInternetConnection: () -> Unit) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_no_internet, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()

        mDialogView.btn_dialog_back.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mAlertDialog.setOnDismissListener {
            checkInternetConnection(doSomething, noInternetConnection)
        }

    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    fun showSnackBar(message: String) {
        hideKeyboard()
        val snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 2
        snackbar.show()
    }

    fun showSnackBarError(message: String) {
        hideKeyboard()
        val snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 2
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this , R.color.errorColor))
        snackbar.show()
    }

    fun hideKeyboard() {
        val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getView().windowToken, 0)
    }

    private fun getView(): View {
        return findViewById(android.R.id.content)
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        mViewDataBinding =  DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    open fun getViewDataBinding(): T {
        return mViewDataBinding!!
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String?) {

    }

}