(ns fractal-realms.db)

(use '[datomic.api :only [q db] :as d])

(def uri "datomic:free://localhost:4334//fractal_realms")
(d/create-database uri)
(def conn (d/connect uri))

(def schema-tx (read-string (slurp "schema/fractal-realms.dtm")))

@(d/transact conn schema-tx)

