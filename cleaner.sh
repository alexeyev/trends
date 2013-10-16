#!/bin/bash

for f in $1/*
do
echo "$f"
if [ ! -d $f ]; then
	echo 'is a file!'
	hxnormalize -l 240 -x "$f" | \
		hxselect -s "\n" -c ".post-content" | \
		tr "\n" "~" | \
		sed -r "s/<script.*/\n&/g" | \
		sed -r "s/<\/script>/\n/g"|\
		sed -r "s/<script.*//g"|\
		sed "s/<[^>]*>//g" | \
		sed "s/\s\+/ /g" |\
		sed "s/~/ /g" |\
		sed -r "s/\[[^]]*\]//g"
	
fi
done
