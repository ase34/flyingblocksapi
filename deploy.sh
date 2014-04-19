#!/bin/bash
mkdir target && 
git clone https://${OAUTH_TOKEN}@github.com/ase34/ase34.github.io.git target/github-site && 
mvn deploy -DaltDeploymentRepository=id::default::file:target/github-site/maven-repo && 
cd target/github-site && 
node generate-index.js
git commit -a -m "Added files for commit ${TRAVIS_COMMIT}" && 
git push
