#!/bin/bash -vx
#########################################################################################
# FTP named files from the banner folder in the current project structure, to the
# ftp_in banner folder on the live server, so that they can be deployed to the live
# webapp.
#
# $1 - name of banner image to transfer.  Wildcard just expands to first match.
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

# Call ftp script for image supplied in parameter 1
for file in ${1}
do
	ftptolda.sh "$project_banner_dir"/"$file" banners
done