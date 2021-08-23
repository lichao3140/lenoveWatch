package cn.ycgo.base.medial.utils.tool

import android.app.Activity
import android.util.Log
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.util.*

/**
 *Author:Kgstt
 *Time: 2020/12/30
 */
object TimerManagerHelper {

    private var launch: Job? = null
    private val events = HashMap<String, TimerEvent>()

    init {
        run()
    }

    private fun run() {
        launch = GlobalScope.launch(Dispatchers.IO) {
            for (i in generateSequence(0) { it }) {
                delay(1000)
                Log.e("TimerManagerHelper", "run()")
                val iterator: MutableIterator<Map.Entry<String, TimerEvent>> = events.entries.iterator()
                var entry: Map.Entry<String, TimerEvent>
                while (iterator.hasNext()) {
                    entry = iterator.next()
                    try {
                        if (entry.value.observer == null || entry.value.act == null || entry.value.act?.get() == null ||
                            entry.value.act?.get()?.isFinishing == true
                        ) {
                            iterator.remove()
                        } else {
                            entry.value.observer!!.count++
                            entry.value.observer!!.onCall(entry.value.observer!!.count)
                        }
                    } catch (e: Exception) {
                        iterator.remove()
                    }
                }
                if (events.isNullOrEmpty()) {
                    Log.e("TimerManagerHelper", "cancel()")
                    launch?.cancel()
                }
            }
        }
    }

    fun addObserver(activity: Activity?, observer: TimerManagerHelper.TimerObserver) {
        val event = TimerEvent()
        event.act = WeakReference(activity)
        event.observer = observer
        events[observer.toString()] = event
        checkTask()
    }

    fun removeObserver(observer: TimerManagerHelper.TimerObserver) {
        Log.e("TimerManagerHelper", "removeObserver()")
        events[observer.toString()]?.observer = null
        events[observer.toString()]?.act = null
        checkTask()
    }

    private fun checkTask() {
        if (events.isEmpty()) {
            launch?.cancel()
        } else {
            Log.e("TimerManagerHelper", "start() ${launch?.isCancelled}")
            if (launch?.isCancelled == true) {
                run()
            }
        }
    }

    abstract class TimerObserver {
        var count: Long = 0
        abstract fun onCall(count: Long)
    }

    private class TimerEvent {
        var act: WeakReference<out Activity?>? = null
        var observer: TimerObserver? = null
    }
}