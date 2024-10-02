do
{
    $Cur += 1
    $cur
}until($Cur -ge 10)



1..3 | ForEach-Object {
    "$( $_ ): $( $_ -xor 2 )"
}

'A'..'d' | % { @{ "$( $_ )" = ($_ -as [Int32]) } }


@('John', 'Smith').ForEach({ Write-Host -NoNewline "$_ " })
Write-Host

# https://learn.microsoft.com/en-us/dotnet/api/system.collections.hashtable.keys
# https://learn.microsoft.com/en-us/powershell/scripting/learn/deep-dives/everything-about-hashtable?view=powershell-7.4
$keys = @{ 'stree' = 'back beem'; 'phone' = '13132' }|Tee-Object -Variable 'table'| Select-Object -ExpandProperty 'Keys'
$keys | % {
    $table[$_]
}