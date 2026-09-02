package nl.leersprong.app.reminder

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val UNIQUE_REVIEW_WORK = "g4-multiplication-smart-review"

object ReviewReminderScheduler {
    fun schedule(context: Context, reviewAtEpochMs: Long) {
        val delay = (reviewAtEpochMs - System.currentTimeMillis()).coerceAtLeast(0L)
        val work = OneTimeWorkRequestBuilder<ReviewReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(UNIQUE_REVIEW_WORK)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            UNIQUE_REVIEW_WORK,
            ExistingWorkPolicy.REPLACE,
            work,
        )
    }
}
