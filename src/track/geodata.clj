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
      {:geodata {:city  (.getName (.getCity dt)) :country (.getName (.getCountry dt)) :country-code (.getIsoCode (.getCountry dt)) 
       :lat (.getLatitlude (.getLocation dt)) :lon (.getLongitude (.getLocation dt))}})
    (catch Exception e {:geodata {:geodata-error (.getMessage e)}})))
