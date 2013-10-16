if [ ! -e sitemap.xml ]
then
	wget gizmodo.com/sitemap.xml
fi

cat sitemap.xml | grep -o '\[http[^]]*\]' | tr '[]' ' ' > intervals.txt;
mkdir all_links
cd all_links
wget -i ../intervals.txt
cat * | grep -o '\[http[^]]*\]' | tr '[]' ' ' > ../links.txt

