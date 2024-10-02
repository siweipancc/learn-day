$DebugPreference
#$DebugPreference = 'continue'
$DebugPreference = [System.Management.Automation.ActionPreference]::Continue

$sb =  {
    [Threading.Thread]::GetDomain().GetAssemblies() | Select-Object {Split-Path ($_.Location) -Leaf }
}
$sb.Invoke()| Write-Verbose
Write-Debug 'begin with debug'

@{'VerbosePreference'=$VerbosePreference}
$VerbosePreference = [System.Management.Automation.ActionPreference]::Continue
$sb.Invoke()| Write-Verbose

@{'ErrorActionPreference'=$ErrorActionPreference}
$ErrorActionPreference = [System.Management.Automation.ActionPreference]::SilentlyContinue
Write-Error 'you can not see me!'
try{
    $i = 2/0
}catch{
    $ErrorActionPreference = [System.Management.Automation.ActionPreference]::Continue
    Write-Error $_.Exception.Message
}
$null -eq $i
Get-Item 'Variable:\i'



Read-Host -Prompt 'Press Enter to exit'