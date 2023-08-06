package com.demo.sport.main.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.demo.sport.databinding.ViewSportBinding
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.presentation.viewmodel.SportViewModel
import javax.inject.Inject

class FeaturedSportView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    private var binding: ViewSportBinding

    @Inject
    lateinit var viewModel: SportViewModel

    init {
        binding = ViewSportBinding.inflate(LayoutInflater.from(context), this, true)
        orientation = VERTICAL
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun observeOnViewModel() {
        viewModel.result.observeForever(Observer {
            it?.let {

            }
        })

        viewModel.showLoader.observeForever(Observer {
            it?.let {
                showLoader()
            }
        })

        viewModel.data.observeForever(Observer {
            it?.let {
                hideLoader()
                showSport(it)
            }
        })
    }


    private fun showSport(sport: Sport) {
        binding.tvSportName.text = sport.name
        binding.tvSportDescription.text = sport.description
    }

    fun initViewModel(viewModel: SportViewModel) {
        this.viewModel = viewModel
        makeApiCall()
        binding.btnRefresh.setOnClickListener {
            makeApiCall()
        }
        observeOnViewModel()
    }

    private fun makeApiCall(){
        showLoader()
        viewModel.getSports()
    }

    private fun showLoader(){
        binding.loaderView.show()
        binding.btnRefresh.hide()
    }

    private fun hideLoader(){
        binding.loaderView.hide()
        binding.btnRefresh.show()
    }
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
