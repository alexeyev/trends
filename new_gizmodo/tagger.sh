# to be put into corpus directory
for f in *; do echo $f;  ../../ner-tool/ner.sh $f > "../tagged/$f" ; printf "\n\n" ;  done;
