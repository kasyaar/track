(defproject track "0.1.0-SNAPSHOT"
  :description "JETE Project Track system"
  :url "http://www.jeteproject.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main track.core
  :aot [track.core]
  :dependencies [
                 [iron_mq_clojure "1.0.3"]
                 [http-kit "2.1.5"]
                 [compojure "1.1.5"]
                 [org.clojure/clojure "1.5.1"]])
