package oen.roomwatcher.pusher.actors

import akka.actor.{Actor, ActorRef, Props}
import oen.roomwatcher.pusher.actors.RoomSensorsActor.Tick
import oen.roomwatcher.pusher.models.SensorRecord

class RoomSensorsActor(mongoActor: ActorRef) extends Actor {

  override def receive: Receive = {
    case Tick =>
      println("Tick")
      mongoActor ! mockSensorRecord

    case _ => println(s"Hello from: ${self.path}")
  }

  val r = new scala.util.Random
  def mockSensorRecord: SensorRecord = SensorRecord(r.nextInt(100), "Mocked")
}

object RoomSensorsActor {
  case object Tick
  def props(mongoActor: ActorRef): Props = Props(new RoomSensorsActor(mongoActor))
}
