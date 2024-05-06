echo "validate"

scriptValidate="/etc/envman/validate.sh"

echo "Checking file exists at $scriptValidate"

if [[ -f "$scriptValidate" ]]; then
  echo "Env validate script found."
  . $scriptValidate website-image-scheduler
else
  echo "Env start script not found on server. Server should be a managed env."
fi