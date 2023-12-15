package com.aghajari.videosharing.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private class KeyboardHeightProvider(
    private val view: View,
    private val scope: CoroutineScope,
    private val heightState: Animatable<Float, AnimationVector1D>
) : PopupWindow(view.context.getActivity()) {

    private val popupView: View = View(view.context)
    private val parentView: View

    init {
        contentView = popupView
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        inputMethodMode = INPUT_METHOD_NEEDED
        parentView = view.context.getActivity().findViewById(android.R.id.content)
        width = 0
        height = WindowManager.LayoutParams.MATCH_PARENT
        popupView.viewTreeObserver.addOnGlobalLayoutListener { handleOnGlobalLayout() }
    }

    fun start() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if (!isShowing && parentView.windowToken != null) {
                    setBackgroundDrawable(ColorDrawable(0))
                    showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0)
                    return
                }
                if (!isShowing) {
                    handler.post(this)
                }
            }
        })
    }

    fun close() {
        dismiss()
    }

    private fun handleOnGlobalLayout() {
        val keyboardHeight = getInputMethodHeight(popupView.context, view.rootView)
        scope.launch {
            heightState.animateTo(keyboardHeight / view.resources.displayMetrics.density)
        }
    }

    companion object {
        @SuppressLint("DiscouragedPrivateApi")
        private fun getInputMethodHeight(context: Context, rootView: View): Int {
            try {
                val imm =
                    context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val inputMethodManagerClass: Class<*> = imm.javaClass
                val visibleHeightMethod =
                    inputMethodManagerClass.getDeclaredMethod("getInputMethodWindowVisibleHeight")
                visibleHeightMethod.isAccessible = true
                return visibleHeightMethod.invoke(imm) as Int
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return alternativeInputMethodHeight(rootView)
        }

        private fun getViewBottomInset(rootView: View?): Int {
            try {
                @SuppressLint("SoonBlockedPrivateApi") val attachInfoField =
                    View::class.java.getDeclaredField("mAttachInfo")
                attachInfoField.isAccessible = true
                val attachInfo = attachInfoField[rootView]
                if (attachInfo != null) {
                    val stableInsetsField = attachInfo.javaClass.getDeclaredField("mStableInsets")
                    stableInsetsField.isAccessible = true
                    return (stableInsetsField[attachInfo] as Rect).bottom
                }
            } catch (ignore: Exception) {
            }
            return 0
        }

        private fun alternativeInputMethodHeight(rootView: View): Int {
            val viewInset = getViewBottomInset(rootView)
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val availableHeight = rootView.height - viewInset - rect.top
            return availableHeight - (rect.bottom - rect.top)
        }
    }
}

@Composable
fun KeyboardEffect(keyboardHeight: Animatable<Float, AnimationVector1D>) {
    val view = LocalView.current
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val heightProvider = KeyboardHeightProvider(
            view = view,
            scope = scope,
            heightState = keyboardHeight
        ).also { it.start() }

        onDispose {
            heightProvider.close()
        }
    }
}

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}