(ns track.core
  (:use 
    compojure.core
    [compojure.handler :only [site]]
    ring.middleware.params
    ring.middleware.keyword-params
    org.httpkit.server)
  (:require 
    [compojure.route :as route]
    [ring.util.response :as response]))
; HANDLERS
(defn pixel "doc-string" [request]
  (response/redirect "http://google.com"))

(defn click "redirects if landing presented" [request]
  (if-let [landing (:landing (:params request))]
    (response/redirect landing)
    (response/status (response/response "landing requred") 400)))
; /click?landing=
; /pixel
; /pixel.png
; ? /pixel.js
(defroutes  all-routes
  (GET "/click" [] (wrap-params (wrap-keyword-params click)))
  (GET "/pixel" [] pixel)
  (route/not-found "<p>Page not found</p>"))

(defn -main [] (run-server (site #'all-routes) {:port 8080}))
