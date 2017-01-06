package oen.roomwatcher.pusher.actors

import akka.actor.{Actor, Props}
import oen.roomwatcher.pusher.models.SensorRecord
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.Future

case class Person(age: Int)

class MongoActor(mongoUri: String) extends Actor {

  import scala.concurrent.ExecutionContext.Implicits.global

  val driver = MongoDriver()
  val database: Future[DefaultDB] = for {
    uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
    con = driver.connection(uri)
    dn <- Future(uri.db.get)
    db <- con.database(dn)
  } yield db

  val col : Future[BSONCollection] = database.map(_.collection("sensorRecords"))

  override def receive: Receive = {
    case s: SensorRecord =>
      col.flatMap(_.insert(s))

    case _ =>
      println(s"Hello from: ${self.path}")
  }
}

object MongoActor {
  def props(mongoUri: String): Props = Props(new MongoActor(mongoUri))
}
