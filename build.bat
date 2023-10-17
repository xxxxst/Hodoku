
@echo off

if not exist "out" (
	mkdir out\
)

call jar -cfm out/HodokuCN.jar manifest.mf -C bin/ .

set "exe=0"
for /F %%i in ('where jsmoothcmd.exe') do (set exe=%%i)
if exist %exe% (
	call jsmoothcmd.exe "vbuild\HodokuCN.exe.jsmooth"
)
