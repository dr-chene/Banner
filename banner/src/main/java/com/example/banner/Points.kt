package com.example.banner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min

/**
 * Created by chene on @date 20-11-13 下午1:58
 */
class Points @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : View(context, attrs, defStyleAttr){

    private val pointNum: Int
    private var pointRadius: Float = 0f
    private val paintWhite: Paint
    private val paintGrey: Paint
    private var curIndex: Int = 0

    init {
        val array = context.obtainStyledAttributes(R.styleable.Points)
        pointNum = array.getInt(R.styleable.Points_point_sum, 3)
        array.recycle()
        paintWhite = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        paintGrey = Paint().apply {
            color = Color.parseColor("#99FFFFFF")
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWith = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = MeasureSpec.getSize(heightMeasureSpec)
        pointRadius = mWith / (2f * pointNum - 1)
        pointRadius = min(pointRadius, mHeight.toFloat()) / 2
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.d("TAG_01", "onDraw: $pointRadius ")

        var num = pointNum
        canvas?.apply {
            var lOffset = 0f
            while (num-- > 0){
                drawCircle(lOffset + pointRadius, pointRadius, pointRadius, paintGrey)
                lOffset += pointRadius * 3.6f
            }
        }
        drawByIndex(curIndex, canvas, paintWhite)
    }

    fun setCurrentPosition(position: Int){
        curIndex = position
        invalidate()
    }

    private fun drawByIndex(index: Int, canvas: Canvas?, paint: Paint){
        var pos = index
        var lOffset = 0f
        while (pos-- > 0){
            lOffset += pointRadius * 3.6f
        }
        canvas?.drawCircle(lOffset + pointRadius, pointRadius, pointRadius, paint)
    }
}
