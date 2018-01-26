#!/usr/bin/env bash

# by default: http://localhost:8080/

mvn clean
mvn install

sudo service tomcat8 stop
sudo rm -f ./target/test.war
sudo rm -rf /var/lib/tomcat8/webapps/test
sudo mv ./target/ROOT.war ./target/test.war
sudo cp -vu ./target/test.war /var/lib/tomcat8/webapps/

sudo service tomcat8 start
#sudo service tomcat8 status

