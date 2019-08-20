FROM airhacks/glassfish
COPY ./target/bookstore-microprofile.war ${DEPLOYMENT_DIR}
