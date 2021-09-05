package com.sample.socket.base.helpers

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.sample.socket.R

fun View.throttleClick(isAnimated: Boolean = false, debounceTime: UInt = 500u, action: () -> Unit) {

    fun animation() {
        val animX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.9f, 1.0f)
        val animY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.9f, 1.0f)
        val animator = AnimatorSet()
        animator.play(animX).with(animY)
        animator.start()
    }

    setOnClickListener {
        when {
            tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
            else -> {
                tag = System.currentTimeMillis() + debounceTime.toLong()
                if (isAnimated) animation()
                action()
            }
        }
    }
}

fun View.rippleClick(debounceTime: UInt = 300u, anim:Int =  R.anim.scale_down, action: () -> Unit) {
    fun animation() {
        startAnimation(AnimationUtils.loadAnimation(context,anim));
    }

    setOnClickListener {
        animation()
        Handler(Looper.getMainLooper()).postDelayed( {
            action()
        },debounceTime.toLong())
    }
}