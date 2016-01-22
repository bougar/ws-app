#!/bin/bash
JAVA=/opt/jdk1.8.0_05/bin/java
JAR=./hola.jar
OPERATIONS=./operaciones
if [ ! -f $OPERATIONS ]; then
	echo "File $OPERATIONS not found.";
	exit;
fi

if [ ! -s $OPERATIONS ]; then
	echo "File $OPERATIONS is empty";
	exit;
fi
oldIFS=$IFS 
IFS=$'\n'
for ARG in $(cat $OPERATIONS);do
	echo $ARG
	i=0;
	eval ARGS=($ARG);
	$JAVA -jar $JAR ${ARGS[@]};
	if [ $# -gt 0 ]; then
		if [ $1 = "wait" ]; then	
			echo -e "\nPress a key to continue...";
			read;
		fi
	fi
done
IFS=$oldIFS;
