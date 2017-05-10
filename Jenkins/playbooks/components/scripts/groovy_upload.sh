#!/bin/bash

groovy_script="$1"

# Wait till jenkins Up and Running
while [ $(curl -sL -w "%{http_code}" localhost -o /dev/null) != "200" ]; 
do 
    sleep 1
done

# Upload and Execute Groovy
curl -s --data-urlencode "script=$(<${groovy_script})" http://localhost/scriptText && touch $2
rm -f ${groovy_script}