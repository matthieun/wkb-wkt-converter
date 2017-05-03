#!/usr/bin/env sh

GITHUB_REPO="matthieun/wkb-wkt-converter"
MERGE_BRANCH=master

echo "merge-dev-to-master: $GITHUB_REPO"

if [ "$TRAVIS_BRANCH" != "dev" ]; then
	echo "merge-dev-to-master: Exiting! Branch is not dev: $TRAVIS_BRANCH"
    exit 0;
fi

export GIT_COMMITTER_EMAIL="travis@travis.org"
export GIT_COMMITTER_NAME="Travis CI"

TEMPORARY_REPOSITORY=$(mktemp -d)
git clone "https://github.com/$GITHUB_REPO" "$TEMPORARY_REPOSITORY"
cd $TEMPORARY_REPOSITORY

echo "Checking out $MERGE_BRANCH"
git checkout $MERGE_BRANCH

echo "Merging $TRAVIS_COMMIT into $MERGE_BRANCH"
git merge --ff-only "$TRAVIS_COMMIT"

echo "Pushing to $GITHUB_REPO"

# Redirect to /dev/null to avoid secret leakage
git push "https://$GITHUB_SECRET_TOKEN@github.com/$GITHUB_REPO" $MERGE_BRANCH > /dev/null 2>&1
