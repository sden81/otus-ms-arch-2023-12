# HW5 - Prometheus + Grafana

## Установка
```console
minikube delete - если нужен чистый миникуб
```
```console
minikube start
```
```console
minikube addons enable ingress
```
```console
kubectl create namespace hw5
```
```console
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
```
```console
kubectl apply -f https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/master/bundle.yaml
```
```console
helm upgrade --install hw5-app -n hw5 ./hw5-chart
```
```console
kubectl port-forward service/prometheus-operated  9090
```
```console
kubectl get all -n hw5
```
```console
ab -n 500000 -c 5 http://arch.homework/api/v1/a
```
```console
ab -n 500000 -c 5 http://arch.homework/api/v1/b
```

## Удаление
```console
helm uninstall hw5-app -n hw5
```

## Тесты
```console
newman run https://api.postman.com/collections/7102499-0408f18d-64b1-46f2-9a32-7b4c4b3c99a1?access_key=PMAT-01HV232QPCA4H74F5X9RZYVFPN
```

### Полезные ресты
1. Получить юзера
```console
curl http://localhost:8081/api/v1/user/1 -I
```

2. Создать юзера
```console
curl --location --request POST 'http://localhost:8081/api/v1/user' \
--header 'Content-Type: application/json' \
--data-raw '{"firstName": "Pedro", "lastName": "Gonzales"}'
```

3. Апдейтить юзера
```console
curl --location --request PUT 'http://localhost:8081/api/v1/user/1' \
--header 'Content-Type: application/json' \
--data-raw '{"id": 1, "firstName": "Ivan", "lastName": "Pupkin"}'
```

4. Удалить юзера
```console
curl --location --request DELETE 'http://localhost:8081/api/v1/user/1'
```

## Информация для меня

### Полезные команды
1. Пробосить порт для тестов - открыть в отдельном окне
```console
kubectl port-forward -n hw4 service/hw4-app-postgresql  5439:5432
```
2. Подключиться к базе
```console
psql --host 127.0.0.1 -U postgres -d postgres -p 5439
```
3. Установка постгреса
```console
helm install pg-minikube --set auth.postgresPassword=pas bitnami/postgresql -n hw4
```
4. Посмотреть объекты
```console
kubectl get all -n hw4
```
5. Алиас для kubectl
```console
alias kubectl='minikube kubectl -- 
```
6. Залить образ в dockerhub
```console
docker login -u sden81
docker build -t hw4-app:v1.1 .
docker image ls
docker tag hw4-app:v1.1 sden81/otus-ms-arch-2023-12-hw4-img:v1.1
docker push sden81/otus-ms-arch-2023-12-hw4-img:v1.1
```
7. Консоль пода
```console
kubectl exec -it -n hw4 hw4-app-hw4-chart -- sh
```
8. Лог пода
```console
kubectl logs -n hw4 hw4-app-hw4-chart
```

### Полезные ссылки
1. https://www.bezkoder.com/spring-boot-r2dbc-postgresql/
2. https://blog.tericcabrel.com/push-docker-image-docker-hub/
3. https://github.com/schetinnikov-otus/arch-labs/tree/master/nginx-forward-auth

