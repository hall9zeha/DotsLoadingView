package com.barryzeha.dotsloadingview.components

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.barryzeha.dotsloadingview.R


/***
 * Project DotsLoadingView
 * Created by Barry Zea H. on 20/5/23.
 * DotsLoadingView is a simple open source library that displays a loading view with a custom drawable
 **/


class DotsLoadingComponent @JvmOverloads constructor(context:Context,
                                                     attrs:AttributeSet?=null,
                                                     defStyleAttr:Int =0) : FrameLayout(context,attrs,defStyleAttr){


    enum class TypeLoading(val value:Int){
        DOT_TYPE(0),SQUARE_TYPE(1),RESOURCE_TYPE(2)
    }
    private var dotSize:Int = convertDpToPixels(8f,context)
    private var margin:Int = convertDpToPixels(4f,context)
    private var numOfDots= 3
    private var animators = mutableListOf<Animator>()
    private var animationTime = 1000L
    private var minScale = 0.5f
    private var maxScale = 1f
    private lateinit var attrCustom:TypedArray
    private var colorDot = androidx.appcompat.R.attr.colorAccent
    private var startDotsAnimation = false
    private var primaryAnimator:ValueAnimator? = null
    private lateinit var dotProgress:LinearLayout
    private var dotType:Int=0
    private var dotResource = R.drawable.dot_shape
    private var dotAnimator:ValueAnimator ? = null

    init{
        attrs?.let{
            attrCustom = context.obtainStyledAttributes(it, R.styleable.DotsLoadingComponent,0,0)
            val colorDots = attrCustom.getString(R.styleable.DotsLoadingComponent_dotColor)

            dotType = attrCustom.getInt(R.styleable.DotsLoadingComponent_dotType,0)
            colorDot = if(!colorDots.isNullOrEmpty()) Color.parseColor(colorDots) else androidx.appcompat.R.attr.colorAccent
            dotResource = getTypeLoading(dotType)
            dotSize = convertDpToPixels(attrCustom.getFloat(R.styleable.DotsLoadingComponent_dotSize,8F),context)
            margin = convertDpToPixels(attrCustom.getFloat(R.styleable.DotsLoadingComponent_dotMargin,4f),context)
            numOfDots = attrCustom.getInt(R.styleable.DotsLoadingComponent_numberOfDots,3)
            animationTime = attrCustom.getInt(R.styleable.DotsLoadingComponent_animationDuration,1000).toLong()
            startDotsAnimation = attrCustom.getBoolean(R.styleable.DotsLoadingComponent_startAnimation,false)
            attrCustom.recycle()
        }

        init()

    }
    private fun init(){
        removeAllViews()
        clipChildren = false
        clipToPadding = false
        dotProgress = LinearLayout(context)
        val progressDotsLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        progressDotsLayoutParams.gravity = Gravity.CENTER
        dotProgress.layoutParams = progressDotsLayoutParams
        dotProgress.clipChildren = false
        dotProgress.clipToPadding = false
        addView(dotProgress)
        animators.clear()
        for(i in 0 until numOfDots){
            val dot= View(context)
            val layoutParams = LayoutParams(dotSize * 2 , dotSize * 2)
            layoutParams.setMargins(margin,margin,margin,margin)
            dot.layoutParams = layoutParams
            dot.scaleX = minScale
            dot.scaleY = minScale
            dot.setBackgroundResource(dotResource)
            dot.background.setTint(colorDot)

            dotProgress.addView(dot)
            val animator = getScaleAnimator(dot)
            animators.add(animator)
        }
        primaryAnimator?.cancel()
        primaryAnimator = ValueAnimator.ofInt(0,numOfDots)
        primaryAnimator?.addUpdateListener {
            if(it.animatedValue != numOfDots)
                animators[it.animatedValue as Int].start()
        }
        primaryAnimator?.repeatMode = ValueAnimator.RESTART
        primaryAnimator?.repeatCount = ValueAnimator.INFINITE
        primaryAnimator?.duration = animationTime
        primaryAnimator?.interpolator = LinearInterpolator()
        setStartAnimation(startDotsAnimation)
    }

    private fun getTypeLoading(type:Int):Int{
        return when (type){
            TypeLoading.SQUARE_TYPE.value -> R.drawable.square_shape
            TypeLoading.RESOURCE_TYPE.value -> attrCustom.getResourceId(R.styleable.DotsLoadingComponent_dotSrc,R.drawable.dot_shape)
            else -> R.drawable.dot_shape
        }
    }
    fun stopAnimation() {
        primaryAnimator?.cancel()
    }

    fun startAnimation() {
        primaryAnimator?.start()
    }

    override fun setVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) startAnimation()
        else stopAnimation()
        super.setVisibility(visibility)
    }
    private fun setStartAnimation(start:Boolean){
        if(start)primaryAnimator?.start()else primaryAnimator?.cancel()
    }

    /*****/

   class Builder {
        private var margin = 4f
        private var dotRadius = 8f
        private var numberOfDots = 3
        private var animationDuration = 1000L
        private var minScale = 0.5f
        private var maxScale = 1f
        private var primaryAnimator: ValueAnimator? = null
        private var colorDot = androidx.appcompat.R.attr.colorAccent
        private var dotResource = R.drawable.dot_shape
        private var typeLoading:Int=0


        fun build(context: Context): DotsLoadingComponent {

            val dotProgressBar = DotsLoadingComponent(context)
            dotProgressBar.maxScale = maxScale
            dotProgressBar.minScale = minScale
            dotProgressBar.numOfDots = numberOfDots
            dotProgressBar.animationTime= animationDuration
            dotProgressBar.margin = convertDpToPixels(margin,context)
            dotProgressBar.dotSize = convertDpToPixels(dotRadius,context)
            dotProgressBar.primaryAnimator = primaryAnimator
            dotProgressBar.dotResource = dotResource
            dotProgressBar.colorDot = colorDot
            dotProgressBar.init()
            return dotProgressBar
        }

        fun setMargin(margin: Float): Builder {
            this.margin = margin
            return this
        }

        fun setDotSize(dotRadius: Float): Builder {
            this.dotRadius = dotRadius
            return this
        }

        fun setNumberOfDots(numberOfDots: Int): Builder {
            this.numberOfDots = numberOfDots
            return this
        }

        fun setMaxScale(maxScale: Float): Builder {
            this.maxScale = maxScale
            return this
        }

        fun setMinScale(minScale: Float): Builder {
            this.minScale = minScale
            return this
        }
        /**
         * Requires an argument of type Long, expressed in milliseconds
        **/
        fun setAnimationDuration(duration: Long): Builder {
            this.animationDuration = duration
            return this
        }

        /**
        * This method changes the color to the view, this method requires a resource as a parameter
         * @param R.Color.CYAN
         * @param R.color.my_color_custom
         * example setDotColor(R.color.CYAN)
         *
        * **/
        fun setDotColor(color:Int):Builder{
            if(color.toString().isNotEmpty())this.colorDot = color
            return this
        }
        /**
         * This method changes the color to the view, this method requires a String resource as a parameter
         * @sample setDotColor("#000455")
         *
         * **/
        fun setDotColor(color:String):Builder{
            if(color.isNotEmpty())this.colorDot = Color.parseColor(color)
            return this
        }
        /**
         * Returns a different view according to the TypeLoading type argument:
         * @param typeLoading DotsLoadingComponent.TypeLoading
         * - DotsLoadingComponent.TypeLoading.DOT_TYPE: dot loading shape
         * - DotsLoadingComponent.TypeLoading.SQUARE_TYPE: square loading shape
         * - DotsLoadingComponent.TypeLoading.RESOURCE_TYPE: shows a drawable that we have in the project
         *   R.drawable.ic_custom
         *
         **/
        fun setTypeLoading(typeLoading:TypeLoading):Builder{
            this.dotResource = getTypeLoadingRes(typeLoading)
            return this
        }

        /**
         * This method changes the view of the progress loading for a drawable element that we pass to it as a parameter:
         *  - setResourceDrawable(R.drawable.ic_custom)
         *
         * If we are going to put a drawable we must put it as TypeLoading:
         *  - setTypeLoading(TypeLoading.TYPE_RESOURCE)
         */
        fun setResourceDrawable(res:Int):Builder{
            this.dotResource=res
            return this
        }
        private fun getTypeLoadingRes(type:TypeLoading):Int{
            return when (type){
                TypeLoading.SQUARE_TYPE -> R.drawable.square_shape
                TypeLoading.RESOURCE_TYPE-> this.dotResource
                else -> R.drawable.dot_shape

            }
        }
        /*****/
    }
    private fun getScaleAnimator(view:View):Animator{
        if(dotAnimator !=null)
            return dotAnimator as ValueAnimator
        val animator = ValueAnimator.ofFloat(minScale,maxScale)
        animator.addUpdateListener {
            view.scaleX = it.animatedValue as Float
            view.scaleY = it.animatedValue as Float
        }
        animator.duration = animationTime / numOfDots.toLong()
        animator.repeatCount = 1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.interpolator = LinearInterpolator()
        return animator
    }
    companion object{
        fun convertDpToPixels(dp:Float,context:Context):Int{
            return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
    }

}