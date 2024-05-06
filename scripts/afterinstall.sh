echo "after install"

jar_count=$(find . -type f -name "*.jar" | wc -l)

if [[ $jar_count -eq 1 ]]; then
  echo "Exactly one jar file found."
else
  if [[ $jar_count -eq 0 ]]; then
    echo "No jar files found."
  else
    echo "More than one jar file found."
  fi
  exit 1
fi

cp *.jar install.jar

docker build -t website-image-scheduler .