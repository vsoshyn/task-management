#!/usr/bin/env sh

echo "$NEXT_DEVELOP_VERSION"
echo "$RELEASE_VERSION"

sed -i "s/version=.*/version=$NEXT_DEVELOP_VERSION/" gradle.properties
sed -i "s/version=.*/version=$NEXT_DEVELOP_VERSION/" environment/prod/.env
git add gradle.properties environment/prod/.env

git commit -q -m "[Next develop version: $NEXT_DEVELOP_VERSION]"