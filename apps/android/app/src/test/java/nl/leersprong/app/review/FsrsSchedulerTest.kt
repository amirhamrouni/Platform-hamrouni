package nl.leersprong.app.review

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FsrsSchedulerTest {
    private val now = 1_800_000_000_000L

    @Test
    fun masteryMapsToExpectedRatings() {
        assertEquals(FsrsRating.Again, FsrsScheduler.ratingForMastery(20))
        assertEquals(FsrsRating.Hard, FsrsScheduler.ratingForMastery(55))
        assertEquals(FsrsRating.Good, FsrsScheduler.ratingForMastery(80))
        assertEquals(FsrsRating.Easy, FsrsScheduler.ratingForMastery(95))
    }

    @Test
    fun newGoodReviewCreatesPersistentMemoryState() {
        val result = FsrsScheduler.schedule(null, FsrsRating.Good, now)
        assertEquals(1, result.state.reps)
        assertEquals(0, result.state.lapses)
        assertTrue(result.state.stability > 0.0)
        assertTrue(result.state.difficulty in 1.0..10.0)
        assertTrue(result.nextReviewAtEpochMs > now)
    }

    @Test
    fun againIncrementsLapseAndSchedulesSoon() {
        val result = FsrsScheduler.schedule(null, FsrsRating.Again, now)
        assertEquals(1, result.state.lapses)
        assertEquals(0, result.intervalDays)
        assertEquals(now + 10 * 60_000L, result.nextReviewAtEpochMs)
    }

    @Test
    fun successfulSecondReviewAdvancesState() {
        val first = FsrsScheduler.schedule(null, FsrsRating.Good, now)
        val secondNow = first.nextReviewAtEpochMs
        val second = FsrsScheduler.schedule(first.state, FsrsRating.Good, secondNow)
        assertEquals(2, second.state.reps)
        assertTrue(second.state.stability > 0.0)
        assertTrue(second.nextReviewAtEpochMs > secondNow)
    }
}
