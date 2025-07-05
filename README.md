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

## Jenkins Pipeline Example

pipeline {
agent any
stages {
stage('Checkout') {
steps { git 'git@github.com:adhvaith267/library-app.git' }
}
stage('Build') {
steps { sh 'mvn clean package' }
}
stage('Test') {
steps {
sh 'mvn test'
junit 'target/surefire-reports/*.xml'
}
}
stage('Dockerize') {
steps { sh 'docker build -t library-app:${BUILD_NUMBER} .' }
}
stage('Deploy with Ansible') {
steps {
ansiblePlaybook credentialsId: 'ansible-ssh-key', playbook: 'ansible/deploy.yml', inventory: 'ansible/inventory.ini'
}
}
stage('Send Metrics to Graphite') {
steps {
sh '''
echo "jenkins.library_app_pipeline.build.success 1 $(date +%s)" | nc -q0 192.168.4.157 2003
'''
}
}
}
}

text

## How to Run Locally

1. Clone the repository:
    ```
    git clone git@github.com:adhvaith267/library-app.git
    ```
2. Build the project:
    ```
    mvn clean package
    ```
3. Build and run the Docker image:
    ```
    docker build -t library-app .
    docker run -p 8080:8080 library-app
    ```
4. Access the app at [http://localhost:8080](http://localhost:8080)

## Credits

Started by user **Adhvaith**.
