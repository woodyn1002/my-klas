#!/bin/sh

cd $HOME
./kill-app.sh

cd $HOME/my-klas

rm /var/log/my-klas/spring.*
echo "removed spring log"

./gradlew --build-cache clean assemble

export DB_HOST=localhost
export DB_PORT=3306
export DB_DATABASE=myklas
export DB_USERNAME=root
export DB_PASSWORD=myPassw0rd
java -XX:-TieredCompilation -XX:CompileThreshold=1000 -jar build/libs/my-klas-*.jar >/dev/null 3>&1 &

ps -ef | grep my-klas
