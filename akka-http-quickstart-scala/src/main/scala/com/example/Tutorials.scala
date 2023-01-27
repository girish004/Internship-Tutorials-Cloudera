package com.example

import java.io._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Tutorials{

  def exception():Unit={
    try {
      val div = 5 / 0
      val f = new FileReader("input.txt")
    } catch {
      case ex: FileNotFoundException => {
        println("Missing file exception")
        println(ex)
      }
      case ex2: ArithmeticException => {
        println("Cannot divide by zero")
        println(ex2)
      }
      case ex: IOException => {
        println("IO Exception")
      }

    }
  }
  def closure_lambda():Unit={
    var mul= (a:Int,b:Int)=> {
      var c=a+1
      c*b*a
    }
    println(mul(2,3))
  }
  def collections(): Unit={
    // List
    var list1=List(1,2,3)
    list1=list1.updated(1,6)
    println(list1)
    // Set
    var set1=Set(1,2,3,3)
    println(set1-4-3)
    // Maps
    var map1 = Map("Hello"-> "English", "Vanakkam"-> "Telugu")
    println(map1.keys)
    println(map1.values)
    map1+="Namaste"->"Hindi"
    map1=map1.updated("Vanakkam","Tamil")
    println(map1)

  }
  def futures(): Unit = {
    val fut = Future { Thread.sleep(10000); 21 + 21 }
    println(fut.isCompleted)
    Thread.sleep(20000)
    //println(fut.isCompleted)
  }
  def anonymous(): Unit = {
    case class anonym(name: String){
      def intro():Unit= {
        println(s"Hello this is ${name}")
      }
    }
    var objAnonym= anonym{
      "Girish"
    }
    println(objAnonym.intro())
  }
  def functional_programming(): Unit={
    val something=new Function1[Int,Int] {
       override def apply(arg1:Int): Int= {
        arg1+2
      }
    }
    val list1=List(1,2,3).map(x=>x+1)
    val list2=List(1,2,3).map(x=>List(x,x+1))
    println(list1)
    println(list2)
  }
  def main(args: Array[String]): Unit = {
    exception()
    closure_lambda()
    collections()
    //futures()
    anonymous()
    functional_programming()
  }
}