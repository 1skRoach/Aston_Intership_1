package com.example.hm_4_customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    /** height, width of the clock's view  */
    private var mHeight = 0
    private var mWidth: Int = 0

    /** numeric numbers to denote the hours  */
    private val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    /** spacing and padding of the clock-hands around the clock round  */
    private var mPadding = 0
    private val mNumeralSpacing = 0

    /** truncation of the heights of the clock-hands,
     * hour clock-hand will be smaller comparatively to others  */
    private var mHandTruncation = 0

    /** truncation of the heights of the clock-hands  */
    private var mHourHandTruncation: Int = 0

    /** others attributes to calculate the locations of hour-points  */
    private var mRadius = 0
    private var mPaint: Paint? = null
    private val mRect: Rect = Rect()
    private var isInit // it will be true once the clock will be initialized.
            = false
    /** coordinates */
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val halfWeight = w / 2f
        val halfHeight = h / 2f

        centerX = halfWeight + paddingLeft - paddingRight
        centerY = halfHeight + paddingTop - paddingBottom
        radius = halfWeight - paddingLeft - paddingRight
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /** initialize necessary values  */
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50 // spacing from the circle border
            val minAttr = min(mHeight, mWidth)
            mRadius = minAttr / 2 - mPadding

            // for maintaining different heights among the clock-hands
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17
            isInit = true // set true once initialized
        }

        /** draw the canvas-color  */
        canvas!!.drawColor(Color.WHITE)

        /** circle border */
        mPaint?.reset()
        mPaint?.color = Color.BLACK
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = 10f
        mPaint?.isAntiAlias = true
        mPaint?.let { paint ->
            canvas.drawCircle(
                (mWidth / 2).toFloat(),
                (mHeight / 2).toFloat(), (mRadius + mPadding - 10).toFloat(), paint
            )
        }

        /** clock-center */
        mPaint?.style = Paint.Style.FILL
        mPaint?.let {
            canvas.drawCircle(
                (mWidth / 2).toFloat(),
                (mHeight / 2).toFloat(),
                15f,
                it
            )
        }  // the 03 clock hands will be rotated from this center point.

        /** border of hours  */
        val fontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
                .toInt()
        mPaint!!.textSize = fontSize.toFloat() // set font size (optional)

        for (hour in mClockHours) {
            val tmp = hour.toString()
            mPaint!!.getTextBounds(tmp, 0, tmp.length, mRect) // for circle-wise bounding

            // find the circle-wise (x, y) position as mathematical rule
            val angle = Math.PI / 6 * (hour - 3)
            val x = (mWidth / 2 + Math.cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = (mHeight / 2 + Math.sin(angle) * mRadius + mRect.height() / 2).toInt()
            canvas.drawText(
                hour.toString(),
                x.toFloat(),
                y.toFloat(),
                mPaint!!
            )
        }

        /** draw clock hands to represent the every single time  */
        val calendar: Calendar = Calendar.getInstance()
        var hour: Float = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
        hour = if (hour > 12) hour - 12 else hour

        drawHandLine(
            canvas,
            ((hour + calendar.get(Calendar.MINUTE) / 60) * 5f).toDouble(),
            isHour = true,
            isMinute = false,
            isSecond = false
        ) // draw hours

        drawHandLine(
            canvas, calendar.get(Calendar.MINUTE).toDouble(),
            isHour = false,
            isMinute = true,
            isSecond = false
        ) // draw minutes

        drawHandLine(
            canvas, calendar.get(Calendar.SECOND).toDouble(),
            isHour = false,
            isMinute = false,
            isSecond = true
        ) // draw seconds

        /** invalidate the appearance for next representation of time   */
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHandLine(canvas: Canvas, moment: Double, isHour: Boolean, isMinute: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val difference = 25
        val handRadius =
            if (isHour){
                mRadius - mHandTruncation - mHourHandTruncation - 70
            } else if(isMinute) {
                mRadius - mHandTruncation - difference
            } else {
                mRadius - mHandTruncation
            }
        if (isSecond) mPaint!!.color = Color.YELLOW
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }
}








