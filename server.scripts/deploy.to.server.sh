#!/usr/bin/env bash

mvn clean
mvn install

scp -i ${HOME}/.ssh/tomcatWeb3j.pem target/*.war ubuntu@tomcatweb3j.cryptonomica.net:/home/ubuntu/toDeploy/

# see: http://stackoverflow.com/questions/305035/how-to-use-ssh-to-run-shell-script-on-a-remote-machine
ssh -i ${HOME}/.ssh/tomcatWeb3j.pem ubuntu@tomcatweb3j.cryptonomica.net "echo $(date "+%FT%T%Z") : $(whoami)  >> /home/ubuntu/deployment.log"
# see: https://stackoverflow.com/a/1930732/1697878
ssh -i ~/.ssh/tomcatWeb3j.pem ubuntu@tomcatweb3j.cryptonomica.net sh ./move.war.on.server.sh

mvn clean
