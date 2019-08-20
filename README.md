# Build
mvn clean package && docker build -t com.truefalse/bookstore-microprofile .

# RUN

docker rm -f bookstore-microprofile || true && docker run -d -p 8080:8080 -p 4848:4848 --name bookstore-microprofile com.truefalse/bookstore-microprofile 