(ns track.geodata
  (:import
    java.io.File
    com.maxmind.geoip2.DatabaseReader
    java.net.InetAddress)
  )
(def mmdbreader (DatabaseReader. (File. (.getFile (clojure.java.io/resource "GeoLite2-City.mmdb")))))

(defn get-geodata "doc-string" [ip-addr]
  (try
    (let [dt (.city mmdbreader (InetAddress/getByName ip-addr))]
      {:city  (.getName (.getCity dt)) :country (.getName (.getCountry dt)) :country-code (.getIsoCode (.getCountry dt))})
    (catch Exception e {:geodata-error (.getMessage e)})))
