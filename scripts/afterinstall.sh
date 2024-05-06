echo "after install"

pwd 

dirCurrent=${BASH_SOURCE%/*}

echo "Searching for jar in $dirCurrent"

jar_count=$(find $dirCurrent -type f -name "*.jar" | wc -l)

if [[ $jar_count -eq 1 ]]; then
  echo "Exactly one jar file found."
else
  if [[ $jar_count -eq 0 ]]; then
    echo "No jar files found."
  else
    echo "More than one jar file found $jar_count."
  fi
  exit 1
fi

cd $dirCurrent

cp *.jar install.jar

docker build -t website-image-scheduler .