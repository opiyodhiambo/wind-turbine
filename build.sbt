name := "wind-turbine"

version := "1.0"

scalaVersion := "2.13.10"

val akkaVersion = "2.8.4"
val AkkaManagementVersion = "1.4.1"

// resolvers += "Akka library repository".at("https://repo.akka.io/maven")
enablePlugins(JavaAppPackaging, DockerPlugin)

Compile / mainClass := Some("adventure.SensorDataIngestion")
dockerBaseImage :="eclipse-temurin:8u382-b05-jre@sha256:d86662a974929a154fe46d10fa17006b3663015e23caff53d2227b8de325bceb"
ThisBuild / dynverSeparator := "-"
// COnfiguring the sbt-native-packager to tag my image as latest
dockerUpdateLatest := true


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.1.4" % Test,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % AkkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % AkkaManagementVersion,
  "com.typesafe.akka" %% "akka-discovery" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % AkkaManagementVersion)
