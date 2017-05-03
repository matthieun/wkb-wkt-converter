#!/usr/bin/env sh

GITHUB_REPO="matthieun/wkb-wkt-converter"
MERGE_BRANCH=master
SOIURCE_BRANCH=dev

echo "merge-dev-to-master: $GITHUB_REPO"
echo "merge-dev-to-master: TRAVIS_BRANCH = $TRAVIS_BRANCH"
echo "merge-dev-to-master: TRAVIS_PULL_REQUEST = $TRAVIS_PULL_REQUEST"

if [ "$TRAVIS_BRANCH" != "dev" ] || [ "$TRAVIS_PULL_REQUEST" != "false" ];
then
	echo "merge-dev-to-master: Exiting! Branch is not dev: $TRAVIS_BRANCH or this is a Pull Request: $TRAVIS_PULL_REQUEST"
    exit 0;
fi

export GIT_COMMITTER_EMAIL="travis@travis.org"
export GIT_COMMITTER_NAME="Travis CI"

TEMPORARY_REPOSITORY=$(mktemp -d)
git clone "https://github.com/$GITHUB_REPO" "$TEMPORARY_REPOSITORY"
cd $TEMPORARY_REPOSITORY

echo "Checking out $SOURCE_BRANCH"
git checkout $SOURCE_BRANCH

echo "Checking out $MERGE_BRANCH"
git checkout $MERGE_BRANCH

echo "Merging $SOURCE_BRANCH into $MERGE_BRANCH"
git merge --ff-only "$SOURCE_BRANCH"

echo "Pushing to $GITHUB_REPO"

# Redirect to /dev/null to avoid secret leakage
git push "https://$GITHUB_SECRET_TOKEN@github.com/$GITHUB_REPO" $MERGE_BRANCH > /dev/null 2>&1
