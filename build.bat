
@echo off

if not exist "out" (
	mkdir out\
)

call jar -cfm out/HodokuCN.jar MANIFEST.MF -C bin/ .

set "exe=0"
for /F %%i in ('where launch4jc.exe') do (set exe=%%i)
if exist %exe% (
	call launch4jc.exe "launch4j\hodoku.launch4j.xml"
)
