package adventure

object SensorDataValidator {
  def isVlaidMetric(m: Measurements) = m.rotorSpeed >= 0.00 & m.windSpeed >= 0.00
}
