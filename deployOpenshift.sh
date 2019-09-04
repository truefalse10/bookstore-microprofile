#!/bin/bash

# project variables
ARTIFACT_ID=bookstore-microprofile
IMAGE_NAME=$ARTIFACT_ID
REGISTRY=$(minishift openshift registry)/payara-micro/bookstore-micro

# cli fancyness
info=$(echo -e "\033[41;30m INFO \033[0m   ") # red background
done=$(echo -e "\033[42;30m DONE \033[0m   ") # green background

echo $info"mvn clean & package"
mvn clean package >/dev/null # dont print to console
echo $done"successfully built "$ARTIFACT_ID".war"
echo $info"docker build image "$IMAGE_NAME" ..."
docker build -q -t $IMAGE_NAME .
docker tag $IMAGE_NAME $REGISTRY
echo $done"successfully built docker image "$IMAGE_NAME
echo $info"pushing image to "$REGISTRY" ..."
docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)
docker push $REGISTRY
echo $done"successfully pushed image to "$REGISTRY
