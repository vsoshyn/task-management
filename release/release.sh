#!/usr/bin/env sh

cd ..

sed -i 's/-SNAPSHOT//' gradle.properties

getProperty() {
   PROP_KEY=$1
   VERSION=`cat gradle.properties | grep "$PROP_KEY" | cut -d '=' -f 2`
}

getProperty "version"
MAJOR_VERSION=`echo "$VERSION" | cut -d "." -f 1`
MINOR_VERSION=`echo "$VERSION" | cut -d "." -f 2`

NEXT_VERSION=`echo "$VERSION + 1" | bc`
NEXT_PATCH_VERSION=$MAJOR_VERSION.`echo "$MINOR_VERSION + 1" | bc`

echo "current-version = $VERSION; next-version = $NEXT_VERSION; next-patch-version = $NEXT_PATCH_VERSION"

if [ -z $1 ]
then
    NEXT_DEVELOP_VERSION=$NEXT_PATCH_VERSION"-SNAPSHOT"
elif [ $1 = "major" ]
then
    NEXT_DEVELOP_VERSION=$NEXT_VERSION"-SNAPSHOT"
else
    NEXT_DEVELOP_VERSION=$NEXT_PATCH_VERSION"-SNAPSHOT"
fi

echo "next-develop-version = $NEXT_DEVELOP_VERSION"

sed -i "s/version=.*/version=${NEXT_DEVELOP_VERSION}/" gradle.properties

#git commit -m '[Release: prepared release ()]'
#git tag -m '[Release: set tag with version]'