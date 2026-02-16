function notify_result {
    param (
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
    param (
        [string]$MESSAGE,
        [int]$PORT,
        [string]$REST_PATH
    )

    Invoke-WebRequest "http://localhost:$PORT/$REST_PATH" -UseBasicParsing > $null
    notify_result $MESSAGE $LASTEXITCODE
}

if (!(podman ps | Select-String -Pattern "pa165-seminar-service")) {
    notify_result "Podman container <userId>/pa165-seminar-service in podman ps found" 1
} else {
    notify_result "Podman container <userId>/pa165-seminar-service in podman ps found" 0
}

if (!(podman ps | Select-String -Pattern "pa165-seminar-service" | Select-String -Pattern "0.0.0.0:8080->8080/tcp")) {
    notify_result "Podman container mapped on port 8080" 1
} else {
    notify_result "Podman container mapped on port 8080" 0
}

if (!(podman ps | Select-String -Pattern "pa165-seminar-service" | Select-String -Pattern "0.0.0.0:8081->8080/tcp")) {
    notify_result "Podman container mapped on port 8081" 1
} else {
    notify_result "Podman container mapped on port 8081" 0
}

verify_curl "8080 whoami" 8080 whoami
verify_curl "8080 whereami" 8080 whereami
verify_curl "8081 whoami" 8081 whoami
verify_curl "8081 whereami" 8081 whereami
