(ns track.core
  (:use 
    compojure.core
    [compojure.handler :only [site]]
    ring.middleware.params
    ring.middleware.keyword-params
    org.httpkit.server)
  (:require 
    [iron-mq-clojure.client :as mq]
    [compojure.route :as route]
    [ring.util.response :as response]))
(def mq-client (mq/create-client 
                 "cUEBGXGmRpcia3iLhKuE6cFJwtY" "520b0dea7347e7000500000b"))
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
(defn click "redirects if landing presented" [request]
  (if-let [landing (:landing (:params request))]
    ((mq/post-message mq-client "click" (str landing))
    ; TODO: 
    ; add url check
    ; extract any possible info from headers
    ; add log event and log any possible info from headers
    
    (response/redirect landing))
    (response/status (response/response "landing requred") 400)))

(defn pixel "doc-string" [request]
  (mq/post-message mq-client "pixel" (str request))
  ; TODO:
  ; add cookie set
  (response/redirect "/img/pixel.png"))

(defroutes  all-routes
  (GET "/click" [] (wrap-params (wrap-keyword-params click)))
  (GET "/pixel" [] (wrap-params pixel))
  (route/files "/")
  (route/not-found "<p>Page not found</p>"))

(defn -main [port] (run-server (site #'all-routes) {:port (Integer. port)}))
