package com.sample.socket.base.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Type Alias class for accepting a lambda with [LayoutInflater], [ViewGroup], and [Boolean]
 * respectively for inflating the required [ViewDataBinding] object into the
 * [BaseFragment].
 */
typealias InflateFragmentLayout<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 * Base class for [Fragment]s that use [ViewDataBinding].
 *
 * The class expects a generic [ViewDataBinding] type along with [bindInflater] (a type alias for
 * accepting a lambda satisfying [InflateFragmentLayout] arguments) in order to process
 * and setup the view binding required for the child.
 *
 * An open extension function [setupViews] is exposed to its children, where the [View]s
 * bound the the provided [ViewDataBinding] class are provided for access without the
 * use of [binding] instance.
 *
 */
abstract class BaseFragment<VB : ViewDataBinding>(
    private val bindInflater: InflateFragmentLayout<VB>
) : Fragment() {

    /*
    Binding instance to hold the current binding instance to be exposed to the child.
     */
    private var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindInflater(inflater, container, false)
        return binding?.run {
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding()?.apply {
            setupViews(savedInstanceState)
            initListeners()
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

    override fun onDestroyView() {
        super.onDestroyView()
        // Destroying the binding object a soon as the View associated to the child fragment
        // is destroyed, to avoid memory leaks, as the View lifecycle is different from the
        // Fragment's lifecycle.
        getBinding()?.onDestroyView()
        binding = null
    }

    /**
     * This extension function is used to expose the [View]s associated to the provided
     * [ViewDataBinding] type in order to reduce the overhead of using [binding] instance
     * to access the required [View]s.
     *
     * NOTE: Any child of [BaseFragment] should always override this method to setup the [View]s,
     * instead of overriding [onViewCreated].
     */
    open fun VB.setupViews(savedInstanceState: Bundle?) {}

    open fun VB.onStart() {}

    open fun VB.onResume() {}

    open fun VB.onPause() {}

    open fun VB.onStop() {}

    open fun VB.onDestroyView() {}

    open fun VB.initListeners(){}

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