package com.classapp.util

import java.io.InputStream
import java.util.zip.ZipInputStream
import javax.xml.parsers.DocumentBuilderFactory

/** Minimal dependency-free XLSX reader for simple first-sheet tabular files. It reads shared strings and cell values. */
object XlsxReader {
    fun read(input:InputStream):List<List<String>>{
        val files=mutableMapOf<String,ByteArray>(); ZipInputStream(input).use{z->var e=z.nextEntry;while(e!=null){if(!e.isDirectory)files[e.name]=z.readBytes();e=z.nextEntry}}
        val shared=files["xl/sharedStrings.xml"]?.let{parseShared(it)}?:emptyList()
        val sheetName=findFirstSheet(files) ?: return emptyList(); val data=files[sheetName]?:return emptyList()
        val db=DocumentBuilderFactory.newInstance().newDocumentBuilder(); val doc=db.parse(data.inputStream()); val rows=doc.getElementsByTagName("row"); val out=mutableListOf<List<String>>()
        for(i in 0 until rows.length){val row=rows.item(i); val cells=row.childNodes; val map=mutableMapOf<Int,String>(); var max=-1; for(j in 0 until cells.length){val n=cells.item(j);if(n.nodeName!="c")continue; val ref=n.attributes?.getNamedItem("r")?.nodeValue?: continue; val col=colIndex(ref); val t=n.attributes?.getNamedItem("t")?.nodeValue; val v=n.childNodes.let{var x="";for(k in 0 until it.length)if(it.item(k).nodeName=="v")x=it.item(k).textContent; x}; val value=if(t=="s")shared.getOrNull(v.toIntOrNull()?:-1)?:"" else v; map[col]=value;max=maxOf(max,col)}; out.add((0..max).map{map[it]?:""}) }
        return out
    }
    private fun parseShared(bytes:ByteArray):List<String>{val d=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bytes.inputStream());val ns=d.getElementsByTagName("si");return List(ns.length){i->ns.item(i).textContent}}
    private fun findFirstSheet(files:Map<String,ByteArray>):String?{val wb=files["xl/workbook.xml"]?:return null;val rel=files["xl/_rels/workbook.xml.rels"]?:return null;val d=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(wb.inputStream());val s=d.getElementsByTagName("sheet").item(0)?:return null;val rid=s.attributes.getNamedItem("r:id")?.nodeValue?:return null;val rd=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rel.inputStream());val rs=rd.getElementsByTagName("Relationship");for(i in 0 until rs.length){val n=rs.item(i);if(n.attributes.getNamedItem("Id")?.nodeValue==rid){val t=n.attributes.getNamedItem("Target")?.nodeValue?:return null;return if(t.startsWith("/"))t.removePrefix("/") else "xl/$t"}};return null}
    private fun colIndex(ref:String):Int{var n=0;for(c in ref.takeWhile{it.isLetter()})n=n*26+(c.uppercaseChar()-'A'+1);return n-1}
}
