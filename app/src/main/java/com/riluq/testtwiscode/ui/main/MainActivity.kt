package com.riluq.testtwiscode.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.riluq.testtwiscode.BR
import com.riluq.testtwiscode.R
import com.riluq.testtwiscode.ViewModelFactory
import com.riluq.testtwiscode.data.remote.response.SearchResponse
import com.riluq.testtwiscode.databinding.ActivityMainBinding
import com.riluq.testtwiscode.ui.base.BaseActivity
import com.riluq.testtwiscode.ui.cart.CartActivity
import com.riluq.testtwiscode.ui.cart.ListProducts
import com.riluq.testtwiscode.utils.AppLogger
import com.riluq.testtwiscode.utils.gone
import com.riluq.testtwiscode.utils.visible
import com.riluq.testtwiscode.vo.Status
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SearchAdapter

    private val REQUEST_CART = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()

        setup()
    }

    private fun setup() {
        adapter = SearchAdapter(
            SearchAdapter.OnClickListener {
                getViewModel().setTotalProductsSelected(adapter.getProductsSelected().size)
            }
        )

        initRecyclerView()
        initSwipeRefresh()

        search()

        getViewModel().totalProductsSelected.observe(this, {
            if (it == 0) {
                binding.mcvCounter.gone()
            } else {
                binding.mcvCounter.visible()
                binding.tvCounter.text = it.toString()
            }
        })

        binding.imgCart.setOnClickListener {
            if (getViewModel().totalProductsSelected.value!! > 0)
                startActivityForResult(CartActivity.getIntent(this, ListProducts(adapter.getProductsSelected())), REQUEST_CART)
            else
                showSnackBarError("Tambahkan product yang mau dibeli")
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = gridLayoutManager
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        binding.swipeRefresh.setOnRefreshListener {
            search()
        }
    }

    private fun search() {
        checkInternetConnection(
            doSomething = {
                showLoading()
                getViewModel().search().observe(this@MainActivity, {
                    when (it.status) {
                        Status.DONE -> {
                            hideLoading()
                            binding.tvError.gone()
                            binding.rvSearch.visible()
                            val dataResponse = it.data
                            adapter.submitList(dataResponse)
                            adapter.notifyDataSetChanged()
                            binding.swipeRefresh.isRefreshing = false

                            getViewModel().setTotalProductsSelected(0)
                        }
                        Status.ERROR -> {
                            hideLoading()
                            binding.tvError.text = it.errorResponse?.error ?: "Error"
                            binding.tvError.setTextColor(resources.getColor(R.color.errorColor))
                        }
                    }
                })
            },
            noInternetConnection = {
                binding.tvError.text = resources.getString(R.string.no_internet_swipe_down)
                binding.tvError.setTextColor(resources.getColor(R.color.errorColor))
                binding.tvError.visible()
                binding.rvSearch.gone()
                binding.swipeRefresh.isRefreshing = false
                hideLoading()
            }
        )
    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(R.string.title_dialog_exit_app)
            message(R.string.message_dialog_exit_app)
            positiveButton(R.string.yes) {
                finishAffinity()
            }
            negativeButton(R.string.no) {
                it.cancel()
            }
        }
    }

    private fun viewModel(): Lazy<MainViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel = viewModel().value

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}