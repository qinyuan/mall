#!/bin/bash


dir=/opt/tomcat/apache-tomcat-7.0.27/webapps/mall/resources
cd $dir
compilerPath=/home/qinyuan/program/javascript/compiler.jar
compressorPath=/home/qinyuan/program/javascript/yuicompressor-2.4.6.jar
jspCompressorPath=/home/qinyuan/program/java/mall/compressJsp

######################### compress JS ##################

compress() { 
    echo "start to compress $1"
    #java -jar $compilerPath --js $dir/$1 --js_output_file $dir/$1.compress
    #mv -f $dir/$1.compress $dir/$1
    echo "compress $1 successfully"
    echo
}

for f in `find -name '*.js'`; do
    if [[ ! $f == ./js/lib/* ]]; then
        compress $f
    fi
done

compress js/lib/linecharts/linecharts.js
compress js/lib/jscolor/jscolor.js
compress js/lib/jsutils.js
compress js/lib/ie-patch.js
compress js/lib/jquery.cookie.js
compress js/lib/jquery.url.js
compress js/lib/jquery-form-3.51.0.js


######################### compress CSS ##################
compressCSS() { 
    echo "start to compress $1"
    #java -jar $compressorPath --type css --charset utf-8 -v $dir/$1 > $dir/$1.compress
    #mv -f $dir/$1.compress $dir/$1
    echo "compress $1 successfully"
    echo
}

for f in `find -name '*.css'`; do
    compressCSS $f
done


compressJSP() {
    echo "start to compress $1"
    $jspCompressorPath $dir/$1
    echo "compress $1 successfully"
    echo
}

dir=$dir/..
cd $dir
for f in `find -name '*.jsp'`; do
    compressJSP $f
done
