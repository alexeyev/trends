#!/bin/bash

# a bit hacky processor for gizmodo pages

counter=0
for f in $1/*
do
if [ ! -d $f ]; then
	counter=$(($counter+1));	

# removing newlines
# removing sequences of whiltespaces
# getting title, date and post content with trash
# removing trash
# removing all tags

	cat "$f" | tr "\n" "~" | \
		sed -r "s/\s+/ /g" | \
		sed -r "s/.*<title>([^<]*)<\/title>.*class=\"show-on-hover\">([^<]*)<\/span>.*post-content\">(.*)/\1\n\2\n\3/g" | \
		sed -r "s/<script.*//g" | \
		sed "s/<[^>]*>/ /g" > "$2/$counter"
	printf "\n";
fi
done
