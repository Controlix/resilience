# Adminserver

Simpele spring-boot admin server
Omdat de spring-security gebruiken, moeten we ook spring-boot-starter-web toevoegen.

> mvn clean install
> 
> mvn k8s:build k8s:resource
> 
> mvn k8s:deploy

> kubectl get pods
> 
> kubectl get svc

Nu opstarten, dan zie je dat spring-boot adminserver de kubernetes service discovered heeft en als foutief aanduidt omdat deze service niet de juiste endpoints aanbiedt.
Daarom moeten we een filter toevoegen mbv `bootstrap.yml`. **Dit blijkt niet te werken.**

We zie wel al dat het `/actuator/info` endpoint de uitgebreidde info aanbiedt mbt kubernetes
```
{
  "kubernetes": {
    "nodeName": "minikube",
    "podIp": "172.18.0.5",
    "hostIp": "172.17.0.3",
    "namespace": "default",
    "podName": "adminserver-58c9448776-rvncx",
    "serviceAccount": "default",
    "inside": true
  }
}
```

# Slowing-svc
elke keer je de `/message` endpoint aanroept, wordt de call 100ms trager (na 10x wordt dit teruggezet)

# Gateway
routeer naar de slowing-svc
maakt gebruik van
- feign
- kubernetes-loadbalancer
- kubernetes-servicediscovery



