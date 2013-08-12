(defproject jete_track "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main jete-track.core
  :aot [jete-track.core]
  :dependencies [
                 [http-kit "2.1.5"]
                 [octohipster "0.2.1-SNAPSHOT"]
                 [org.clojure/clojure "1.5.1"]])
