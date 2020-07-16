#!/bin/bash
PROJECTS=" bookstore configuration-service eureka-server reading zuul-api-gateway"
WORKING_DIR=$PWD
for PRO in $PROJECTS; do
	cd $PRO
	echo "Building $PRO"
	sleep 1
	mvn -DskipTests clean package
	cd $WORKING_DIR
done

