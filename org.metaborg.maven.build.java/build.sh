#!/usr/bin/env bash

set -e
set -u


# Parse input
while getopts ":gq:a:e:" opt; do
  case $opt in
    g)
      NO_GENERATOR="true"
      ;;
    q)
      INPUT_QUALIFIER=$OPTARG
      ;;
    a)
      INPUT_MAVEN_ARGS=$OPTARG
      ;;
    e)
      INPUT_MAVEN_ENV=$OPTARG
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 2
      ;;
  esac
done


# Set build vars
QUALIFIER=${INPUT_QUALIFIER:-$(date +%Y%m%d%H%M)}

MAVEN_ARGS=${INPUT_MAVEN_ARGS:-""}
if [ -z ${INPUT_MAVEN_ENV+x} ]; then
  export MAVEN_OPTS="-Xmx512m -Xms512m -Xss16m"
else
  export MAVEN_OPTS="$INPUT_MAVEN_ENV"
fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ROOT="$DIR/../../"
GEN_LOC="$ROOT/spoofax/org.strategoxt.imp.generator/"
GEN_DIST_LOC="$GEN_LOC/dist/"


# Copy strategoxt JAR and distribution from (local) Maven repository
function maven-get {
  mvn org.apache.maven.plugins:maven-dependency-plugin:2.8:get \
    -Dartifact=$1 \
    -Ddest=$2 \
    -Dtransitive=false \
    -q \
    $MAVEN_ARGS
}

maven-get org.metaborg:strategoxt-jar:1.2.0-SNAPSHOT strategoxt.jar
rm -rf strategoxt-distrib
mkdir -p strategoxt-distrib
maven-get org.metaborg:strategoxt-distrib:1.2.0-SNAPSHOT:tar:bin strategoxt-distrib/distrib.tar
cd strategoxt-distrib
tar -xf distrib.tar
chmod a+x share/strategoxt/macosx/*
chmod a+x share/strategoxt/linux/*
rm distrib.tar
cd ..


# Build the generator
if [ -z ${NO_GENERATOR+x} ]; then
  rm -rf "$GEN_LOC/strategoxt-distrib"
  cp -r strategoxt-distrib "$GEN_LOC/strategoxt-distrib"
  cd "$GEN_LOC"
  ./build.sh
  cd "$DIR"
fi


# Copy strategoxt jar into strategoxt java backend
cp strategoxt.jar ../../strategoxt/strategoxt/stratego-libraries/java-backend/java/strategoxt.jar


# Build and install Java projects
mvn \
  -f "$DIR/pom.xml" \
  -DforceContextQualifier=$QUALIFIER \
  clean install \
  $MAVEN_ARGS


# Clean up
rm strategoxt.jar
rm -rf strategoxt-distrib