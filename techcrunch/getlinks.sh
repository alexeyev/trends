#!/bin/bash
# Code is dirty and hacky, but this is one-timer, isn't it?

# checking if page exists by given URL
function is_blank {
	res=$(curl $1 | grep -c '<title>Page not found')
	if [ $res -eq 1 ]
		then echo "yep" 
		else echo "nope" 
	fi	
}

current_date=$(date)

> links.txt

while :
do
	# creating URL slug '/{yyyy}/{MM}/{dd}/page/{n}/'
	formatted=$(date -d "$current_date" +%Y/%m/%d)
	current_date=$(date +%Y/%m/%d  -d "$formatted 1 days ago")
	page=1
	url="http://techcrunch.com/$current_date/"
	while [[ $(is_blank $url) == 'nope' ]]
	do
		# plain regex for extracting URLs of interest
		curl -L $url | grep -o 'href=[^<>]*data-omni-sm="gbl_river_headline"' | sed -r 's/.*(http[^"]*)".*/\1/g' >> links.txt
		echo "DONE WITH: $current_date, page $page, url: $url"
		page=$(($page+1))
		url="http://techcrunch.com/$current_date/page/$page/"
		echo "TRYING: $current_date, page $page, url: $url"
	done
done