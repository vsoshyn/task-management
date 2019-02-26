#!/usr/bin/env sh

sed -i 's/-SNAPSHOT//' gradle.properties

echo "Version type: $1"

getProperty() {
   PROP_KEY=$1
   RELEASE_VERSION=`cat gradle.properties | grep "$PROP_KEY" | cut -d '=' -f 2`
   MAJOR_VERSION=`echo "$RELEASE_VERSION" | cut -d "." -f 1`
   MINOR_VERSION=`echo "$RELEASE_VERSION" | cut -d "." -f 2`
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
echo "Release version: $RELEASE_VERSION"
echo "Next develop version: $NEXT_DEVELOP_VERSION"

echo "##teamcity[setParameter name='env.RELEASE_VERSION' value='${RELEASE_VERSION}']"
echo "##teamcity[setParameter name='env.NEXT_DEVELOP_VERSION' value='${NEXT_DEVELOP_VERSION}']"

sed -i "s/version=.*/version=$RELEASE_VERSION/" gradle.properties
sed -i "s/TASK_MANAGER_VERSION=.*/TASK_MANAGER_VERSION=$RELEASE_VERSION/" environment/prod/.env
git add gradle.properties environment/prod/.env
git commit -q -m "[Release version: $RELEASE_VERSION]"
git tag -a ${RELEASE_VERSION} -m "[Release tag: $RELEASE_VERSION]"