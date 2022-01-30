#Download Kafka package binary code. Better follow 2.8 version.
>https://kafka.apache.org/downloads
#Go to bin/windows and Run Zookeper first by below command
>zookeeper-server-start.bat ..\..\config\zookeeper.properties
#Go to bin/windows and Run kafka first by below command
>kafka-server-start.bat ..\..\config\server.properties
#Go to bin/windows and create topic 
>kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topicname

#Run below command to test if topic working fine

>kafka-console-producer.bat --broker-list localhost:9092 --topic topicname
>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic topicname
#Install Mongo DB or add embedded Mongo in dependency

