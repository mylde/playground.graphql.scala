name := "graphql_scala"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "2.0.1",
  "org.sangria-graphql" %% "sangria-circe" % "1.3.0",
  "com.typesafe.akka" %% "akka-actor" % "2.6.9",
  "com.typesafe.akka" %% "akka-stream" % "2.6.9",
  "com.typesafe.akka" %% "akka-http" % "10.2.1",
  "de.heikoseeberger" %% "akka-http-circe" % "1.35.0",
  "io.circe" %% "circe-core" % "0.13.0",
  "io.circe" %% "circe-generic" % "0.13.0",
  "io.circe" %% "circe-optics" % "0.13.0",
  "io.circe" %% "circe-parser" % "0.13.0"
)

mainClass in (Compile, run) := Some("playground.gql.Server")