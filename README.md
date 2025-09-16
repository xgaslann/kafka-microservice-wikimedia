### KAFKA CLI COMMANDS

#### Start kafka server
```bash
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"  //
echo $KAFKA_CLUSTER_ID //
bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties //
bin/kafka-server-start.sh config/kraft/server.properties //
```

#### Stop kafka server
```bash
bin/kafka-server-stop.sh
```