# Déploiement sur Kubernetes

Pour déployer les services sur Kubernetes, utilisez les commandes suivantes :

```bash
kubectl apply -f api-gateway-deployment.yaml
kubectl apply -f patient-service-deployment.yaml
kubectl apply -f practitioner-service-deployment.yaml
kubectl apply -f medical-record-service-deployment.yaml
kubectl apply -f appointment-service-deployment.yaml
kubectl apply -f eureka-server-deployment.yaml
```

Chaque service a son propre Dockerfile.

Pour construire une image Docker :

```bash
docker build -t <nom-image> <chemin-vers-dockerfile>
```

Pour exécuter un conteneur Docker :

```bash
docker run -d -p <port-hôte>:<port-conteneur> <nom-image>
```

Dans le serveur Eureka, il y a un fichier Docker Compose qui démarre le serveur et tous les services associés.

Pour exécuter le fichier Docker Compose :

```bash
docker-compose up -d
```
