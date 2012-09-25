(defproject fractal-realms "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [com.datomic/datomic-free "0.8.3524"]]
  :plugins [[lein-ring "0.7.3"]]
  :ring {:handler fractal-realms.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
