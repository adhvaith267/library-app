pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        stage('Checkout') {
            steps {
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
                    extraVars: [build_number: env.BUILD_NUMBER],
                    credentialsId: 'ssh-key'
                )
            }
        }
        stage('Send Metrics to Graphite') {
            steps {
                script {
                    // Send build success metric
                    sh "echo 'jenkins.library_app_pipeline.build.success 1 \$(date +%s)' | nc -q0 localhost 2003"
                    
                    // Send build duration metric
                    sh "echo 'jenkins.library_app_pipeline.build.duration ${currentBuild.duration} \$(date +%s)' | nc -q0 localhost 2003"
                    
                    // CORRECTED test results metric
                    sh '''
                        # Extract and sum test counts from all reports
                        TOTAL_TESTS=$(grep -o "Tests run: [0-9]*" target/surefire-reports/*.txt | awk -F" " "{print \$3}" | paste -sd+ | bc)
                        echo "jenkins.library_app_pipeline.tests.total $TOTAL_TESTS $(date +%s)" | nc -q0 localhost 2003
                    '''
                }
            }
        }
    }
    post {
        failure {
            echo 'Build failed! Consider rollback or alert.'
            script {
                // Send build failure metric
                sh "echo 'jenkins.library_app_pipeline.build.success 0 \$(date +%s)' | nc -q0 localhost 2003"
            }
        }
    }
}

