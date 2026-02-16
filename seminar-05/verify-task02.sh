#!/bin/bash

verify_curl () {
  local MESSAGE=$1
  local PORT=$2
  local REST_PATH=$3
  curl "http://localhost:$PORT/$REST_PATH" > /dev/null 2>&1
  if [[ $(echo $?) != 0 ]]; then
    echo -e "\xE2\x9D\x8C NOK $MESSAGE"
  else
    echo -e "\xE2\x9C\x94 OK $MESSAGE"
  fi
}

verify_curl "8080 whoami" 8080 whoami
verify_curl "8080 whereami" 8080 whereami
verify_curl "8081 whoami" 8081 whoami
verify_curl "8081 whereamii" 8081 whereami
