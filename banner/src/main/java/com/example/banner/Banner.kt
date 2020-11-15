package com.example.banner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.banner.bean.DataEntry
import com.example.banner.databinding.BannerBinding
import kotlinx.android.synthetic.main.banner.view.*
import kotlinx.coroutines.*

/**
Created by chene on @date 20-11-10 下午8:19
 **/
class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: BannerBinding = BannerBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    private var loopDuration: Long
    private var autoLoop: Boolean = DEFAULT_AUTO_LOOP
    private var dataEntries: List<DataEntry>? = null


    init {
        binding.bannerRv.let {
            it.layoutManager = LinearLayoutManager(it.context, LinearLayoutManager.HORIZONTAL, false)
            PagerSnapHelper().attachToRecyclerView(it)
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        dataEntries?.let { set ->
                            val position = (it.getChildAt(0).layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
                            Log.d("TAG_04", "position_point: $position")
                            banner_point.setCurrentPosition(position % set.size)
                        }
                    }
                }
            })
        }

        val array = context.obtainStyledAttributes(R.styleable.Banner)
        loopDuration = array.getInt(R.styleable.Banner_loop_duration, DEFAULT_LOOP_DURATION).toLong()
        autoLoop = array.getBoolean(R.styleable.Banner_auto_loop, DEFAULT_AUTO_LOOP)
        array.recycle()

        CoroutineScope(Dispatchers.Main).launch {
            while (true){
                delay(loopDuration)
                val position = (banner_rv.getChildAt(0).layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
                Log.d("TAG_04", "position_index: $position")
                banner_rv.smoothScrollToPosition(position + 1)
            }
        }

    }

    private fun setAdapter(dataEntries: List<DataEntry>, clickListener: BannerAdapter.BannerClickListener){
        val adapter = BannerAdapter(dataEntries, clickListener)
        banner_rv.adapter = adapter
    }

    fun setDataAndClickListener(dataEntries: List<DataEntry>, clickListener: BannerAdapter.BannerClickListener){
        this.dataEntries = dataEntries
        setAdapter(dataEntries, clickListener)
    }


    companion object {
        const val DEFAULT_LOOP_DURATION = 2000
        const val DEFAULT_AUTO_LOOP = true
    }

}