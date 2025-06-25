# 📦 Cloud-Native CI/CD Pipeline Demo

![Kubernetes](https://img.shields.io/badge/Kubernetes-%231563ff.svg?logo=kubernetes&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-%23D24939.svg?logo=jenkins&logoColor=white)

A hands-on reference implementation of a **Jenkins ➜ Docker ➜ Kubernetes** deployment pipeline with **Helm** packaging and **Prometheus + Grafana** monitoring.  
Use it to learn—or demonstrate—how modern teams ship containerised applications to a cluster with full observability.

---

## ✨ Features

| Area            | What’s Included                                                                                               |
| --------------- | -------------------------------------------------------------------------------------------------------------- |
| **CI/CD**       | Declarative `Jenkinsfile` builds & pushes Docker images, then triggers Helm upgrades                           |
| **Containers**  | Minimal `Dockerfile` for the sample Spring Boot app                                                            |
| **Helm Chart**  | `helm/vprofilecharts/` – templated Deployments, Services, Secrets, ConfigMaps                                  |
| **Raw YAML**    | `kubernetes/` – standalone manifests grouped by component (`db`, `memcache`, `tomapp`, `vpro-app`)            |
| **Monitoring**  | Helm install of Prometheus Operator + Grafana with sample dashboards                                           |
| **Run Anywhere**| Works on **Minikube**, **Kind**, or a single-node **AWS EC2** host                                             |

---

## 🖼️ Architecture

```
Developer → Git push
   │
   ▼
Jenkins (CI)
   ├─ Build & test
   ├─ Docker build & push
   └─ Helm upgrade --install
          │
          ▼
   Kubernetes cluster
   ├─ Spring Boot API
   ├─ MySQL / Memcache / RabbitMQ
   └─ Prometheus ↔ Grafana (dashboards + alerts)
```



---

## 🚀 Quick Start (local Minikube)

```bash
git clone <your-fork-url>
cd <repo>

# Start cluster & point Docker CLI to Minikube’s daemon
minikube start --driver=docker
eval $(minikube docker-env)

# Build application image locally
docker build -t vpro-app:1.0 ./src

# Install Helm chart
helm dependency update helm/vprofilecharts
helm install vprofile-demo helm/vprofilecharts

# Open the app in your browser
minikube service vprofile-demo-vproapp-service
```

---

## 🌩️ Deploy on AWS EC2 (outline)

```bash
# SSH into an Ubuntu EC2 instance
# 1) Install Docker, kubectl, helm, Jenkins
# 2) Bootstrap single-node K8s (kubeadm, microk8s, or k3s)
# 3) Configure Docker creds & KUBECONFIG in Jenkins
# 4) Run the pipeline from Jenkins “Build Now”
```



---

## 📊 Monitoring Setup

```bash
# Install monitoring stack
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install monitoring prometheus-community/kube-prometheus-stack

# Access Grafana
kubectl -n monitoring port-forward svc/monitoring-grafana 3000:80
open http://localhost:3000       # login: admin / prom-operator
```

Import the example dashboard JSON in `docs/grafana-dashboard.json`.

---

## 📁 Repository Layout

```
helm/vprofilecharts/      # Helm chart (templates + values)
kubernetes/
  ├─ db/                  # MySQL manifests
  ├─ memcache/            # Memcache manifests
  ├─ tomapp/              # Tomcat manifests
  └─ vpro-app/            # Spring Boot app manifests
src/                      # Simple Spring Boot REST service
Jenkinsfile               # Declarative pipeline
Dockerfile                # Container image definition
Screenshots/              # PNGs referenced in this README
LICENSE                   # GNU GPL v3
```

---

## 🛠️ Development

```bash
# Run unit tests
./mvnw test

# Run the app locally
./mvnw spring-boot:run
```

---

## 🤝 Contributing

1. **Fork** this repo & create your branch: `git checkout -b feature/foo`  
2. **Commit** your changes: `git commit -m "feat: add foo"`  
3. **Push** to the branch: `git push origin feature/foo`  
4. **Open a Pull Request**

---



---

### Maintainer

**Dhruv Baraiya** — [LinkedIn](https://www.linkedin.com/in/dhruvbaraiya27) • [Email](mailto:baraiya.d@northeastern.edu)
