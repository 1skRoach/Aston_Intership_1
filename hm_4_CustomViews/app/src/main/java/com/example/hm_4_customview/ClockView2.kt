package com.example.hm_4_customview

import android.animation.PropertyValuesHolder
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

class ClockView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
        private val mPaintOutCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintOutText = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintProgressBg = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintProgress = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintTriangle = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintHour = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintMinute = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaintBall = Paint(Paint.ANTI_ALIAS_FLAG)
        private val color_halfWhite: Int = Color.argb(255 - 180, 255, 255, 255)
        private val color_white: Int = Color.parseColor("#FFFFFF")
        private var mWidth: Int = 0
        private var mHeight: Int = 0
        private var mCenterX: Int = 0
        private var mCenterY: Int = 0
        private val paddingOut: Float = dp2px(25f)
        private val innerRadius: Float = dp2px(6f)
        private var mHourDegress: Int = 0
        private var mMinuteDegress: Int = 0
        private var mSecondMillsDegress: Float = 0f
        private var mSecondDegress: Int = 0

        private var mRadius: Float = 0.toFloat()
        private var mShakeAnim: ValueAnimator? = null
        private val mCameraMatrix: Matrix by lazy { Matrix() }
        private val mCamera: Camera by lazy { Camera() }
        private var mCameraRotateX: Float = 0f
        private var mCameraRotateY: Float = 0f
        private val mMaxCameraRotate = 10f
        private var mCanvasTranslateX: Float = 0f
        private var mCanvasTranslateY: Float = 0f
        private var mMaxCanvasTranslate: Float = 0f
        private lateinit var mCanvas: Canvas

        init {
            mPaintOutCircle.color = Color.GRAY
            mPaintOutCircle.strokeWidth = dp2px(1f)
            mPaintOutCircle.style = Paint.Style.STROKE

            mPaintOutText.color = Color.GRAY
            mPaintOutText.strokeWidth = dp2px(1f)
            mPaintOutText.style = Paint.Style.STROKE
            mPaintOutText.textSize = sp2px(11f)
            mPaintOutText.textAlign = Paint.Align.CENTER

            mPaintProgressBg.color = Color.GRAY
            mPaintProgressBg.strokeWidth = dp2px(2f)
            mPaintProgressBg.style = Paint.Style.STROKE

            mPaintProgress.color = Color.GRAY
            mPaintProgress.strokeWidth = dp2px(2f)
            mPaintProgress.style = Paint.Style.STROKE

            mPaintTriangle.color = Color.WHITE
            mPaintTriangle.style = Paint.Style.FILL

            mPaintHour.color = Color.GRAY
            mPaintHour.style = Paint.Style.FILL

            mPaintMinute.color = Color.WHITE
            mPaintMinute.strokeWidth = dp2px(3f)
            mPaintMinute.style = Paint.Style.STROKE
            mPaintMinute.strokeCap = Paint.Cap.ROUND

            mPaintBall.color = Color.parseColor("#4169E1")
            mPaintBall.style = Paint.Style.FILL

        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = MeasureSpec.getSize(heightMeasureSpec)
            val imageSize = if (width < height) width else height
            setMeasuredDimension(imageSize, imageSize)

        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            mWidth = w
            mHeight = h
            mCenterX = mWidth / 2
            mCenterY = mHeight / 2
            mRadius = (Math.min(w - paddingLeft - paddingRight,
                h - paddingTop - paddingBottom) / 2).toFloat()

            mMaxCanvasTranslate = 0.02f * mRadius
        }

        override fun onDraw(canvas: Canvas) {
            mCanvas = canvas
            canvas.translate(mCenterX.toFloat(), mCenterY.toFloat())
            setCameraRotate()
            drawArcCircle(canvas)
            drawOutText(canvas)
            drawCalibrationLine(canvas)
            drawSecond(canvas)
            drawMinute(canvas)
            drawHour(canvas)
            drawBall(canvas)
        }

        private fun drawBall(canvas: Canvas) {
            canvas.drawCircle(0f, 0f, innerRadius / 2, mPaintBall)
        }

        private fun drawMinute(canvas: Canvas) {
            canvas.save()
            canvas.rotate(mMinuteDegress.toFloat())
            canvas.drawLine(0f, 0f, 0f, -(width / 3).toFloat(), mPaintMinute)
            canvas.restore()
        }

        private fun drawHour(canvas: Canvas) {
            canvas.save()
            canvas.rotate(mHourDegress.toFloat())
            canvas.drawCircle(0f, 0f, innerRadius, mPaintTriangle)
            val path = Path()
            path.moveTo(-innerRadius / 2, 0f)
            path.lineTo(innerRadius / 2, 0f)
            path.lineTo(innerRadius / 6, -(width / 4).toFloat())
            path.lineTo(-innerRadius / 6, -(width / 4).toFloat())
            path.close()
            canvas.drawPath(path, mPaintHour)
            canvas.restore()
        }

        private fun drawSecond(canvas: Canvas) {

            canvas.save()
            canvas.rotate(mSecondMillsDegress)
            val path = Path()
            path.moveTo(0f, -width * 3f / 8 + dp2px(5f))
            path.lineTo(dp2px(8f), -width * 3f / 8 + dp2px(20f))
            path.lineTo(-dp2px(8f), -width * 3f / 8 + dp2px(20f))
            path.close()
            canvas.drawPath(path, mPaintTriangle)
            canvas.restore()

            val min = Math.min(width, mHeight) / 2
            for (i in 0..90 step 2) {
                canvas.save()
                mPaintProgress.setARGB((255 - 2.7 * i).toInt(), 255, 255, 255)

                canvas.rotate(((mSecondDegress - 90 - i).toFloat()))
                canvas.drawLine(min.toFloat() * 3 / 4, 0f, min * 3 / 4 + dp2px(10f), 0f, mPaintProgress)
                canvas.restore()
            }
        }

        private fun drawCalibrationLine(canvas: Canvas) {
            val min = Math.min(width, mHeight) / 2
            for (i in 0 until 360 step 2) {
                canvas.save()
                canvas.rotate(i.toFloat())
                canvas.drawLine(min.toFloat() * 3 / 4, 0f, min * 3 / 4 + dp2px(10f), 0f, mPaintProgressBg)
                canvas.restore()
            }
        }

        private fun drawOutText(canvas: Canvas) {
            val min = Math.min(width, mHeight)
            val textRadius = (min - paddingOut) / 2
            val fm = mPaintOutText.fontMetrics
            val mTxtHeight = Math.ceil((fm.leading - fm.ascent).toDouble()).toInt()
            canvas.drawText("3", textRadius, (mTxtHeight / 2).toFloat(), mPaintOutText)
            canvas.drawText("9", -textRadius, (mTxtHeight / 2).toFloat(), mPaintOutText)
            canvas.drawText("6", 0f, textRadius + mTxtHeight / 2, mPaintOutText)
            canvas.drawText("12", 0f, -textRadius + mTxtHeight / 2, mPaintOutText)
        }

        private fun drawArcCircle(canvas: Canvas) {
            val min = Math.min(width, mHeight)
            val rect = RectF(-(min - paddingOut) / 2, -(min - paddingOut) / 2, (min - paddingOut) / 2, (min - paddingOut) / 2)
            canvas.drawArc(rect, 5f, 80f, false, mPaintOutCircle)
            canvas.drawArc(rect, 95f, 80f, false, mPaintOutCircle)
            canvas.drawArc(rect, 185f, 80f, false, mPaintOutCircle)
            canvas.drawArc(rect, 275f, 80f, false, mPaintOutCircle)

        }

        private fun startTick() {
            postDelayed(mRunnable, 150)
        }

        private val mRunnable = Runnable {
            calculateDegree()
            invalidate()
            startTick()
        }

    private fun calculateDegree() {
        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = System.currentTimeMillis()
        val minute = mCalendar.get(Calendar.MINUTE)
        val secondMills = mCalendar.get(Calendar.MILLISECOND)
        val second = mCalendar.get(Calendar.SECOND)
        val hour = mCalendar.get(Calendar.HOUR)
        mHourDegress = hour * 30
        mMinuteDegress = minute * 6
        mSecondMillsDegress = second * 6 + secondMills * 0.006f
        mSecondDegress = second * 6
        //Because there is no 2°rotation of a scale, the calculation here is based on the millisecond value
        when ((secondMills * 0.006f).toInt()) {
            in 2 until 4 -> {
                mSecondDegress +=2
            }
            in 4 until 6 -> {
                mSecondDegress += 4
            }
        }
    }


    override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            startTick()
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            removeCallbacks(mRunnable)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mShakeAnim?.let {
                        if (it.isRunning) {
                            it.cancel()
                        }
                    }
                    getCameraRotate(event)
                    getCanvasTranslate(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    getCameraRotate(event)
                    getCanvasTranslate(event)
                }
                MotionEvent.ACTION_UP ->
                    startShakeAnim()
            }
            return true
        }

        private fun setCameraRotate() {
            mCameraMatrix.reset()
            mCamera.save()
            mCamera.rotateX(mCameraRotateX)
            mCamera.rotateY(mCameraRotateY)
            mCamera.getMatrix(mCameraMatrix)
            mCamera.restore()
            mCanvas.concat(mCameraMatrix)
        }

        private fun startShakeAnim() {
            val cameraRotateXName = "cameraRotateX"
            val cameraRotateYName = "cameraRotateY"
            val canvasTranslateXName = "canvasTranslateX"
            val canvasTranslateYName = "canvasTranslateY"
            val cameraRotateXHolder = PropertyValuesHolder.ofFloat(cameraRotateXName, mCameraRotateX, 0f)
            val cameraRotateYHolder = PropertyValuesHolder.ofFloat(cameraRotateYName, mCameraRotateY, 0f)
            val canvasTranslateXHolder = PropertyValuesHolder.ofFloat(canvasTranslateXName, mCanvasTranslateX, 0f)
            val canvasTranslateYHolder = PropertyValuesHolder.ofFloat(canvasTranslateYName, mCanvasTranslateY, 0f)
            mShakeAnim = ValueAnimator.ofPropertyValuesHolder(cameraRotateXHolder,
                cameraRotateYHolder, canvasTranslateXHolder, canvasTranslateYHolder)
            mShakeAnim?.interpolator = TimeInterpolator { input ->
                //http://inloop.github.io/interpolator/
                val f = 0.571429f
                (Math.pow(2.0, (-2 * input).toDouble()) * Math.sin((input - f / 4) * (2 * Math.PI) / f) + 1).toFloat()
            }
            mShakeAnim?.duration = 1000
            mShakeAnim?.addUpdateListener { animation ->
                mCameraRotateX = animation.getAnimatedValue(cameraRotateXName) as Float
                mCameraRotateY = animation.getAnimatedValue(cameraRotateYName) as Float
                mCanvasTranslateX = animation.getAnimatedValue(canvasTranslateXName) as Float
                mCanvasTranslateY = animation.getAnimatedValue(canvasTranslateYName) as Float
            }
            mShakeAnim?.start()
        }

        /**
         * camera
         *
         * @param event motionEvent
         */
        private fun getCameraRotate(event: MotionEvent) {
            val rotateX = -(event.y - height / 2)
            val rotateY = event.x - width / 2
            val percentArr = getPercent(rotateX, rotateY)
            mCameraRotateX = percentArr[0] * mMaxCameraRotate
            mCameraRotateY = percentArr[1] * mMaxCameraRotate
        }

        private fun getCanvasTranslate(event: MotionEvent) {
            val translateX = event.x - width / 2
            val translateY = event.y - height / 2
            val percentArr = getPercent(translateX, translateY)
            mCanvasTranslateX = percentArr[0] * mMaxCanvasTranslate
            mCanvasTranslateY = percentArr[1] * mMaxCanvasTranslate
        }

        private fun getPercent(x: Float, y: Float): FloatArray {
            val percentArr = FloatArray(2)
            var percentX = x / mRadius
            var percentY = y / mRadius
            if (percentX > 1) {
                percentX = 1f
            } else if (percentX < -1) {
                percentX = -1f
            }
            if (percentY > 1) {
                percentY = 1f
            } else if (percentY < -1) {
                percentY = -1f
            }
            percentArr[0] = percentX
            percentArr[1] = percentY
            return percentArr
        }
    }

    fun View.dp2px(dipValue: Float): Float {
        return (dipValue * this.resources.displayMetrics.density + 0.5f)
    }

    fun View.sp2px(spValue: Float): Float {
        return (spValue * this.resources.displayMetrics.scaledDensity + 0.5f)
    }




