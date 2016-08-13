# Need to stop Tomcat before applying dump or locks on database will be taken.
# So stop and then restart afterwards...

echo 'avoidance' | sudo -S /usr/local/tomcat/bin/catalina.sh stop
sleep 2

# Now apply the dump to the development database
mysql -u root -pOC7AaWC77B lda_v02 < /Users/thomassecondary/Projects/webapp-01\(trunk\)/documents/sqldump/lda_v02\(dev\).sql

# shouldn't need to re-enter password for restart of tomcat
echo 'avoidance' | sudo -S /usr/local/tomcat/bin/catalina.sh start
