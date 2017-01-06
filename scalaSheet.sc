import com.typesafe.config.ConfigFactory

def foo = "Hello"

foo

val customConf = ConfigFactory.parseString(
  """
    MONGO_URL = "mongodb://test:test@localhost:27017/test"
    MONGO_URL = ${?MONGO_URL_ROOM_WATCHER}
  """)


val cfg = ConfigFactory.load(customConf)
cfg.getString("MONGO_URL")

val sensorRegex = """\nH: (\d+)\nT: (\d+)\n""".r

def parseSensorData(data: String): Option[(String, String)] = {
  data match {
    case sensorRegex(hum, tmp) => Some(hum -> tmp)
    case _ => None
  }
}

val exampleSensorData =
  """
    |H: 36
    |T: 23
    |""".stripMargin
parseSensorData(exampleSensorData)

val wrongSensorData = "ERR_CRC"
parseSensorData(wrongSensorData)
