#!/usr/bin/env bash

REPO=115056349306.dkr.ecr.us-east-1.amazonaws.com
TAG=$1

bash <(aws ecr get-login --no-include-email --region us-east-1)

export CLASSIFICATORS_MONGODB_SERVICE=classificators-mongodb
docker build -t $REPO/$CLASSIFICATORS_MONGODB_SERVICE:$TAG ./classificators-mongodb
docker push $REPO/$CLASSIFICATORS_MONGODB_SERVICE:$TAG

