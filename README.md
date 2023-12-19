# strimzi

```
minikube start --cpus=2 --memory=5000 --driver=docker
```

```
kubectl label namespace kafka istio-injection=enabled
```

```
kubectl create namespace kafka        
```


## Apply the Strimzi install files
```
kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka
```

## Follow the deployment of the Strimzi cluster operator
```
kubectl get pod -n kafka --watch
```

## You can also follow the operator’s log
```
kubectl logs deployment/strimzi-cluster-operator -n kafka -f
```

## Create an Apache Kafka cluster

Create a new Kafka custom resource to get a small persistent Apache Kafka Cluster with one node for Apache Zookeeper and Apache Kafka:
```
kubectl apply -f https://strimzi.io/examples/latest/kafka/kafka-persistent-single.yaml -n kafka
```

Wait while Kubernetes starts the required pods, services, and so on:
```
kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka
```

```
kubectl apply -f servicemesh.yaml     
```

## Send and receive messages
With the cluster running, run a simple producer to send messages to a Kafka topic (the topic is automatically created):
```
kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.38.0-kafka-3.6.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic
```
Once everything is set up correctly, you’ll see a prompt where you can type in your messages.

And to receive them in a different terminal, run:
```
kubectl -n kafka run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.38.0-kafka-3.6.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic --from-beginning
```

```
kubectl get pods -n=kafka
```

```
kubectl get services -n=kafka
```

```
kubectl delete pod kafka-producer -n=kafka       
```

```
kubectl delete pod kafka-consumer   -n=kafka
```

# Deleting your Apache Kafka cluster
```
kubectl -n kafka delete $(kubectl get strimzi -o name -n kafka)
```

# Deleting the Strimzi cluster operator
```
kubectl -n kafka delete -f 'https://strimzi.io/install/latest?namespace=kafka'
```
