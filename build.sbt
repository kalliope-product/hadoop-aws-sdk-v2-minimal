import sbtassembly.AssemblyPlugin.autoImport._
lazy val root = (project in file("."))
  .settings(
    name := "hadoop-aws-sdk-minimal",
    version := "1.0.0",
    scalaVersion := "2.13.12",

    libraryDependencies ++= {
      val awsSdkVersion = "2.29.52"
      Seq(
        "software.amazon.awssdk" % "s3"                  % awsSdkVersion,
        "software.amazon.awssdk" % "s3-transfer-manager" % awsSdkVersion,
        "software.amazon.awssdk" % "sts"                 % awsSdkVersion,
        "software.amazon.awssdk" % "apache-client"       % awsSdkVersion,
        "software.amazon.awssdk" % "kms"                 % awsSdkVersion,
        "software.amazon.awssdk.crt" % "aws-crt"         % "0.29.25"
      )
    },

    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "services", _*) => MergeStrategy.concat
      case PathList("META-INF", _*)             => MergeStrategy.discard
      case "module-info.class"                  => MergeStrategy.discard
      case x                                    => MergeStrategy.first
    },

    assembly / assemblyJarName := "hadoop-aws-sdk-minimal.jar"
  )

// plugins.sbt needs: addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.1.5")