#!/bin/bash

readonly USERNAME=$1

notify_result() {
  local MESSAGE=$1
  local RESULT_CODE=$2
  if [[ "$RESULT_CODE" != 0 ]]; then
    echo -e "\xE2\x9D\x8C NOK $MESSAGE"
  else
    echo -e "\xE2\x9C\x94 OK $MESSAGE"
  fi
}

podman image ls | grep "quay.io/$USERNAME/pa165-seminar-service" &> /dev/null
notify_result "Podman image quay.io/$USERNAME/pa165-seminar-service in podman found" $?

podman image rm "quay.io/$USERNAME/pa165-seminar-service"
notify_result "Removed the local tag of quay.io/$USERNAME/pa165-seminar-service" $?

podman pull "quay.io/$USERNAME/pa165-seminar-service"
notify_result "Pulling image from quay.io/$USERNAME/pa165-seminar-service" $?

