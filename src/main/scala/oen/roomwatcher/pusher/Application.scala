package oen.roomwatcher.pusher

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import oen.roomwatcher.pusher.actors.{MongoActor, RoomSensorsActor}

import scala.concurrent.duration.DurationDouble

object Application extends App {

  val cfg = ConfigFactory.load()
  val mongoUri = cfg.getString("MONGO_URI")
  val pythonScript = cfg.getString("PYTHON_SCRIPT")

  val system = ActorSystem("room-watcher-pusher")

  val mongoActor = system.actorOf(MongoActor.props(mongoUri), "mongo-actor")
  val roomSensorsActor = system.actorOf(RoomSensorsActor.props(mongoActor, pythonScript), "room-sensors-actor")

  import system.dispatcher

  val cancellable = system.scheduler.schedule(
    0 seconds,
    5 seconds,
    roomSensorsActor,
    RoomSensorsActor.Tick)

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    cancellable.cancel()
    system.terminate()
    println("App closed")
  }))
}
