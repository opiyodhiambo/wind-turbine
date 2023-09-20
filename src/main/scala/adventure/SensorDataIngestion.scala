package adventure

import akka.{NotUsed, Done}
import adventure.SensorData
import akka.stream.scaladsl._
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import GraphDSL.Implicits._
import akka.stream.ClosedShape

object SensorDataIngestion {
  implicit val system: ActorSystem = ActorSystem("SensorDataIngest")
  implicit val materializer: Materializer = ActorMaterializer()
  // Simulating a data source
  val source: Source[SensorData, NotUsed] = Source(
    List(
      SensorData(
        "device1",
        System.currentTimeMillis(),
        Measurements(10.0, 100.0, 20.0)
      ),
      SensorData(
        "device2",
        System.currentTimeMillis(),
        Measurements(10.0, 100.0, 20.0)
      ),
      SensorData(
        "device3",
        System.currentTimeMillis(),
        Measurements(10.0, 100.0, 20.0)
      ),
      SensorData(
        "device4",
        System.currentTimeMillis(),
        Measurements(10.0, 100.0, 20.0)
      ),
      SensorData(
        "device5",
        System.currentTimeMillis(),
        Measurements(-10.0, 100.0, 20.0)
      )
    )
  )

  // The graphDSL
  val validationGraph = GraphDSL.create() {
    implicit builder: GraphDSL.Builder[NotUsed] =>
      // The building blocks
      val input = builder.add(source)
      // For invalid metrics
      val invalidMetricsLogger = builder.add(Sink.foreach[SensorData](data => {
        if (!SensorDataValidator.isVlaidMetric(data.measurements)) {
          println(data)
        }
      }))
      // For valid metrics
      val validMetricsLogger = builder.add(Sink.foreach[SensorData](data => {
        if (SensorDataValidator.isVlaidMetric(data.measurements)) {
          println(data)
        }
      }))
      val broadcast = builder.add(Broadcast[SensorData](2))

      // The pipeline layout
      input ~> broadcast
      broadcast.out(0) ~> validMetricsLogger
      broadcast.out(1) ~> invalidMetricsLogger

      // Closing
      ClosedShape

  }

  def main(args: Array[String]): Unit = {
    RunnableGraph.fromGraph(validationGraph).run()
  }

}
