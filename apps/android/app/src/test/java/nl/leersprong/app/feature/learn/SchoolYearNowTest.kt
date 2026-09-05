package nl.leersprong.app.feature.learn

import org.junit.Assert.assertEquals
import org.junit.Test

class SchoolYearNowTest {
    @Test
    fun monthsMapToExpectedSchoolYearBlocks() {
        assertEquals(SchoolYearBlock.START, SchoolYearNow.currentBlock(8))
        assertEquals(SchoolYearBlock.START, SchoolYearNow.currentBlock(9))
        assertEquals(SchoolYearBlock.AUTUMN, SchoolYearNow.currentBlock(10))
        assertEquals(SchoolYearBlock.AUTUMN, SchoolYearNow.currentBlock(12))
        assertEquals(SchoolYearBlock.WINTER, SchoolYearNow.currentBlock(1))
        assertEquals(SchoolYearBlock.WINTER, SchoolYearNow.currentBlock(2))
        assertEquals(SchoolYearBlock.SPRING, SchoolYearNow.currentBlock(3))
        assertEquals(SchoolYearBlock.SPRING, SchoolYearNow.currentBlock(4))
        assertEquals(SchoolYearBlock.FINAL, SchoolYearNow.currentBlock(5))
        assertEquals(SchoolYearBlock.FINAL, SchoolYearNow.currentBlock(7))
    }
}
