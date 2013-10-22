year=2013
month=10
day=31


function is_blank {
	res=$(curl $1 | grep -c '<title>Page not found')
#	echo "$res page-not-found matches found!"
	if [ $res -eq 1 ]
		then echo "yep" 
		else echo "nope" 
	fi	
}

if [ $(is_blank $1) == 'yep' ]
	then echo 'Pustota'
	else echo 'Chapayev'
fi
