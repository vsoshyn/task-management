#!/usr/bin/env sh

NEXT_DEVELOP_VERSION=`echo "##teamcity[getParameter name='env.NEXT_DEVELOP_VERSION']"`

echo "##teamcity[getParameter name='env.NEXT_DEVELOP_VERSION']"
echo "##teamcity[getParameter name='env.RELEASE_VERSION']"

sed -i "s/version=.*/version=$NEXT_DEVELOP_VERSION/" gradle.properties
sed -i "s/TASK_MANAGER_VERSION=.*/version=$NEXT_DEVELOP_VERSION/" environment/prod/.env
git add gradle.properties environment/prod/.env

git commit -q -m "[Next develop version: $NEXT_DEVELOP_VERSION]"