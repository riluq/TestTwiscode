package com.riluq.testtwiscode.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.riluq.testtwiscode.BR
import com.riluq.testtwiscode.R
import com.riluq.testtwiscode.ViewModelFactory
import com.riluq.testtwiscode.databinding.ActivityCartBinding
import com.riluq.testtwiscode.ui.base.BaseActivity
import com.riluq.testtwiscode.ui.main.SearchAdapter
import com.riluq.testtwiscode.utils.bindTextMoney
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CartActivity : BaseActivity<ActivityCartBinding, CartViewModel>(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityCartBinding

    private var products: ListProducts? = null

    private lateinit var adapter: CartAdapter

    companion object {
        const val EXTRA_PRODUCTS = "extra_products"

        fun getIntent(context: Context, products: ListProducts): Intent {
            val intent = Intent(context, CartActivity::class.java)
            intent.putExtra(EXTRA_PRODUCTS, products)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()

        setup()
    }

    private fun setup() {
        products = intent.getParcelableExtra(EXTRA_PRODUCTS)

        adapter = CartAdapter(
            CartAdapter.OnClickListener {
                binding.tvTotalPrice.bindTextMoney(adapter.getTotal().toString())
            }
        )

        initRecyclerView()

        adapter.submitList(products?.product)
        adapter.notifyDataSetChanged()
        binding.tvTotalPrice.bindTextMoney(adapter.getTotal().toString())
    }

    private fun initRecyclerView() {
        binding.rvSearch.adapter = adapter
    }

    private fun viewModel(): Lazy<CartViewModel> = viewModels { factory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_cart

    override fun getViewModel(): CartViewModel = viewModel().value

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}