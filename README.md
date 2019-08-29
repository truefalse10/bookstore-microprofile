# bookstore-microprofile
> simple java-ee project that should evaluate oracle's [microprofile](https://microprofile.io/) specification together with the thinWar approach.

### included microprofile libraries:
* [microprofile-config](https://microprofile.io/project/eclipse/microprofile-config)
* [microprofile-jwt](https://microprofile.io/project/eclipse/microprofile-jwt-auth)

### persistence:
for testing purposes this project uses an h2 database that is filled with dummy data on application start

### authentication with jwt
authentication is done via json-web-token ([JWT](https://jwt.io)). To retrieve the token the login route 
expects `name` and `password` and returns the token. This token must be sent as `Authorization: Bearer <token>`
header to access protected routes. The test credentials are `{ "name": "Dieter", "password": "123Geheim" }`

### api-endpoints:
the app exposes the following endpoints
```
GET     /bookstore-microprofile/resources/ping
GET     /bookstore-microprofile/resources/books
POST    /bookstore-microprofile/resources/books
GET     /bookstore-microprofile/resources/books/{id}

POST    /bookstore-microprofile/resources/user // Login with body { name, password }
```

## build docker container
```
mvn clean package && docker build -t com.truefalse/bookstore-microprofile .
```

## run app in glassfish container
```
docker rm -f bookstore-microprofile || true && docker run -d -p 8080:8080 -p 4848:4848 --name bookstore-microprofile com.truefalse/bookstore-microprofile 
```

## deploy app locally with live-reload
In order to have a server with live-reload this project uses a tool called [wad](https://github.com/AdamBien/wad).

If you get errors executing make sure your `$M2_HOME` is set to the maven directory (I installed maven with homebrew and had to manually set it to `/Users/sedi/homebrew/Cellar/maven/3.6.0`)

```
java -jar wad.jar $PAYARA_DIR/glassfish/domains/domain1/autodeploy
```