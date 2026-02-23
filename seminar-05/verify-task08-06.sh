#!/bin/bash

notify_result() {
  local MESSAGE=$1
  local RESULT_CODE=$2
  if [[ "$RESULT_CODE" != 0 ]]; then
    echo -e "\xE2\x9D\x8C NOK $MESSAGE"
  else
    echo -e "\xE2\x9C\x94 OK $MESSAGE"
  fi
}

verify_curl () {
  local MESSAGE=$1
  local OUTPUT_CONTAINS=$2
  local PORT=$3
  local REST_PATH=$4
  curl "http://localhost:$PORT/$REST_PATH" &> /dev/null
  notify_result "$MESSAGE" $?
  local OUTPUT=$(curl "http://localhost:$PORT/$REST_PATH")
  echo $OUTPUT | grep "$OUTPUT_CONTAINS"
  notify_result "Output ($MESSAGE) contains '$OUTPUT_CONTAINS'" $?
}

verify_curl "avenger-generator" "Generated" 8081 avenger/generate
verify_curl "avenger-client" "WRONG URL" 8080 avenger/3

