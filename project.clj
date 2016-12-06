(defproject com.codahale/totp-clj "0.1.0"
  :description "A library for implementing TOTP."
  :url "https://github.com/codahale/totp-clj"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.google.zxing/javase "3.3.0"]
                 [crypto-random "1.2.0"]
                 [org.jboss.aerogear/aerogear-otp-java "1.0.0"]]
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :jvm-opts ["-Djava.awt.headless=true"]
  :profiles {:dev           [:project/dev :profiles/dev]
             :test          [:project/test :profiles/test]
             :profiles/dev  {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :profiles/test {}
             :project/dev   {:source-paths ["dev"]
                             :repl-options {:init-ns user}}
             :project/test  {:dependencies []}})
