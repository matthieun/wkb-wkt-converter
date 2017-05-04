#!/usr/bin/env sh

if [ $TRAVIS_TEST_RESULT -eq 0 ];
then
	.travis/merge-dev-to-master.sh
fi
