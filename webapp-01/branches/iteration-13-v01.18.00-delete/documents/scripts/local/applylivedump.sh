echo NOTE! ----- This script applies an existing live dump.  It does not transfer it from the live server
echo NOTE! ----- To ftp the live dump, use getlivedumpfromserver.sh

# Need to stop Tomcat before applying dump or locks on database will be taken.
# So stop and then restart afterwards...

echo 'avoidance' | sudo -S /usr/local/tomcat/bin/catalina.sh stop

# Now apply the dump to the development database
/usr/local/mysql/bin/mysql -u root -pOC7AaWC77B lda_v02\(live\) < /Users/thomassecondary/Projects/webapp-01\(trunk\)/Documents/sqldump/lda_v02.sql

# shouldn't need to re-enter password for restart of tomcat
echo 'avoidance' | sudo -S /usr/local/tomcat/bin/catalina.sh start