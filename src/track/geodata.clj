(ns track.geodata
  (:import
    java.io.File
    java.net.URI
    java.net.URL
    com.maxmind.geoip2.DatabaseReader
    java.net.InetAddress)
  (:require [clojure.java.io :as  io])
  )
; (def mmdbreader (DatabaseReader. (File. (.getFile (io/resource "GeoLite2-City.mmdb")))))
(def mmdbreader (DatabaseReader. (File. (.getFile (io/resource "geodata.mmdb")))))

(defn get-geodata "doc-string" [ip-addr]
  (try
    (let [dt (.city mmdbreader (InetAddress/getByName ip-addr))]
      {:city  (.getName (.getCity dt)) :country (.getName (.getCountry dt)) :country-code (.getIsoCode (.getCountry dt))})
    (catch Exception e {:geodata-error (.getMessage e)})))
