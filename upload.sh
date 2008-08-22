#!/bin/bash

#script for uploading zips to upload.sf.net
#you can pass an argument to specify the folder, if not all zip files in . will be uploaded

#USAGE
#./upload.sh zips/ #uploads all zip in the zips folder
#./upload.sh #all zips in the current folder will be uploaded

###############
#checking folder
ZIP_DIR=$1

if [ -z $1 ];
then
	echo -e "\n>>>>> no folder passed as argument, assuming . as current folder"
	ZIP_DIR=.
fi


if [ -d $ZIP_DIR ];
then
	echo -e "\n>>>>> uploading all zips in $ZIP_DIR to upload.sf.net"
else
	echo -e "\n>>>>> $ZIP_DIR is not a directory"
	exit
fi

FILES=`ls $ZIP_DIR`

cd $ZIP_DIR

UPLOAD_FILES=""

#############################################
#check zips and create new upload file string
for file in $FILES; do

	result=${file##*.}

	if [ $result != "zip" ];then
		#echo -e "\n>>>>> $file is not a zip file - ignoring"
                continue
        fi

	UPLOAD_FILES="$UPLOAD_FILES $file"
done

	echo -e "\n>>>>> Starting to upload: $UPLOAD_FILES\n"


################
#uploading files
ftp -iv << EOF
  open upload.sf.net
  cd incoming
  mput $UPLOAD_FILES
EOF
