package com.yuriysurzhikov.lab4.ui.list

interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int)
}