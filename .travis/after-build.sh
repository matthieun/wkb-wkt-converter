#!/usr/bin/env sh

chmod u+x gradlew

if [ $TRAVIS_TEST_RESULT -eq 0 ];
then
	.travis/merge-dev-to-master.sh
fi
