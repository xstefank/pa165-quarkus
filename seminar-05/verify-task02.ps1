function verify_curl {
  param(
    [string]$MESSAGE,
    [int]$PORT,
    [string]$REST_PATH
  )
  $url = "http://localhost:$PORT/$REST_PATH"
  $response = Invoke-WebRequest -Uri $url -UseBasicParsing
  if ($response.StatusCode -ne 200) {
    Write-Host "NOK $MESSAGE" -ForegroundColor Red
  } else {
    Write-Host "OK $MESSAGE" -ForegroundColor Green
  }
}

verify_curl "8080 whoami" 8080 whoami
verify_curl "8080 whereami" 8080 whereami
verify_curl "8081 whoami" 8081 whoami
verify_curl "8081 whereami" 8081 whereami

