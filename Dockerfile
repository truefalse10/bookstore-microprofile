FROM payara/micro 
COPY ./target/bookstore-microprofile.war ${DEPLOY_DIR}