#!/usr/bin/env sh

chmod u+x gradlew

if [ "$TRAVIS_PULL_REQUEST" != "false" ];
then
	echo "Skip integration tests in pull request builds"
	./gradlew clean build -x integrationTest
    exit 0;
fi

if [ "$TRAVIS_PULL_REQUEST" == "false" ];
then
	./gradlew clean build
    exit 0;
fi
