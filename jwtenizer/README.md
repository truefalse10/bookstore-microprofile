I used [jwtenizer](https://github.com/AdamBien/jwtenizr) to create a public key and an example token that can be used for thesting. Just use `curl -i -H'Authorization: Bearer {token.jwt}' http://localhost:8080/bookstore-microprofile/resource/ping`