(defproject scrambler "0.1.0-SNAPSHOT"
  :description "Clojure web service that accepts two strings in a request and applies function scramble?
                returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false."
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.1.6"]
                 [ring/ring-defaults "0.1.2"]
                 [hiccup "1.0.5"]]
  :main ^:skip-aot scrambler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})