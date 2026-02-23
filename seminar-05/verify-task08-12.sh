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
  local URL=$3
  local REST_PATH=$4
  curl "$URL/$REST_PATH" &> /dev/null
  notify_result "$MESSAGE" $?
  local OUTPUT=$(curl "$URL/$REST_PATH")
  echo $OUTPUT | grep "$OUTPUT_CONTAINS"
  notify_result "Output ($MESSAGE) contains '$OUTPUT_CONTAINS'" $?
}

readonly ROUTE_URL=$1

verify_curl "avenger-client" "Generated" "$ROUTE_URL" avenger/3
verify_curl "avenger-client" "snapped" "$ROUTE_URL" avenger/3

