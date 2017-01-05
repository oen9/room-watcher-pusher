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
