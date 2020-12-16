logLevel := Level.Warn

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.2")

resolvers += Resolver.bintrayRepo("givers", "maven")

addSbtPlugin("givers.vuefy" % "sbt-vuefy" % "5.0.0")