#!/bin/bash
# Compile and run the Data Structure & Graph Analyzer
cd "$(dirname "$0")"
mkdir -p out
javac -d out -sourcepath src src/Main.java src/test/TestRunner.java || { echo "Compilation failed."; exit 1; }
java -cp out Main
