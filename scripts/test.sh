#!/bin/bash

cd server && ./gradlew installDist || exit 1
cd ..
server/build/install/server/bin/server &
sleep 10
./gradlew build || exit 1
./gradlew cloverAggregateReports || exit 1
scripts/coverage_summary.sh
ls -l /
ls -l /coverage-out/
echo "This is before copying clover report."
cp -r build/reports/clover/html/* /coverage-out/ || exit 1
echo "Clover reports copied. Going to kill the server now."
pkill server
echo "Server killed."