import sbt._

object Dependencies {
  val spark = "org.apache.spark"

  val sparkVersion = "2.4.8"

  lazy val dependencies = (buildDependencies ++ sparkDependencies ++ testDependencies)

  lazy val buildDependencies = Seq(
    "software.amazon.awssdk" % "s3"                       % "2.17.122",
    "ch.qos.logback"         % "logback-classic"          % "1.2.3",
    "net.logstash.logback"   % "logstash-logback-encoder" % "6.4",
    "com.datadoghq"          % "datadog-api-client"       % "1.4.0"
  )

  val sparkDependencies = Seq("spark-core", "spark-sql", "spark-mllib")
    .map(spark %% _ % sparkVersion % "provided")
    .map(
      _.excludeAll(
        ExclusionRule("log4j"),
        ExclusionRule("slf4j-log4j12")
      )
    )

  lazy val testDependencies = Seq(
    "org.scalamock"     %% "scalamock"          % "4.4.0"        % "test, it",
    "org.scalatest"     %% "scalatest"          % "3.1.1"        % "test, it",
    "org.scalatestplus" %% "mockito-3-4"        % "3.2.2.0"      % "test, it",
    "com.holdenkarau"   %% "spark-testing-base" % "2.4.3_0.14.0" % "test, it",
    "org.scalacheck"    %% "scalacheck"         % "1.14.1"       % "test, it"
  )

  lazy val overrides = Seq(
    "com.fasterxml.jackson.core"   % "jackson-core"          % "2.10.5" % "provided",
    "com.fasterxml.jackson.core"   % "jackson-databind"      % "2.10.5" % "provided",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.5" % "provided"
  )

}
