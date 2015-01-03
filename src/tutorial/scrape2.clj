(ns tutorial.scrape2
  (:require [clj-http.client]
            [net.cgrand.enlive-html :as html]))

(def ^:dynamic *base-url* "https://news.ycombinator.com/")

(defn fetch-url [url]
  (-> url clj-http.client/get :body java.io.StringReader. html/html-resource))

(defn hn-headlines-and-points []
  (map html/text
       (html/select (fetch-url *base-url*)
                    #{[:td.title :a] [:td.subtext html/first-child]})))

(defn print-headlines-and-points []
  (doseq [line (map (fn [[h s]] (str h " (" s ")"))
                    (partition 2 (hn-headlines-and-points)))]
    (println line)))
