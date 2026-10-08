package com.classapp.util
import android.content.Context
import com.classapp.data.AppDatabase
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStream

object Backup {
    suspend fun export(db:AppDatabase,out:OutputStream){val root=JSONObject();fun arr(a:List<Any>,keys:Array<String>):JSONArray{val x=JSONArray();a.forEach{v->val o=JSONObject();keys.forEachIndexed{i,k->val f=v.javaClass.declaredFields.firstOrNull{it.name==k};f?.isAccessible=true;val z=f?.get(v);o.put(k,z);};x.put(o)};return x};root.put("version",3);root.put("students",arr(db.students().all(),arrayOf("id","code","firstName","lastName","mobile","nationalId","birthDate","note","createdAt")));root.put("teachers",arr(db.teachers().all(),arrayOf("id","code","name","mobile","note")));root.put("courses",arr(db.courses().all(),arrayOf("id","code","name","level","tuition","note")));root.put("classes",arr(db.classes().all(),arrayOf("id","code","name","courseId","teacherId","startDate","endDate","days","time","capacity","note")));root.put("enrollments",arr(db.enrollments().all(),arrayOf("id","studentId","classId","enrolledAt","status")));root.put("attendance",arr(db.attendance().all(),arrayOf("id","enrollmentId","date","status","note")));root.put("payments",arr(db.payments().all(),arrayOf("id","studentId","date","amount","description")));root.put("grades",arr(db.grades().all(),arrayOf("id","studentId","classId","title","score","maxScore","date","note")));root.put("dailyReports",arr(db.dailyReports().all(),arrayOf("id","classId","date","teacherId","text","createdAt")));out.writer().use{it.write(root.toString(2));it.flush()}}
}
