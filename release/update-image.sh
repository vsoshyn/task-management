#!/usr/bin/env sh

cd ..

RELEASE_VERSION=`echo "##teamcity[getParameter name='env.RELEASE_VERSION']"`

docker build -q -t task-manager:${RELEASE_VERSION} backend

sed -i "s/version=.*/version=${NEXT_DEVELOP_VERSION}/" gradle.properties
git add gradle.properties
git commit -q -m "[Next develop version: $NEXT_DEVELOP_VERSION]"
echo "Committed next develop version: develop"

git push --tags
echo "Published to git: develop"

git checkout -q master
echo "Checked out: master"

git pull -q
git merge -q --ff-only ${CURRENT_VERSION}
echo "Merged: develop -> master"

git push
echo "Published to git: develop"
