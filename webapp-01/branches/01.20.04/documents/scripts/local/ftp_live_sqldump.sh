#!/bin/sh

/usr/bin/ftp -inv lucifersdarkangel.co.uk << EOF

user thomas q1w2e3r4t5y6
cd /home/thomas/backup
lcd /Users/thomassecondary/Projects/webapp-01\(trunk\)/Documents/sqldump
get lda_v02.sql
bye
EOF

# Need to stop Tomcat before applying dump or locks on database will be taken.
# So stop and then restart afterwards...

echo 'avoidance' | sudo -S /usr/local/tomcat/bin/catalina.sh stop

# Now apply the dump to the development database
/usr/local/mysql/bin/mysql -u root -pOC7AaWC77B lda_v02 < /Users/thomassecondary/Projects/webapp-01\(trunk\)/Documents/sqldump/lda_v02.sql

# shouldn't need to re-enter password for restart of tomcat
echo 'avoidance' | sudo -S /usr/local/tomcat/bin/catalina.sh start