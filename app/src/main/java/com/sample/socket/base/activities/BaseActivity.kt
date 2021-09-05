package com.sample.socket.base.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

/**
 * Type Alias class for accepting a lambda with [LayoutInflater] respectively for inflating
 * the required [ViewDataBinding] object into the [BaseActivity].
 */
typealias InflateActivityLayout<T> = (LayoutInflater) -> T

/**
 * Base class for [AppCompatActivity]s that use [ViewDataBinding].
 *
 * The class expects a generic [ViewDataBinding] type along with [bindInflater] (a type alias for
 * accepting a lambda satisfying [InflateActivityLayout] arguments) in order to process
 * and setup the view binding required for the child.
 *
 * An open extension function [setupViews] is exposed to its children, where the [View]s
 * bound the the provided [ViewDataBinding] class are provided for access without the
 * use of [binding] instance.
 *
 */
abstract class BaseActivity<VB : ViewDataBinding>(
    private val bindInflater: InflateActivityLayout<VB>
) : AppCompatActivity() {

    /*
    Binding instance to hold the current binding instance to be exposed to the child.
     */
    private var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindInflater(layoutInflater)
        binding?.apply {
            setContentView(root)
            setupViews(savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        getBinding()?.onStart()
    }

    override fun onResume() {
        super.onResume()
        getBinding()?.onResume()
    }

    override fun onPause() {
        super.onPause()
        getBinding()?.onPause()
    }

    override fun onStop() {
        super.onStop()
        getBinding()?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Destroying the binding object explicitly to avoid any potential leaks.
        binding = null
    }
    /**
     * This extension function is used to expose the [View]s associated to the provided
     * [ViewDataBinding] type in order to reduce the overhead of using [binding] instance
     * to access the required [View]s.
     *
     * NOTE: Any child of [BaseActivity] should always override this method to setup the [View]s,
     * instead of overriding [onCreate].
     */
    open fun VB.setupViews(savedInstanceState: Bundle?) {}

    open fun VB.onStart() {}

    open fun VB.onResume() {}

    open fun VB.onPause() {}

    open fun VB.onStop() {}
    /**
     * Helper function to expose the [binding] instance in case it is required outside
     * the [setupViews] function.
     */
    @JvmName(JVM_BINDING_INSTANCE)
    fun getBinding(): VB? = binding

    companion object {
        private const val JVM_BINDING_INSTANCE = "getBindingInstance"
    }
}