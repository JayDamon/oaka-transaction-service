#!/bin/bash

image=$1;
docker_user=$2;
docker_password=$3;

version=$(git describe --tags --abbrev=0 | sed 's/v//')
major_version=$(git describe --tags --abbrev=0 | cut -f1 -d"." | sed 's/v//')
minor_version=$(git describe --tags --abbrev=0 | cut -f1-2 -d"." | sed 's/v//')

echo $version
echo $major_version
echo $minor_version

docker build -t $image:$version -t $image:$major_version -t $image:$minor_version -t $image:latest .

echo "performing docker login for $version with username: $docker_user password $docker_password"
docker login -u $docker_user -p $docker_password

docker push $image:$version
docker push $image:$major_version
docker push $image:$minor_version
docker push $image:latest