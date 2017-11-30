#!/usr/bin/env bash
export CONFIG_SERVICE_PASSWORD=123
export NOTIFICATION_SERVICE_PASSWORD=123
export MONGODB_PASSWORD=123
docker-compose -f cloud-deploy.yml -f cloud-deploy.dev.yml up -d