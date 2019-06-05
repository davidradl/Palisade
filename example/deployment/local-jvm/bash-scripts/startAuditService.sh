#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
. "$DIR/../../bash-scripts/setScriptPath.sh"

export PALISADE_REST_CONFIG_PATH="$EXAMPLE/example-model/src/main/resources/configRest.json"

java -jar "$EXAMPLESERVICES"/example-rest-audit-service/target/example-rest-audit-service-*-executable.jar \
                -httpPort=8086 \
                -extractDirectory=.extract/Audit \
                -Dpalisade.rest.basePath=audit \
                -Dpalisade.properties.app.title=rest-audit-service
