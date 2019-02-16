#!/usr/bin/env sh

cd ..

sed -i 's/-SNAPSHOT//' gradle.properties

getProperty() {
   PROP_KEY=$1
   VERSION=`cat gradle.properties | grep "$PROP_KEY" | cut -d '=' -f 2`
   MAJOR_VERSION=`echo "$VERSION" | cut -d "." -f 1`
   MINOR_VERSION=`echo "$VERSION" | cut -d "." -f 2`
}

getProperty "version"

if [[ -z $1 ]]
then
    RELEASE_VERSION=${MAJOR_VERSION}.`echo "$MINOR_VERSION + 1" | bc`
elif [[ $1 = "major" ]]
then
    RELEASE_VERSION=`echo "$MAJOR_VERSION + 1" | bc`".0"
else
    RELEASE_VERSION=${MAJOR_VERSION}.`echo "$MINOR_VERSION + 1" | bc`
fi

sed -i "s/version=.*/version=$VERSION/" gradle.properties

NEXT_DEVELOP_VERSION="$RELEASE_VERSION-SNAPSHOT"
echo "Release version: $RELEASE_VERSION"
echo "Next develop version: $NEXT_DEVELOP_VERSION"

git add gradle.properties
git commit -m "[Release version: $NEXT_DEVELOP_VERSION]"
git tag -m "[Release tag: $NEXT_DEVELOP_VERSION]"

sed -i "s/version=.*/version=${NEXT_DEVELOP_VERSION}/" gradle.properties
git commit -m "[Next develop version: $NEXT_DEVELOP_VERSION]"

git push --tags

git checkout master
git merge ${NEXT_DEVELOP_VERSION}
git push
git checkout develop