#!/bin/sh

pid=`ps -ef | awk '/my-klas/ { if ($8=="java") print $2 }'`
kill -9 $pid
