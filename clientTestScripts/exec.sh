#!/bin/bash
MAVEN=/opt/apache-maven-3.2.2/bin/mvn
CLIENTDIR=/home/bougar/workspace/ws-app/ws-app-client
MAINCLASS=es.udc.ws.app.client.ui.OfferServiceClient
OPERATIONS=/home/bougar/isd/operaciones
if [ ! -f $OPERATIONS ]; then
	echo "File $OPERATIONS not found.";
	exit;
fi

if [ ! -s $OPERATIONS ]; then
	echo "File $OPERATIONS is empty";
	exit;
fi
cd $CLIENTDIR
oldIFS=$IFS 
IFS=$'\n'
for ARG in $(cat $OPERATIONS);do
	echo $ARG
	$MAVEN exec:java -Dexec.mainClass=$MAINCLASS -Dexec.args="$ARG"
	if [ $# -gt 0 ]; then
		if [ $1 = "wait" ]; then	
			echo -e "\nPress a key to continue...";
			read;
		fi
	fi
done
IFS=$oldIFS;
