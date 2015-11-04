(defproject net.honjala/clj-db-notify "0.1.0"
  :description "App to check database and notify to Chatwork."
  :url "http://github.com/pirosuke/clj-db-notify"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clojure-ini "0.0.2"]
                 [korma "0.4.2"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.postgresql/postgresql "9.4-1203-jdbc42"]
                 [clj-time "0.11.0"]
                 [net.honjala/clj-chatwork-client "0.1.0"]]
  :main ^:skip-aot clj-db-notify.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
