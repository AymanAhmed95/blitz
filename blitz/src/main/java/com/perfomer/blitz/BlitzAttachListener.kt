package com.perfomer.blitz

import android.view.View
import android.widget.TextView

internal class BlitzAttachListener(
    private val target: TextView
) : View.OnAttachStateChangeListener {

    internal var time: Long = 0
        set(value) {
            if (field == value) return
            field = value
            updateRunnableSubscription()
        }

    internal var showSeconds: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            updateRunnableSubscription()
        }

    //todo remove
    var counter = 0

    private val runnable = object : Runnable {
        override fun run() {
            val diff = System.currentTimeMillis() - time
            val blitzTime = getBlitzTime(diff)

            target.diffedValue = blitzTime.getText(target.context, diff, showSeconds) + " " + counter
            counter++
            target.postDelayed(this, blitzTime.updateRateMs)
        }
    }

    override fun onViewDetachedFromWindow(v: View?) {
        target.removeCallbacks(runnable)
    }

    override fun onViewAttachedToWindow(v: View?) {
        updateRunnableSubscription()
    }

    fun dropCounter() {
        target.removeCallbacks(runnable)
    }

    private fun updateRunnableSubscription() {
        target.removeCallbacks(runnable)
        target.post(runnable)
    }

}