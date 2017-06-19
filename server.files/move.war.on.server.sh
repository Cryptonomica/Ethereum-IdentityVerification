#!/usr/bin/env bash

# this is to be executed on the server (does not need to be executable on local )
# see: http://stackoverflow.com/questions/305035/how-to-use-ssh-to-run-shell-script-on-a-remote-machine
sudo service tomcat8 stop
sudo rm -rf /var/lib/tomcat8/webapps/ROOT /var/lib/tomcat8/webapps/ROOT.war
sudo cp -vu /home/ubuntu/toDeploy/*.war /var/lib/tomcat8/webapps/ROOT.war
# '/home/ubuntu/toDeploy/ROOT.war' -> '/var/lib/tomcat7/webapps/ROOT.war'

sudo service tomcat8 start
#sudo service tomcat8 status
#sudo service nginx start
#sudo service nginx status
touch /home/ubuntu/deployment.log
echo $(date "+%FT%T%Z") : $(whoami)  >> /home/ubuntu/deployment.log
echo "---------------------"  >> /home/ubuntu/deployment.log
tail /home/ubuntu/deployment.log