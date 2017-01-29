#!/bin/bash

######################################################
# Common script pre-amble
######################################################
set -o pipefail
set -o errexit
#set -o xtrace

__DIR__="$(cd "$(dirname "${0}")"; echo $(pwd))"
__BASE__="$(basename "${0}")"
__FILE__="${__DIR__}/${__BASE__}"

source ${__DIR__}/set-constants.sh

__WEBAPP__=$webappdir/lda##$war_version

######################################################
# End Of Common script pre-amble
######################################################

cp "$project_local_scripts_dir"/* "$common_scripts_dir"
cp "$project_common_scripts_dir"/* "$common_scripts_dir"
chmod +x "$common_scripts_dir"/*
