package com.github.akurilov.chuchundra

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{ complete, get, path }
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.stream.ActorMaterializer

import scala.io.StdIn

/**
 * Created by andrey on 20.08.17.
 */
object Server {

	def main(args: Array[String]) {
	implicit val system = ActorSystem("chuchundra")
	implicit val materializer = ActorMaterializer()
	// needed for the future flatMap/onComplete in the end
	implicit val executionContext = system.dispatcher

	val route = path("hello") {
		get {
			complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
		}
	}

	val bindingFuture = Http().bindAndHandle(route, "localhost", 9020)

	println(s"Server online at http://localhost:9020/\nPress RETURN to stop...")
	StdIn.readLine() // let it run until user presses return
	bindingFuture
		.flatMap(_.unbind()) // trigger unbinding from the port
		.onComplete(_ => system.terminate()) // and shutdown when done
	}
}
