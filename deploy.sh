#!/bin/bash
git clone https://${OAUTH_TOKEN}@github.com/ase34/ase34.github.io.git target/github-site
mvn deploy -DaltDeploymentRepository=id::default::file:target/github-site/maven-repo
cd target/github-site/maven-repo
node ../generate-index.js
cd ..
git config user.email "asehrm34@gmail.com"
git config user.name "ase34"
git add *
git status
git commit -a -m "Added files for flyingblocks (commit ${TRAVIS_COMMIT})"
git push
