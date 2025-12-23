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

    // Global exclusion
    excludeDependencies ++= Seq(
      ExclusionRule("org.slf4j", "slf4j-api")
    ),

    // Shading/relocation rules
    assembly / assemblyShadeRules := Seq(
      ShadeRule.rename("org.apache.http.**" -> "org.apache.iceberg.aws.shaded.org.apache.http.@1").inAll,
      ShadeRule.rename("io.netty.**" -> "org.apache.iceberg.aws.shaded.io.netty.@1").inAll
    ),

    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "services", xs @ _*) => MergeStrategy.concat
      case PathList("META-INF", "MANIFEST.MF")       => MergeStrategy.discard
      case PathList("META-INF", xs @ _*) if xs.lastOption.exists(x => x.endsWith(".SF") || x.endsWith(".DSA") || x.endsWith(".RSA")) =>
        MergeStrategy.discard
      case PathList("META-INF", xs @ _*)             => MergeStrategy.first
      case "module-info.class"                       => MergeStrategy.discard
      case x if x.endsWith(".properties")            => MergeStrategy.first
      case x                                         => MergeStrategy.first
    },

    assembly / assemblyJarName := "hadoop-aws-sdk-minimal.jar"
  )

// plugins.sbt needs: addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.1.5")