# strimzi


minikube start --cpus=2 --memory=5000 --driver=docker

kubectl create namespace kafka        

kubectl label namespace kafka istio-injection=enabled

kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka


kubectl get pod -n kafka --watch

kubectl apply -f https://strimzi.io/examples/latest/kafka/kafka-persistent-single.yaml -n kafka


kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka

kubectl apply -f servicemesh.yaml     

kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.38.0-kafka-3.6.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic

kubectl -n kafka run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.38.0-kafka-3.6.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic --from-beginning

kubectl get pods -n=kafka


kubectl get services -n=kafka

kubectl delete pod kafka-producer -n=kafka       

kubectl delete pod kafka-consumer   -n=kafka

kubectl -n kafka delete $(kubectl get strimzi -o name -n kafka)


kubectl -n kafka delete -f 'https://strimzi.io/install/latest?namespace=kafka'

