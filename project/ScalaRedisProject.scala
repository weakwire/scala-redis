import sbt._
import Keys._

object ScalaRedisProject extends Build
{
  import Resolvers._
  lazy val root = Project("RedisClient", file(".")) settings(coreSettings : _*)

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    organization := "net.debasishg",
    version := "2.10",
    scalaVersion := "2.10.0",
    crossScalaVersions := Seq("2.10.0-RC5"),

    scalacOptions <++= scalaVersion.map {sv =>
      if (sv contains "2.10") Seq("-deprecation", "-unchecked", "-feature", "-language:postfixOps")
      else Seq("-deprecation", "-unchecked")
    },

    resolvers ++= Seq(akkaRepo)
  )

  lazy val coreSettings = commonSettings ++ Seq(
    name := "RedisClient",

    libraryDependencies <<= scalaVersion {v =>
      if (v contains "2.10")
        Seq(
          "commons-pool"      %  "commons-pool"            % "1.6",
          "org.scala-lang"    %  "scala-actors"            % "2.10.0",
          "com.typesafe.akka" %  "akka-actor_2.10"         % "2.1.0",
          "org.slf4j"         %  "slf4j-api"               % "1.7.2",
          "org.slf4j"         %  "slf4j-log4j12"           % "1.7.2"      % "provided",
          "log4j"             %  "log4j"                   % "1.2.16"     % "provided",
          "junit"             %  "junit"                   % "4.8.1"      % "test",
          "org.scalatest"     %  "scalatest_2.10.0"    % "2.0.M5"     % "test")
      else
        Seq(
          "commons-pool"      %  "commons-pool"            % "1.6",
          "com.typesafe.akka" %  "akka-actor"              % "2.0.5",
          "org.scala-lang"    %  "scala-library"           % v,
          "org.slf4j"         %  "slf4j-api"               % "1.7.2",
          "org.slf4j"         %  "slf4j-log4j12"           % "1.7.2"      % "provided",
          "log4j"             %  "log4j"                   % "1.2.16"     % "provided",
          "junit"             %  "junit"                   % "4.8.1"      % "test",
          "org.scalatest"     %  ("scalatest_" + v)          % "2.0.M4"      % "test")
    }   
   
}

object Resolvers {
  val akkaRepo = "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
}
