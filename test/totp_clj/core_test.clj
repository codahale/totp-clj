(ns totp-clj.core-test
  (:require [clojure.test :refer :all]
            [totp-clj.core :refer :all]))

(deftest generate-secrets-test
  (is (= 1000 (count (set (for [_ (range 0 1000)]
                            (let [secret (generate-secret)]
                              (is (re-matches #"[A-Z0-9]{52}" secret))
                              secret)))))))

(deftest uri-test
  (let [secret (generate-secret)
        uri (uri "example" "user" secret)]
    (is (= (str "otpauth://totp/example%3Auser?secret=" secret) uri))))

(deftest qrcode-test
  (is (some? (qrcode (uri "example" "user" (generate-secret))))))

(deftest verify-test
  (is (not (verify (generate-secret) "123456"))))
