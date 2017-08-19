package com.github.akurilov.chuchundra

import com.lightbend.akka.http.sample.UserRegistryActor.ActionPerformed

trait JsonSupport extends SprayJsonSupport {

  implicit val userJsonFormat = jsonFormat3(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
