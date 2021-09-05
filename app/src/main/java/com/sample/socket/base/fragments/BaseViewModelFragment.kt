package com.sample.socket.base.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import com.sample.socket.base.viewmodels.BaseViewModel
import kotlin.reflect.KClass

/**
 * Base abstraction over [BaseFragment] class to accommodate [ViewModel] provision
 * to the child.
 *
 * This class expects parameters required by [BaseFragment] along with a generic type
 * extending [BaseViewModel] and its [KClass] parameter, in order to process and
 * setup the viewModel implementation required by the child.
 *
 * Open functions [setViewModelBinding], and [observeViewModel] are exposed to its children
 * in order to assign the [ViewModel] instance to data object in the layout, and to observe
 * changes in the viewModel data respectively.
 */
abstract class BaseViewModelFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    bindInflater: InflateFragmentLayout<VB>,
    viewModelClass: KClass<VM>,
    isOwnViewModel: Boolean
) : BaseFragment<VB>(bindInflater) {

    /*
    ViewModel instance to expose the required ViewModel based on the provided type.
     */
    private val viewModel: VM by ViewModelLazy(
        viewModelClass,
        { if (isOwnViewModel) viewModelStore else requireActivity().viewModelStore },
        { defaultViewModelProviderFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            initialApiCalls(setupInitialApiCalls(this))
            initialScreenApiCall(javaClass.name, setupScreenInitialApiCalls(this))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        // The following is called to provide a means to assign the viewModel instance to
        // the data object in the layout.
        getBinding()?.setViewModelBinding(viewModel)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding()?.observeViewModel(viewModel)
    }

    override fun VB.setupViews(savedInstanceState: Bundle?) {
        setupViews(savedInstanceState, viewModel)
    }

    override fun VB.onStart() {
        onStart(viewModel)
    }

    override fun VB.onResume() {
        onResume(viewModel)
    }

    override fun VB.onPause() {
        onPause(viewModel)
    }

    override fun VB.onStop() {
        onStop(viewModel)
    }

    /**
     * This extension function is used to expose both the [ViewDataBinding] and
     * the [ViewModel] associated to the child, in order to the help assign the [vm]
     * instance to the data object in the layout.
     */
    open fun VB.setViewModelBinding(vm: VM) {}

    /**
     * This extension function is used to expose both the [ViewDataBinding] and
     * the [ViewModel] associated to the child, in order to the help observe the
     * changes in the observables provided by the [ViewModel].
     */
    open fun VB.observeViewModel(vm: VM) {}

    open fun VB.setupViews(savedInstanceState: Bundle?, vm: VM) {}

    open fun VB.onStart(vm: VM) {}

    open fun VB.onResume(vm: VM) {}

    open fun VB.onPause(vm: VM) {}

    open fun VB.onStop(vm: VM) {}

    /**
     * To perform initial API calls that set data to the respective [LiveData] objects inside the
     * [ViewModel].
     */
    open fun setupInitialApiCalls(vm: VM): (() -> Unit)? = null

    /**
     * To perform initial API calls that set data to the respective [LiveData] objects inside the
     * [ViewModel].
     */
    open fun setupScreenInitialApiCalls(vm: VM): (() -> Unit)? = null

    /**
     * Helper function to expose the [viewModel] instance in case it is required outside
     * the [observeViewModel] function.
     */
    @JvmName(JVM_VIEW_MODEL_INSTANCE)
    fun getViewModel() = viewModel

    companion object {
        private const val JVM_VIEW_MODEL_INSTANCE = "getViewModelInstance"
    }
}