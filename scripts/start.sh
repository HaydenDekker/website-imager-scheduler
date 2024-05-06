echo "start"

startScriptPath="/etc/envman/start.sh"

echo "Checking file exists at $startScriptPath"

if [[ -f "$startScriptPath" ]]; then
  echo "Env start script found."
  . $startScriptPath
else
  echo "Env start script not found on server. Server should be a managed env."
fi