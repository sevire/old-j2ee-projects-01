#!/bin/bash -vx
#########################################################################################
# Uses ftp to transfer script to directory on remote server.  The directory will be
# relative to the ftp_in folder which is where all content should be # transferred to.
# It will then be moved as appropriate into the right place within the webapp.
#
# $1 : file to transfer (wildcards allowed)
# $2 : folder under ftp_in folder to place file. Must already exist
#########################################################################################

#########################################################################################
# Common script pre-amble
#########################################################################################
set -o pipefail
set -o errexit
# set -o xtrace

__DIR__="$(cd "$(dirname "${0}")"; echo $(pwd))"
__BASE__="$(basename "${0}")"
__FILE__="${__DIR__}/${__BASE__}"

source ${__DIR__}/set-constants.sh

__WEBAPP__=$webappdir/lda##$war_version

#########################################################################################
# End Of Common script pre-amble
#########################################################################################

HOST=lucifersdarkangel.co.uk  #This is the FTP server's host or IP address. 
USER=thomas                   #This is the FTP user that has access to the server. 
PASS=q1w2e3r4t5y6             #This is the password for the FTP user. 

# Can use full path of source file, but need to specify name of destination file
# without path, or ftp will try to find source path on destination server.  So parse
# out name of file before calling ftp.

basefile="$(basename "${1}")"

# ftp command with the -inv switches.
# -i turns off interactive prompting. 
# -n Restrains FTP from attempting the auto-login feature.
# -v enables verbose and progress.  

ftp -inv $HOST << EOF 

user $USER $PASS 
cd "$ftpindir"/$2

put "$1" "$basefile"

bye 
EOF  