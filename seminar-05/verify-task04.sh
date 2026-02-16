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
  local PORT=$2
  local REST_PATH=$3
  curl "http://localhost:$PORT/$REST_PATH" &> /dev/null
  notify_result "$MESSAGE" $(echo $?)
}

podman ps | grep pa165-seminar-service &> /dev/null
notify_result "Podman container <userId>/pa165-seminar-service in podman ps found" $?

podman ps | grep pa165-seminar-service | grep "0.0.0.0:8080->8080/tcp" &> /dev/null
notify_result "Podman container mapped on port 8080" $?

podman ps | grep pa165-seminar-service | grep "0.0.0.0:8081->8080/tcp" &> /dev/null
notify_result "Podman container mapped on port 8081" $?

verify_curl "8080 whoami" 8080 whoami
verify_curl "8080 whereami" 8080 whereami
verify_curl "8081 whoami" 8081 whoami
verify_curl "8081 whereami" 8081 whereami

