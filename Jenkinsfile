pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        stage('Checkout') {
            steps {
                // Jenkins will automatically checkout the code from your repo
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/**/*.xml'
            }
        }
        stage('Dockerize') {
            steps {
                script {
                    dockerImage = docker.build("library-app:${env.BUILD_NUMBER}")
                }
            }
        }
        stage('Deploy with Ansible') {
            steps {
                ansiblePlaybook(
                    playbook: 'ansible/deploy.yml',
                    inventory: 'ansible/inventory.ini',
                    extraVars: ["build_number=${env.BUILD_NUMBER}"],
                    credentialsId: 'ssh-key'
                )
            }
        }
    }
    post {
        failure {
            echo 'Build failed! Consider rollback or alert.'
        }
    }
}
