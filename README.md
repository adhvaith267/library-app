# Library Management System – CI/CD Pipeline

## Overview

This project demonstrates a robust **CI/CD pipeline** for a Java-based Library Management System. The pipeline automates building, testing, containerizing, deploying, and monitoring the application using industry-standard DevOps tools.

## Tech Stack & Tools

| Tool      | Purpose                                      |
|-----------|----------------------------------------------|
| Git       | Version control                              |
| Jenkins   | CI/CD orchestration                          |
| Maven     | Build automation & dependency management     |
| JUnit     | Unit testing                                 |
| Docker    | Containerization                             |
| Ansible   | Automated deployment                         |
| Graphite  | Metrics collection                           |
| Grafana   | Real-time monitoring & dashboards            |

## Pipeline Stages

1. **Source Checkout**: Jenkins pulls the latest code from GitHub.
2. **Build**: Maven compiles and packages the Java application.
3. **Test**: JUnit tests are run; results are recorded in Jenkins.
4. **Dockerize**: Docker builds an image from the JAR file.
5. **Deploy**: Ansible deploys the Docker container to the server.
6. **Monitoring**: Metrics are sent to Graphite and visualized in Grafana.

