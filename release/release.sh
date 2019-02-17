#!/usr/bin/env sh

cd ..

sed -i 's/-SNAPSHOT//' gradle.properties

getProperty() {
   PROP_KEY=$1
   CURRENT_VERSION=`cat gradle.properties | grep "$PROP_KEY" | cut -d '=' -f 2`
   MAJOR_VERSION=`echo "$CURRENT_VERSION" | cut -d "." -f 1`
   MINOR_VERSION=`echo "$CURRENT_VERSION" | cut -d "." -f 2`
}

getProperty "version"

if [ -z $1 ]
then
    NEXT_DEVELOP_VERSION=${MAJOR_VERSION}.`echo "$MINOR_VERSION + 1" | bc`
elif [ $1 = "major" ]
then
    NEXT_DEVELOP_VERSION=`echo "$MAJOR_VERSION + 1" | bc`".0"
else
    NEXT_DEVELOP_VERSION=${MAJOR_VERSION}.`echo "$MINOR_VERSION + 1" | bc`
fi

NEXT_DEVELOP_VERSION="$NEXT_DEVELOP_VERSION-SNAPSHOT"
echo "Release version: $CURRENT_VERSION"
echo "Next develop version: $NEXT_DEVELOP_VERSION"

git reset --hard HEAD
git checkout develop
git pull

sed -i "s/version=.*/version=$CURRENT_VERSION/" gradle.properties
sed -i "s/TASK_MANAGER_VERSION=.*/version=$CURRENT_VERSION/" environment/prod/.env
git add gradle.properties .env
git commit -m "[Release version: $CURRENT_VERSION]"
git tag -a -m "[Release tag: $CURRENT_VERSION]" ${CURRENT_VERSION}

sh gradlew clean bootDistTar -x test
docker build -t task-manager:${CURRENT_VERSION} backend

sed -i "s/version=.*/version=${NEXT_DEVELOP_VERSION}/" gradle.properties
git add gradle.properties
git commit -m "[Next develop version: $NEXT_DEVELOP_VERSION]"

git push --tags

git checkout master
git pull
git merge --ff-only ${CURRENT_VERSION}
git push