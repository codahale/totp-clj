# totp-clj

A Clojure library for implementing sever-side TOTP multi-factor authentication.

## Usage

```clojure
(require '[totp-clj.core :as totp])

;; generate a new TOTP secret
(def secret (totp/generate-secret))

;; verify a user-provided token against an existing secret
(totp/verify secret "123456")

;; generate a QRCode PNG image for the user to scan
(totp/qrcode (totp/uri "my-app" "codahale" secret))
```

## License

Copyright © 2016 Coda Hale

Distributed under the Eclipse Public License either version 1.0 or (at your
option) any later version.
