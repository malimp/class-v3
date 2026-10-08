package com.classapp.util
import java.util.UUID
object Id { fun id()=UUID.randomUUID().toString(); fun studentCode()="ST-"+UUID.randomUUID().toString().substring(0,8).uppercase(); fun teacherCode()="TE-"+UUID.randomUUID().toString().substring(0,8).uppercase(); fun courseCode()="CO-"+UUID.randomUUID().toString().substring(0,8).uppercase(); fun classCode()="CL-"+UUID.randomUUID().toString().substring(0,8).uppercase() }
