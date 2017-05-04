#!/usr/bin/env sh

if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
    ./gradlew signArchives
    ./gradlew uploadArchives
fi
