if [ ! -e ./tmp ]; then
    mkdir tmp
fi 
cd ./tmp
docker run -it -p 80:8080 --rm kazurayam/flaskr-kazurayam:1.0.3
cd -