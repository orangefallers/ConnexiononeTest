package com.test.connexiononetest.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.test.connexiononetest.R
import com.test.connexiononetest.tool.RxBindingTools
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
abstract class BaseFragment : Fragment() {

    var mDisposable: Disposable? = null
    var mToast: Toast? = null
    var mCustomTabsOpened: Boolean = false

    private var _mComposite: CompositeDisposable? = null
    val mComposite: CompositeDisposable
        get() {
            return _mComposite ?: run {
                val instance = CompositeDisposable()
                _mComposite = instance

                return@run instance
            }
        }

    private var _mPreferences: RxSharedPreferences? = null
    val mPreferences: RxSharedPreferences
        get() {
            return _mPreferences ?: run {
                @Suppress("DEPRECATION")
                val instance = RxSharedPreferences.create(
                        PreferenceManager.getDefaultSharedPreferences(context))
                _mPreferences = instance

                return@run instance
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate layout view
        val view = inflater.inflate(onResource(), container, false)

        // Return layout view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        initial(savedInstanceState, view)
    }

    abstract fun initial(@NonNull savedInstanceState: Bundle?, view: View)

    abstract fun onResource(): Int

    fun View.clicks(): Observable<Any> = RxBindingTools.onClicks(this)

    fun TextView.changes(): Observable<String> = RxBindingTools.onChanges(this)

    fun CompoundButton.checks(): Observable<Boolean> = RxBindingTools.onChecks(this)

    fun Disposable.composite() = mComposite.add(this)

    inline fun <reified T : Activity> Activity.start(initializer: Intent.() -> Unit) {
        startActivity(Intent(context, T::class.java).apply(initializer))
        overridePendingTransition(R.anim.anim_start_enter, R.anim.anim_start_out)
    }

    inline fun <reified T : Fragment> T.start(argsBuilder: Bundle.() -> Unit): T {
        return apply { arguments = Bundle().apply(argsBuilder) }
    }

    override fun onDetach() {
        super.onDetach()
        // Dispose composite disposable
        mComposite.dispose()

        // Dispose disposable
        mDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

}