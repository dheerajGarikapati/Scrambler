(ns scrambler.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.string :as string]
            [compojure.core :refer [defroutes GET]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.core :as hiccup]))

(defn html-input-form [title & content]
  (hiccup/html
   [:head
    [:title title]]
   [:body [:form {:action "/scramble/result" :method "GET"}
           "String One: " [:input {:type "text" :id "string1" :name "str1" :value ""}] [:br] [:br]
           "String Two: " [:input {:type "text" :id "string2" :name "str2" :value ""}] [:br] [:br]
           [:input {:type "Submit" :value "Submit"}]]
    content]))

(defn application-html [title req function]
  (let [string-1 (get (:params req) :str1)
        string-2 (get (:params req) :str2)]
    (html-input-form title [:h3 (str "Scramble result for \"" string-1 "\" and \"" string-2 "\" is : " (function string-1 string-2))])))

(defn scramble? [str1 str2]
  (let [vec2 (vec str2)
        value (map #(string/includes? str1 (str %)) vec2)]
    (every? true? value)))

(defroutes myroutes
  (GET "/scramble" [] (html-input-form "Scramble Application"))
  (GET "/scramble/result" req (application-html "Scramble result" req scramble?)))

(defn -main [& args]
  (-> myroutes
      (wrap-defaults  (assoc-in site-defaults [:security :anti-forgery] false))
      (jetty/run-jetty {:port  8080
                        :join? false}))
  (println "Server Started"))