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

podman image ls | grep pa165-seminar-service &> /dev/null
notify_result "Podman image <userId>/pa165-seminar-service in podman found" $?

