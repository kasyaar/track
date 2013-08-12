(ns jete-track.core
  (:use 
    [octohipster core routes mixins]
    [octohipster.documenters swagger]
    org.httpkit.server)
  (:require 
    [liberator.core :as lib]
    [ring.util.response :as resputil]))


(defresource pixel-handler
  :desc "Handle tracking pixel"
  :url "/pixel"
  :mixins [handled-resource]
  :post-redirect? true
  :exists? (fn [ctx] (println (str (:request ctx))) {:location "EBA!"}))

(defgroup root-group
  :resources [pixel-handler])

(defroutes site
  :groups [root-group])

(defn -main [] (run-server site {:port 8080}))
