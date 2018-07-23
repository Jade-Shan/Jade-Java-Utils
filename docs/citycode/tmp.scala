import java.io._

import scala.xml._
import scala.collection.mutable.Map

//val a = XML.load("ctrip.city.xml")
//val b = XML.load("city.xml")
//
//val citymap = 
//(Map[String,(String, String, String)]() /: (b \ "li")) {
//  (map , symbolNode) =>
//  val id = (symbolNode \ "@data-id").toString
//  val filtertext = (symbolNode \ "@data-filtertext").toString.split(" ")
//  //val name = (symbolNode \ "").text.toInt
//  val name = symbolNode.text.toString
//  map(id) = (name,filtertext(1),filtertext(2)) //Creates and returns a new Map
//  map
//}
//
//val citylist = 
//(List[(String, String, String)]() /: (a \ "CityDetail")) {
//  (list, symbolNode) => 
//      ((symbolNode \ "City")(0).text.toString, 
//          (symbolNode \ "CityName")(0).text.toString, 
//          (symbolNode \ "CityEName")(0).text.toString) :: list
//}
//
//
//
//val w1 = new PrintWriter(new File("no-id.txt"))
//
//citylist.foreach((c) => 
//    if(citymap.contains(c._1)) {
//    } else {
//        w1.write(c._1)
//        w1.write("\n")
//    }
//)
//w1.close

var oldlist : List[(String, String, String, String)] = Nil
def addLine(l: String) {
	val r = l.split("\t")
	oldlist = (r(0), r(3), r(1), r(2)) :: oldlist
}
scala.io.Source.fromFile("city.pinyin.js").getLines.foreach( addLine)
//println(oldlist)


val b = XML.load("city.xml")
var pinyinlist = 
(List[(String,String, String, String)]() /: (b \ "li")) {
  (l, symbolNode) =>
  val id = (symbolNode \ "@data-id").toString
  val filtertext = (symbolNode \ "@data-filtertext").toString.split(" ")
  //val name = (symbolNode \ "").text.toInt
  val name = symbolNode.text.toString
  (id, name,filtertext(1),filtertext(2)) :: l
}


var hit: Int = 0

var finallist = 
(List[(String,String, String, String)]() /: oldlist) {
	(l, c) => {
		var city = c
		pinyinlist.foreach((p) => {
				if (c._1 == p._1 && c._2 == p._2) {
					city = p
					hit = hit + 1
				} else {
					// do nothin
				}
			})
		city :: l
	}
}

println(hit)

val w1 = new PrintWriter(new File("pinyin.txt"))
def writeRec(rec: (String,String, String, String)) {
	w1.write( String.format(
		"""{"id":"%s","code":"","short":"%s","spell":"%s","word":"%s"},""",
		rec._1, rec._3, rec._4, rec._2))
}
finallist.foreach(writeRec)
w1.close
