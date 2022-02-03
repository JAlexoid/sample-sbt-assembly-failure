import Dependencies._

lazy val common = (project in file("."))
  .enablePlugins()
  .configs(IntegrationTest)
  .settings(
    commonSettings,
    libraryDependencies ++= dependencies,
    Defaults.itSettings,
    name := "example",
    test in assembly := {},
    assemblyExcludedJars in assembly := {
      val fcp = (fullClasspath in assembly).value
      fcp filter { jar =>
        jar.data.getName.startsWith("slf4j") || jar.data.getName.startsWith("jackson")
      }
    },
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
    assemblyOutputPath in assembly := baseDirectory.value / "bin" / s"${name.value}.jar"
  )

lazy val commonSettings = Seq(
  ThisBuild / organization := "example",
  ThisBuild / scalaVersion := "2.11.12",
  ThisBuild / version := "0.1.0-SNAPSHOT",
  libraryDependencies ++= dependencies,
  dependencyOverrides ++= overrides,
  resolvers ++= Seq[Resolver](
    "aws-glue-etl-artifacts" at "https://aws-glue-etl-artifacts.s3.amazonaws.com/release/",
    "jitpack" at "https://jitpack.io"
  ),
  scalacOptions ++= Seq(
    "-language:_",
    "-target:jvm-1.8",
    "-deprecation",
    // yes, this is 2 args
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xlint",
    // N.B. doesn't work well with the ??? hole
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard"
  ),
  scalacOptions in Test -= "-Ywarn-dead-code",
  parallelExecution in Test := false,
  run in Compile := Defaults
    .runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))
    .evaluated
)


