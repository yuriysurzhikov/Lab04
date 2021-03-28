package com.yuriysurzhikov.lab4.ui.list

interface IDataHolder<T> {
    fun setOnClickListener(listener: OnItemClickListener<T>?)
    fun bind(item: T)
    fun getItem(): T?
}