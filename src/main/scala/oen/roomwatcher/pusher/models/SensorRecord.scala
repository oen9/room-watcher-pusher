package oen.roomwatcher.pusher.models

import java.util.Date

import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class SensorRecord(v1: Int, v2: String, date: Date = new Date())

object SensorRecord {
  implicit def sensorRecordWriter: BSONDocumentWriter[SensorRecord] = Macros.writer[SensorRecord]
  implicit def sensorRecordReader: BSONDocumentReader[SensorRecord] = Macros.reader[SensorRecord]
}
