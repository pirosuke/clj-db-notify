(ns clj-db-notify.core
  (:gen-class)
  (:refer-clojure :exclude [update])
  (:require [clojure.string :as string])
  (:require [clj-chatwork-client.core :as chatwork])
  (:require [clojure-ini.core :as ini]))

(use 'korma.db)
(use 'korma.core)

(defn check-db
  [ini-settings]
  (try
    (let [db-settings (:db ini-settings)]
      (defdb db
        {:user (:dbuser db-settings)
         :password (:dbpass db-settings)
         :subname (:dburl db-settings)
         :subprotocol "postgresql"})
      (if
        (> (count (exec-raw (:check-sql db-settings) :results true)) 0) 
        (exec-raw (:notify-sql db-settings) :results true)
        [])
      )
    (catch java.sql.BatchUpdateException e (println (.getNextException e)))))

(defn prettify-table
  [rows]
  (let [col-names (string/join "," (for [k (keys (first rows))] (name k)))]
    (str col-names "\n" (string/join "\n" (for [row rows] (string/join "," (vals row)))))))

(defn proceed-ini
  [ini-settings]
  (let [db-result (check-db ini-settings)
        cw-settings (:chatwork ini-settings)]
    (cond
      (> (count db-result) 0)
      (chatwork/post-chatwork (str (:title cw-settings) "\n\n" (prettify-table db-result))
                              (:room-id cw-settings) 
                              (:api-key cw-settings)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [ini-path (first args)
        ini-settings (ini/read-ini ini-path 
                                   :keywordize? true 
                                   :comment-char \#)]
    (proceed-ini ini-settings)))

