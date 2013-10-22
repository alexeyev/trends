#!/bin/bash
# code is dirty and hacky
# but this is one-timer, isn't it?

function is_blank {
	res=$(curl $1 | grep -c '<title>Page not found')
	#echo "$res page-not-found matches found!\n\n\n"
	if [ $res -eq 1 ]
		then echo "yep" 
		else echo "nope" 
	fi	
}


#if [[ $(is_blank $1) == 'yep' ]]
#	then echo 'Pustota'
#elif [[ $(is_blank $1) == 'nope' ]]
#	then echo 'Chapayev'
#else echo 'Clay machine gun'
#fi

current_date=$(date)
> links.txt

while :
do
	formatted=$(date -d "$current_date" +%Y/%m/%d)
	current_date=$(date +%Y/%m/%d  -d "$formatted 1 days ago")
	page=1
	url="http://techcrunch.com/$current_date/page/$page/"
	while [[ $(is_blank $url) == 'nope' ]]
	do
		curl $url | grep -o 'href=[^<>]*data-omni-sm="gbl_river_headline"' | sed -r 's/.*(http[^"]*)".*/\1/g' >> links.txt
		echo "DONE WITH: $current_date, page $page, url: $url"
		page=$(($page+1))
		url="http://techcrunch.com/$current_date/page/$page/"
		echo "TRYING: $current_date, page $page, url: $url"
	done
done

