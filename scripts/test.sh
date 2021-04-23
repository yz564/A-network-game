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
cp -r build/reports/clover/html/* /coverage-out/ || exit 1
pkill server