echo "start"

servicesName=$HOSTNAME

if [[ -z "$servicesName" ]]; then
  echo "No Environment Services Name set for ENV Variable ENV_SERVICES_NAME"
  exit 1
else
  echo "Environment Services Folder Name is: $servicesName"
fi

composeFilePath="/etc/envman/orchestration/$servicesName/"

echo "Checking file exists at $composeFilePath"

if [[ -f "$composeFilePath" ]]; then
  echo "Docker comopse found for $servicesName."
else
  echo "docker-compose.yml found for $servicesName."
fi

composeDir="${composeFilePath%/*}"

cd composeDir

docker-compose up