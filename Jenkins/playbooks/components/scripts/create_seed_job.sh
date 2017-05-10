#!/bin/bash

groovy_script="$1"

# Wait till jenkins Up and Running
while [ $(curl -sL -w "%{http_code}" localhost -o /dev/null) != "200" ]; 
do 
    sleep 1
done

curl -s -XPOST 'http://localhost/createItem?name=SeedJob' --data-binary "@$(<${seed_job})" -H "Content-Type:text/xml" && touch $2
rm -f ${groovy_script}