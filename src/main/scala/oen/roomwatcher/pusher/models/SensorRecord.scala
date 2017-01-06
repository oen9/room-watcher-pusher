package oen.roomwatcher.pusher.models

import java.util.Date

import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class SensorRecord(hum: Int, tmp: Int, date: Date = new Date())

object SensorRecord {
  implicit def sensorRecordWriter: BSONDocumentWriter[SensorRecord] = Macros.writer[SensorRecord]
  implicit def sensorRecordReader: BSONDocumentReader[SensorRecord] = Macros.reader[SensorRecord]
}
