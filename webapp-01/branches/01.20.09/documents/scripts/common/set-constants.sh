# Intended to be included (sourced) into other scripts so export not required (I think).

war_version=01.16.01
ssh_password=OC7AaWC77B
webappdir=/var/tomcat7/webapps

# Relative Directory Paths

banners=site_images/link_banners
gallery=gallery

# Local Directories Within Project Structure

project_name='webapp-01'
projects_dir="/Users/thomassecondary/Projects"
project_dir="$projects_dir"/"$project_name"
project_web_dir="$project_dir"/web
project_script_dir="$project_dir"/documents/scripts
project_local_scripts_dir="$project_script_dir"/local
project_remote_scripts_dir="$project_script_dir"/remote
project_common_scripts_dir="$project_script_dir"/common
project_live_sql_backup_dir="$project_dir"/documents/sqldump

# Local Content Directories Within Project Structure

project_banner_dir="$project_web_dir"/"$banners"
project_gallery_dir="$project_web_dir"/"$gallery"

# Local Directories Outside Project Structure

common_scripts_dir=/Users/shared/common-scripts
#common_scripts_common_dir="$common_scripts_dir"
#common_scripts_local_dir="$common_scripts_dir"/"$local"

# Remote ftp directories

ftpindir=/home/thomas/ftp_in
ftp_link_banner_dir="$ftpindir"/banners
ftp_gallery_dir="$ftpindir"/gallery
ftp_command_dir=commands
ftp_sql_backup_dir=/home/thomas/backup

# Remote target directories

remote_shared_commands_dir=/usr/local/bin

# Remote Filenames

remote_sql_dump_file=lda_v02.sql
