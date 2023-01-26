package com.sample.myapplication.presentation.base

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<BaseAdapterUiModel>?) {
    val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, BaseAdapterUiModel>?
    adapter?.submitList(list)
}