Get-PSDrive
Get-ChildItem -Path 'Variable:' | Select-Object @{ n = 'name'; e = { $_.Name } },@{ n = 'value'; e = { $_.Value } }


$a = 2
(Get-Variable 'a').Value -eq $a
(Get-Item 'Variable:\a') -eq (Get-Variable 'a')
Remove-Item 'Variable:\a'
$a -eq $null

Get-ChildItem -Path 'Variable:' | Where-Object { $_.Name -eq 'a' } |  Measure-Object

Push-Location
Set-Location 'Variable:'
New-Item 'b' -Value 3
"the value of b: {0}" -f $b
Clear-Variable 'b'
Get-Item 'b'|Format-Table

$Host.UI.RawUI.WindowTitle |Tee-Object -Variable 'originTitle'
$Host.UI.RawUI.WindowTitle = 'continue after 2 seconds'
Start-Sleep -Seconds 2
$Host.UI.RawUI.WindowTitle = $originTitle



Read-Host -Prompt 'Press Enter to exit'


