(ns fractal-realms.handler
  (:use compojure.core)
  (:use [datomic.api :as d])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn parse-long [s]
   (Long. (re-find  #"\d+" s)))

(def uri "datomic:free://localhost:4334//fractal_realms")
(def conn (d/connect uri))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/entity/:id/visible-entities" [id]
    (pr-str [1 2 3 4 id id]))
  (GET "/entity/:id" [id]
    (pr-str [id id id]))

  (GET "/region" [x y]
    (pr-str [:x x :y y]))

  (GET "/region/:id" [id] 
    (pr-str {:get-region id}))

  (GET "/actor" []
    (pr-str (into [] (q '[:find ?a ?n :where [?a :actor/name ?n]] (db conn))))) 

  (GET "/actor/:id" [id]
    (pr-str 
      (let 
        [actor (first (q '[:find ?c ?n :in $ ?c :where [$ ?c :actor/name ?n]] (db conn) (parse-long id)))]
        (into {} (apply map vector [[:id :name] actor])))))

  (POST "/actor" [name]
    (pr-str {:id (let [tid (tempid 3)
          {tempids :tempids} @(d/transact conn [{:db/id tid :actor/name name}])]
      (resolve-tempid (db conn) tempids tid))}))

  (route/not-found "Not Found"))

(def app
  (handler/api app-routes))
