package nl.leersprong.app.feature.learn

import java.util.Calendar

object SchoolYearNow {
    fun currentBlock(month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1): SchoolYearBlock = when (month) {
        8, 9 -> SchoolYearBlock.START
        10, 11, 12 -> SchoolYearBlock.AUTUMN
        1, 2 -> SchoolYearBlock.WINTER
        3, 4 -> SchoolYearBlock.SPRING
        else -> SchoolYearBlock.FINAL
    }
}
