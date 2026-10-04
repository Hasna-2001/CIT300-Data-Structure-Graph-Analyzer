@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -d out -sourcepath src src\Main.java src\test\TestRunner.java
if errorlevel 1 (
  echo Compilation failed. Make sure the JDK is installed and javac is on your PATH.
  pause
  exit /b 1
)
java -cp out Main
pause
