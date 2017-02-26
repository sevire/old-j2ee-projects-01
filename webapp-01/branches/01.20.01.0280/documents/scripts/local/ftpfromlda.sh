#!/bin/bash -vx
#########################################################################################
# General script to ftp a file from the lda server to a local folder.
# $1: Remote folder in which file is held
# $2: Name of file to transfer
# $3: Folder in which to place file
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

# ftp command with the -inv switches.
# -i turns off interactive prompting. 
# -n Restrains FTP from attempting the auto-login feature.
# -v enables verbose and progress.  

ftp -inv $HOST << EOF 

user $USER $PASS 
cd $1
lcd $3

get "$2"

bye 
EOF  