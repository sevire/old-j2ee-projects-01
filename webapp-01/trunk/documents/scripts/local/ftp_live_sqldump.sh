#!/bin/sh

/usr/bin/ftp -inv lucifersdarkangel.co.uk << EOF

user thomas q1w2e3r4t5y6
cd /home/thomas/backup
lcd /Users/thomassecondary/Projects/webapp-01\(trunk\)/Documents/sqldump
get lda_v02.sql
bye
EOF

/usr/local/mysql/bin/mysql -u root -pOC7AaWC77B lda_v02 < /Users/thomassecondary/Projects/webapp-01\(trunk\)/Documents/sqldump/lda_v02.sql