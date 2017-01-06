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

val sensorRegex = """Humidity:(\d+)%\nTemperature:(\d+)C\n""".r

def parseSensorData(data: String): Option[(String, String)] = {
  data match {
    case sensorRegex(hum, tmp) => Some(hum -> tmp)
    case _ => None
  }
}

val exampleSensorData =
  """Humidity:36%
    |Temperature:23C
    |""".stripMargin
parseSensorData(exampleSensorData)

val wrongSensorData = "ERR_CRC"
parseSensorData(wrongSensorData)
