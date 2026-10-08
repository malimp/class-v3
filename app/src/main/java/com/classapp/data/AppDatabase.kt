package com.classapp.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.classapp.model.*

@Database(entities=[Student::class,Teacher::class,Course::class,SchoolClass::class,Enrollment::class,Attendance::class,Payment::class,Grade::class,DailyReport::class],version=1,exportSchema=false)
abstract class AppDatabase:RoomDatabase(){abstract fun students():StudentDao;abstract fun teachers():TeacherDao;abstract fun courses():CourseDao;abstract fun classes():ClassDao;abstract fun enrollments():EnrollmentDao;abstract fun attendance():AttendanceDao;abstract fun payments():PaymentDao;abstract fun grades():GradeDao;abstract fun dailyReports():DailyReportDao
 companion object { @Volatile private var I:AppDatabase?=null; fun get(c:Context)=I?:synchronized(this){I?:Room.databaseBuilder(c.applicationContext,AppDatabase::class.java,"class_school.db").fallbackToDestructiveMigration().build().also{I=it}} }
}
