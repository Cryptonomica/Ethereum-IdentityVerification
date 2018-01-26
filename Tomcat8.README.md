
# install tomcat on ubuntu 

server update: 
```bash
sudo apt update && sudo apt upgrade && sudo apt dist-upgrade && sudo apt autoremove && sudo apt install -f
```

install tomcat8:
```bash
sudo apt install tomcat8
```

# get info on installed tomcat 

If you don't know where catalina.sh is (or it never gets called), you can usually find it via ps:

```bash
ps aux | grep catalina
```

From the ps output, you can see both *catalina.home* and *catalina.base* 

catalina.home is where the Tomcat base files are installed, 
and catalina.base is where the running configuration of Tomcat exists.

run: 

```bash
/usr/share/tomcat8/bin/version.sh 
```
output current (2017-12-06) (Ubuntu 16.04.3 LTS (GNU/Linux 4.4.0-1041-aws x86_64)): 

Using CATALINA_BASE:   /usr/share/tomcat8
Using CATALINA_HOME:   /usr/share/tomcat8
Using CATALINA_TMPDIR: /usr/share/tomcat8/temp
Using JRE_HOME:        /usr
Using CLASSPATH:       /usr/share/tomcat8/bin/bootstrap.jar:/usr/share/tomcat8/bin/tomcat-juli.jar
Server version: Apache Tomcat/8.0.32 (Ubuntu)
Server built:   Mar 9 2017 21:38:04 UTC
Server number:  8.0.32.0
OS Name:        Linux
OS Version:     4.4.0-1041-aws
Architecture:   amd64
JVM Version:    1.8.0_151-8u151-b12-0ubuntu0.16.04.2-b12
JVM Vendor:     Oracle Corporation

# tomcat8 system variables 

/usr/share/tomcat8/bin/catalina.sh : 
```bash
#   Do not set the variables in this script. Instead put them into a script
#   setenv.sh in CATALINA_BASE/bin to keep your customizations separate.
```

create file: 
```bash
sudo touch /usr/share/tomcat8/bin/setenv.sh
# see: https://unix.stackexchange.com/questions/4335/how-to-insert-text-into-a-root-owned-file-using-sudo
echo "export TEST_VAR=testEnvironmentVariableValue" | sudo tee -a /usr/share/tomcat8/bin/setenv.sh > /dev/null
```
or: 
```bash
sudo vim /usr/share/tomcat8/bin/setenv.sh
```

# logging 

logs are in /var/log/tomcat8/

```bash
ls /var/log/tomcat8/ 
cat /var/log/tomcat8/catalina.out 
cat /var/log/tomcat8/localhost_access_log.2017-12-11.txt
cat /var/log/tomcat8/localhost_access_log.$(date "+%F").txt

```
