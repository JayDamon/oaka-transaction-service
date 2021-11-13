#!/bin/bash

image=$1;

version=$(git describe --tags --abbrev=0 | sed 's/v//')
major_version=$(git describe --tags --abbrev=0 | cut -f1 -d"." | sed 's/v//')
minor_version=$(git describe --tags --abbrev=0 | cut -f1-2 -d"." | sed 's/v//')

echo $version
echo $major_version
echo $minor_version

docker build -t $image:$version -t $image:$major_version -t $image:$minor_version -t $image:latest .

