#!/usr/bin/env sh

if [ $TRAVIS_TEST_RESULT -eq 0 ];
then
	.travis/deploy.sh
fi
