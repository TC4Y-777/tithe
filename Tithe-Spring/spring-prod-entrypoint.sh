#!/bin/bash

./gradlew build

java -jar ./build/libs/Tithe-Spring-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
