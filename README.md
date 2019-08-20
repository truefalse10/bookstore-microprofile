# Build
```
mvn clean package && docker build -t com.truefalse/bookstore-microprofile .
```

# RUN
```
docker rm -f bookstore-microprofile || true && docker run -d -p 8080:8080 -p 4848:4848 --name bookstore-microprofile com.truefalse/bookstore-microprofile 
```

# Deploy to local Wildfly with WAD
make sure your `$M2_HOME` is set to the maven directory (I installed maven with homebrew and had to manually set it to `/Users/sedi/homebrew/Cellar/maven/3.6.0`)

```
java -jar wad.jar $WILDFLY_DIRECTORY/standalone/deployments/
```