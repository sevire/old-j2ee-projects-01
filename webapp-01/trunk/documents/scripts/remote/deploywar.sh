#/bin/bash
# This script copies a file of the form lda##???.war
# - From the ftp_in folder
# - To the webapps folder on the live server

######################################################
# Common script pre-amble
######################################################
set -o pipefail
set -o errexit
# set -o xtrace

__DIR__="$(cd "$(dirname "${0}")"; echo $(pwd))"
__BASE__="$(basename "${0}")"
__FILE__="${__DIR__}/${__BASE__}"

source ${__DIR__}/set-constants.sh

__WEBAPP__=/var/tomcat7/webapps/lda##"$war_version"

######################################################
# End Of Common script pre-amble
######################################################

mv "$ftpindir"/lda##$war_version.war "$webappdir"