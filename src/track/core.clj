(ns track.core
  (:use 
    compojure.core
    [compojure.handler :only [site]]
    org.httpkit.server)
  (:require 
    [compojure.route :as route]
    [ring.util.response :as response]))

(defn pixel "doc-string" [request]
  (response/redirect "http://google.com"))

(defroutes  all-routes
  (GET "/pixel" [] pixel)
  (route/not-found "<p>Page not found</p>"))

(defn -main [] (run-server (site #'all-routes) {:port 8080}))
