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
                    // Send build success metric (always 1 here since we're in success path)
                    sh "echo 'jenkins.library_app_pipeline.build.success 1 \$(date +%s)' | nc -q0 localhost 2003"
                    // TEST: Send fixed duration metric for verification
                    sh "echo 'jenkins.library_app_pipeline.build.test_duration 5000 \$(date +%s)' | nc -q0 localhost 2003"
                    // Send test metric (corrected)
                    sh '''
                        TOTAL_TESTS=$(grep -o "Tests run: [0-9]*" target/surefire-reports/*.txt | awk -F": " '{print $2}' | paste -sd+ | bc)
                        echo "jenkins.library_app_pipeline.tests.total $TOTAL_TESTS $(date +%s)" | nc -q0 localhost 2003
                    '''
                }
            }
        }
    }
    post {
        always {
            script {
                // REAL build duration (available only in post)
                def duration = currentBuild.duration
                sh "echo 'jenkins.library_app_pipeline.build.duration ${duration} \$(date +%s)' | nc -q0 localhost 2003"
                // REAL build success (works for success/failure)
                def success = currentBuild.result == 'SUCCESS' ? 1 : 0
                sh "echo 'jenkins.library_app_pipeline.build.success ${success} \$(date +%s)' | nc -q0 localhost 2003"
            }
        }
    }
}

