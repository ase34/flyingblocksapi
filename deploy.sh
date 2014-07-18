#!/bin/bash

# git settings
git config --global user.email "asehrm34@gmail.com"
git config --global user.name "ase34"

# clone ase34.github.io
git clone --depth 1 --quiet https://${OAUTH_TOKEN}@github.com/ase34/ase34.github.io.git target/github-site

# deploy to Maven repository
mvn deploy -DaltDeploymentRepository=id::default::file:target/github-site/maven-repo
cd target/github-site/maven-repo
node ../generate-index.js
cd ..

# push all changes to ase34.github.io
git add *
git status
git commit -a -m "Added files for flyingblocks (commit ${TRAVIS_COMMIT})"
git push --quiet
cd ../..

# retrieve Maven version number and generate javadoc
PROJECT_VERSION=`cd plugin && mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version -B | grep -w '^[0-9].*$'`
echo "PROJECT_VERSION = ${PROJECT_VERSION}"
mvn javadoc:aggregate

# clone gh-pages branch, copy files and generate index
git clone --branch gh-pages --depth 1 --quiet https://${OAUTH_TOKEN}@github.com/ase34/flyingblocksapi.git target/gh-pages
cd target/gh-pages

rm -r javadocs/latest
cp -r ../site/apidocs/ javadocs/latest
cp -r ../site/apidocs/ javadocs/v${PROJECT_VERSION}@${TRAVIS_COMMIT}

rm javadocs/index.html
echo '<ul>' >> index.html.temp
ls javadocs | sed 's/^.*/<li><a href="&">&<\/a><\/li>/' >> index.html.temp
echo '</ul>' >> index.html.temp
cp index.html.temp javadocs/index.html
rm index.html.temp

# push all changes to flyingblocksapi
git add *
git status
git commit -a -m "Added javadoc for commit ${TRAVIS_COMMIT}"
git push --quiet
git checkout master
