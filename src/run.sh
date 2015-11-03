#!/bin/bash
echo -e "\033[31mDFA:\033[0m"
javac DFA.java
java DFA
echo -e "\033[31mNFA:\033[0m"
javac NFA.java
java NFA
rm -rf *.class
