#!/usr/bin/env bash

if [ "$#" -ne 1 ]; then
  echo "Missing arguments"
  echo "Usage: $0 <POM_VERSION>"
  exit 1
fi

NEW_VERSION=$1

WORKING_DIR=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd ${WORKING_DIR}/..

echo "Using maven version: $(mvn -v)"

mvn versions:set -DnewVersion=${NEW_VERSION} -q
echo ${NEW_VERSION} > .app-version

echo "Changed pom version to ${NEW_VERSION}"