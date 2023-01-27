package com.example
/*
 * Copyright (C) 2020-2022 Lightbend Inc. <https://www.lightbend.com>
 */


import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.io.StdIn
import spray.json._

case class Book(name: String, author: String)

case class BookList(name: String,author: String)

trait BookJsonProtocol extends DefaultJsonProtocol {
  implicit val BookData = jsonFormat2(Book)
  implicit val BookAdded= jsonFormat2(BookList)
}

object QuickstartApp extends BookJsonProtocol with SprayJsonSupport {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext
    var num=0;
    var bookshelf:Array[Book]=new Array[Book](3)
    val route: Route = (path("api" / "user") & get) {
      entity(as[Book]) { book =>
        bookshelf(num)=book
        num=num+1
        println(bookshelf(0).name)
        complete(BookList(book.name, book.author))
      }
    }
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server now online. Please navigate to http://localhost:8080/api/user\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}