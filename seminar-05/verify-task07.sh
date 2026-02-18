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
  local URL=$2
  local REST_PATH=$3
  curl "$URL/$REST_PATH" &> /dev/null
  notify_result "$MESSAGE" $?
}

readonly ROUTE_URL=$1

if [ $ROUTE_URL == "localhost" ] || [ $ROUTE_URL == "127.0.0.1" ] || [ $ROUTE_URL == "::1" ] || [ $ROUTE_URL == "[::1]" ]; then
  echo "Localhost is not allowed for this task"
  exit 1
fi

verify_curl "$ROUTE_URL /whoami" $ROUTE_URL whoami
verify_curl "$ROUTE_URL /whereami" $ROUTE_URL whereami

ID_NODE=$(curl $ROUTE_URL/whoami)
ID_NODE1=$(curl $ROUTE_URL/whoami)
ID_NODE2=$(curl $ROUTE_URL/whoami)
ID_NODE3=$(curl $ROUTE_URL/whoami)
ID_NODE4=$(curl $ROUTE_URL/whoami)
ID_NODE5=$(curl $ROUTE_URL/whoami)

echo "$ID_NODE"
echo "$ID_NODE1"
echo "$ID_NODE2"
echo "$ID_NODE3"
echo "$ID_NODE4"
echo "$ID_NODE5"

if [ "$ID_NODE" != "$ID_NODE1" ] || [ "$ID_NODE" != "$ID_NODE2" ] || [ "$ID_NODE" != "$ID_NODE3" ] || [ "$ID_NODE" != "$ID_NODE4" ] || [ "$ID_NODE" != "$ID_NODE5" ]; then
  notify_result "Expected that at least one of the 5 consequitive calls is returned from different pod. It may happen that it's not so rerun this test if this is the only task that fails." 0
else
  notify_result "Expected that at least one of the 5 consequitive calls is returned from different pod. It may happen that it's not so rerun this test if this is the only task that fails." 1
fi

