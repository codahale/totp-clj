(ns totp-clj.core
  "A set of functions for implementing TOTP multi-factor authentication."
  (:require [crypto.random :as r])
  (:import (java.io ByteArrayOutputStream)
           (java.nio.charset StandardCharsets)
           (com.google.zxing BarcodeFormat EncodeHintType MultiFormatWriter)
           (com.google.zxing.client.j2se MatrixToImageWriter)
           (com.google.zxing.qrcode.decoder ErrorCorrectionLevel)
           (org.jboss.aerogear.security.otp Totp)
           (org.jboss.aerogear.security.otp.api Base32 Clock)))

(defn generate-secret
  "Generates a new 256-bit TOTP secret."
  []
  (Base32/encode (r/bytes 32)))

(defn verify
  "Given a TOTP secret and token, verifies the token."
  [secret token]
  (try
    (.verify (Totp. secret) token)
    (catch Throwable _ false)))

(defn uri
  "Given a service name, username, and TOTP secret, returns a TOTP URI."
  [service username secret]
  (.uri (Totp. secret) (str service ":" username)))

(defn qrcode
  "Returns a QR Code PNG image of the user's TOTP secret as a byte array."
  [uri & {:keys [width height margin image-type]
          :or   {width      250
                 height     250
                 margin     4
                 image-type "PNG"}}]
  (let [out    (ByteArrayOutputStream.)
        writer (MultiFormatWriter.)
        hints  {EncodeHintType/CHARACTER_SET    StandardCharsets/UTF_8
                EncodeHintType/MARGIN           (int margin)
                EncodeHintType/ERROR_CORRECTION (ErrorCorrectionLevel/L)}
        matrix (.encode writer uri BarcodeFormat/QR_CODE width height hints)]
    (MatrixToImageWriter/writeToStream matrix image-type out)
    (.toByteArray out)))
