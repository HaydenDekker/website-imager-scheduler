#!/bin/bash

echo "validate"

container_name="website-image-scheduler"
matching_containers=$(docker-compose ps -q --filter="name=${container_name}" | grep -E "^/${container_name}$")

if [[ ! -z "$matching_containers" ]]; then
  echo "Found containers with name '${container_name}':"
  docker-compose ps | grep "${container_name}"
else
  echo "No containers found with name '${container_name}'"
  exit 1
fi