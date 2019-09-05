#!/usr/bin/env sh
set -eu #exit if something exits with nonzero or variable undeclared

# project variables
ARTIFACT_ID=bookstore-microprofile
IMAGE_NAME=$ARTIFACT_ID
REGISTRY=$(minishift openshift registry)/payara-micro/bookstore-micro

# cli fancyness
start=$(date +%s) # current timestamp
badgeInfo=$(echo "\033[41;30m INFO \033[0m") # red background
badgeDone=$(echo "\033[42;30m DONE \033[0m") # green background

echo "$badgeInfo mvn clean & package"
mvn -Dmaven.test.skip=true clean package
echo "$badgeDone successfully built $ARTIFACT_ID.war"
echo "$badgeInfo docker build image $IMAGE_NAME ..."
docker build -q -t "$IMAGE_NAME" .
docker tag "$IMAGE_NAME" "$REGISTRY"
echo "$badgeDone successfully built docker image $IMAGE_NAME"
echo "$badgeInfo pushing image to $REGISTRY ..."
docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)
docker push "$REGISTRY"
stop=$(date +%s) # current timestamp
echo "$badgeDone successfully pushed image to $REGISTRY in $(expr $stop - $start)s"