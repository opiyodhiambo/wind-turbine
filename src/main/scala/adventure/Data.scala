package adventure

case class SensorData(devideId: String, timestamp: Long, measurements: Measurements)
case class Measurements(power: Double, rotorSpeed: Double, windSpeed: Double)
case class Metrics(devideId: String, timestamp: Long, name: String, value: Double)
case class InvalidMetrics(metric: Metrics, error: String)
