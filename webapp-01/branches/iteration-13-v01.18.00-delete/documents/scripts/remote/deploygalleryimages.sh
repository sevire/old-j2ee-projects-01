#!/usr/bin/env bash
# This script should be run on the remote server.  It will move any images from the
# ftp_in folder to the appropriate gallery folder within the current version of the
# webapp.

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

if [ -z "$1" ]
then
    echo "You must supply the name of the gallery"
else
	for file in "$ftpindir"/*.{gif,jpg,png,GIF,JPG,PNG}
	do
		mv $file "$__WEBAPP__/gallery/$1"
	done
fi
