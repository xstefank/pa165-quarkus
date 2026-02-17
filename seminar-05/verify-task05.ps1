function notify_result {
  param(
    [string]$MESSAGE,
    [int]$RESULT_CODE
  )
  if ($RESULT_CODE -ne 0) {
    Write-Host "NOK $MESSAGE" -ForegroundColor Red
  } else {
    Write-Host "OK $MESSAGE" -ForegroundColor Green
  }
}

function verify_curl {
  param(
    [string]$MESSAGE,
    [int]$PORT,
    [string]$REST_PATH
  )
  $request = Invoke-WebRequest -Uri "http://localhost:$PORT/$REST_PATH" -UseBasicParsing
  if ($request.StatusCode -ne 200) {
    notify_result $MESSAGE 1
  } else {
    notify_result $MESSAGE 0
  }
}

if (podman ps | Select-String "pa165-seminar-service") {
  notify_result "Podman container <userId>/pa165-seminar-service in podman ps found" 0
} else {
  notify_result "Podman container <userId>/pa165-seminar-service in podman ps found" 1
}

if (podman ps | Select-String "pa165-test-image-quarkus") {
  notify_result "Podman container quay.io/xstefank/pa165-test-image in podman ps found" 0
} else {
  notify_result "Podman container quay.io/xstefank/pa165-test-image in podman ps found" 1
}

if (podman ps | Select-String "0.0.0.0:8080->8080/tcp" | Select-String "pa165-seminar-service") {
  notify_result "Podman container mapped on port 8080" 0
} else {
  notify_result "Podman container mapped on port 8080" 1
}

if (podman ps | Select-String "0.0.0.0:8081->8080/tcp" | Select-String "pa165-seminar-service") {
  notify_result "Podman container mapped on port 8081" 0
} else {
  notify_result "Podman container mapped on port 8081" 1
}

if (podman ps | Select-String "0.0.0.0:8082->8080/tcp" | Select-String "pa165-test-image-quarkus") {
  notify_result "Podman container mapped on port 8082" 0
} else {
  notify_result "Podman container mapped on port 8082" 1
}

verify_curl "8080 whoami" 8080 whoami
verify_curl "8080 whereami" 8080 whereami
verify_curl "8081 whoami" 8081 whoami
verify_curl "8081 whereami" 8081 whereami
verify_curl "8082 values" 8082 values

$VALUES = Invoke-WebRequest -Uri "http://localhost:8082/values" -UseBasicParsing | Select-Object -ExpandProperty Content
if ($VALUES -ne "d8a245b0b2d2c8cf42694893fe12efe240322b1b4d05231d5e47b4b68e62cb6c") {
  Write-Host "NOK Values from test-image" -ForegroundColor Red
} else {
  Write-Host "OK Values from test-image" -ForegroundColor Green
}
