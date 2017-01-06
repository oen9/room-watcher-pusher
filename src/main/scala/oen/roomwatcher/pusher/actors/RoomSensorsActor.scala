package oen.roomwatcher.pusher.actors

import java.io.InputStream

import akka.actor.{Actor, ActorRef, Props}
import oen.roomwatcher.pusher.actors.RoomSensorsActor.Tick
import oen.roomwatcher.pusher.models.SensorRecord

import scala.annotation.tailrec
import scala.util.matching.Regex

class RoomSensorsActor(mongoActor: ActorRef, pythonScript: String) extends Actor {

  val sensorRegex: Regex = """\nH: (\d+)\nT: (\d+)\n""".r

  override def receive: Receive = {
    case Tick =>
      println("Tick")
      mongoActor ! readSensors()

    case _ => println(s"Hello from: ${self.path}")
  }

  def readSensors(): SensorRecord = {

    @tailrec
    def loop(result: String = ""):SensorRecord = {
      result match {
        case sensorRegex(hum, tmp) => SensorRecord(hum.toInt, tmp.toInt)

        case _ =>
          val pyScriptStream: InputStream = getClass.getResourceAsStream(pythonScript)
          import scala.sys.process._
          val result = "python " #< pyScriptStream !!

          loop(result)
      }
    }

    loop()
  }
}

object RoomSensorsActor {
  case object Tick
  def props(mongoActor: ActorRef, pythonScript: String): Props = Props(new RoomSensorsActor(mongoActor, pythonScript))
}
