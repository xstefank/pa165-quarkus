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

if (!(podman image ls | Select-String -Pattern "pa165-seminar-service")) {
    notify_result "Podman image <userId>/pa165-seminar-service in podman found" 1
} else {
    notify_result "Podman image <userId>/pa165-seminar-service in podman found" 0
}
