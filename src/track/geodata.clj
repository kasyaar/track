(ns track.geodata
  (:import
    java.io.File
    com.maxmind.geoip2.DatabaseReader
    java.net.InetAddress)
  (:require [clojure.java.io :as  io])
  )
; (def mmdbreader (DatabaseReader. (File. (.getFile (io/resource "GeoLite2-City.mmdb")))))
(defn get-mmdb-file "doc-string" []
  (let [db-resource (io/resource "geodata.mmdb")
        db-file (File/createTempFile  "geodata" "mmdb" )]
      (io/copy (io/input-stream db-resource) db-file)
      db-file
    ))

(def mmdbreader (DatabaseReader. (get-mmdb-file)))
; (def mmdbreader (DatabaseReader. (-> "geodata.mmdb" io/resource io/file)))

(defn get-geodata "doc-string" [ip-addr]
  (try
    (let [dt (.city mmdbreader (InetAddress/getByName ip-addr))]
      {:geodata {:city  (.getName (.getCity dt)) :country (.getName (.getCountry dt)) :country-code (.getIsoCode (.getCountry dt)) 
       :lat (.getLatitude (.getLocation dt)) :lon (.getLongitude (.getLocation dt))}})
    (catch Exception e {:geodata {:geodata-error (.getMessage e)}})))
