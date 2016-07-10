#!/bin/bash -vx

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

__WEBAPP__=$webappdir/lda##$war_version

######################################################
# End Of Common script pre-amble
######################################################

# Call ftp script for image supplied in parameter 1

for file in "$project_remote_scripts_dir"/"$1"
do
	echo "FTPing $file to remote ftp in dir"
	ftptolda.sh "$file" "$ftp_command_dir"
done
for file in "$project_common_scripts_dir"/"$1"
do
	echo "FTPing $file to remote ftp in dir"
	ftptolda.sh "$file" "$ftp_command_dir"
done
