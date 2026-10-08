package com.classapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="students") data class Student(@PrimaryKey val id:String,val code:String,val firstName:String,val lastName:String,val mobile:String="",val nationalId:String="",val birthDate:String="",val note:String="",val createdAt:Long=System.currentTimeMillis())
@Entity(tableName="teachers") data class Teacher(@PrimaryKey val id:String,val code:String,val name:String,val mobile:String="",val note:String="")
@Entity(tableName="courses") data class Course(@PrimaryKey val id:String,val code:String,val name:String,val level:String="",val tuition:Long=0,val note:String="")
@Entity(tableName="school_classes") data class SchoolClass(@PrimaryKey val id:String,val code:String,val name:String,val courseId:String="",val teacherId:String="",val startDate:String="",val endDate:String="",val days:String="",val time:String="",val capacity:Int=0,val note:String="")
@Entity(tableName="enrollments") data class Enrollment(@PrimaryKey val id:String,val studentId:String,val classId:String,val enrolledAt:String,val status:String="active")
@Entity(tableName="attendance") data class Attendance(@PrimaryKey val id:String,val enrollmentId:String,val date:String,val status:String,val note:String="")
@Entity(tableName="payments") data class Payment(@PrimaryKey val id:String,val studentId:String,val date:String,val amount:Long,val description:String="")
@Entity(tableName="grades") data class Grade(@PrimaryKey val id:String,val studentId:String,val classId:String,val title:String,val score:Double,val maxScore:Double=20.0,val date:String,val note:String="")
@Entity(tableName="daily_reports") data class DailyReport(@PrimaryKey val id:String,val classId:String,val date:String,val teacherId:String,val text:String,val createdAt:Long=System.currentTimeMillis())
