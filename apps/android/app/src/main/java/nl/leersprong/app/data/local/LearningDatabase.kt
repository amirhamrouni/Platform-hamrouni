package nl.leersprong.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [LessonAttemptEntity::class, SkillReviewEntity::class],
    version = 2,
    exportSchema = true,
)
abstract class LearningDatabase : RoomDatabase() {
    abstract fun learningDao(): LearningDao

    companion object {
        @Volatile private var instance: LearningDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE skill_reviews ADD COLUMN fsrsStability REAL NOT NULL DEFAULT 0.0")
                db.execSQL("ALTER TABLE skill_reviews ADD COLUMN fsrsDifficulty REAL NOT NULL DEFAULT 0.0")
                db.execSQL("ALTER TABLE skill_reviews ADD COLUMN fsrsReps INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE skill_reviews ADD COLUMN fsrsLapses INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE skill_reviews ADD COLUMN fsrsLastReviewAtEpochMs INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun get(context: Context): LearningDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                LearningDatabase::class.java,
                "leersprong-learning.db",
            ).addMigrations(MIGRATION_1_2)
                .build()
                .also { instance = it }
        }
    }
}
