(ns track.dbip
  (:use [clojure.string :only [split join]])
  (:require 
    [clojure.java.io :as io]
    [clojure.data.csv :as csv]))

(def inet-mul [16777216 65536 256 1])

(defn inet-aton "transform ip to unique in" [a]
  (let [q (map read-string (split a #"\."))]
    (reduce + (map * q inet-mul))))

(defn inet-ntoa "transform unique int to ip" [n]
  (reduce  
    (fn [& args] (join "." args)) 
    (map #(rem (quot %1 %2) 256) (repeat 4 n) inet-mul)))

(defn read-dbip-csv "reads dbip database" []
  (with-open [rdr (io/reader (io/resource "dbip-city.csv"))]
    (doseq [[left right & _] (csv/read-csv rdr)]
      (let [ileft (inet-aton left)
            iright (inet-aton right)
            mid (+ ileft (quot (- iright ileft) 2))]
        (println (join " - " [ileft mid iright]))))))

