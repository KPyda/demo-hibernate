#!/bin/bash
./mvnw clean install
docker-compose -f docker-compose.yml up --build