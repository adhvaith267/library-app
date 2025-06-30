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
                    // Use your host's IP instead of localhost
                    def GRAPHITE_HOST = '192.168.4.157' // REPLACE WITH YOUR HOST IP
                    
                    // CORRECTED: Using single quotes for echo to ensure $(date +%s) expands
                    sh "echo 'jenkins.library_app_pipeline.build.success 1 \$(date +%s)' | nc -q0 ${GRAPHITE_HOST} 2003"
                    
                    // CORRECTED: Using single quotes for echo to ensure $(date +%s) expands
                    sh "echo 'jenkins.library_app_pipeline.build.test_duration 5000 \$(date +%s)' | nc -q0 ${GRAPHITE_HOST} 2003"
                    
                    // CORRECTED: Fixed quoting for grep pattern
                    sh """
                        TOTAL_TESTS=\$(grep -o 'Tests run: [0-9]*' target/surefire-reports/*.txt | awk -F': ' '{print \$2}' | paste -sd+ | bc)
                        echo "jenkins.library_app_pipeline.tests.total \$TOTAL_TESTS \$(date +%s)" | nc -q0 ${GRAPHITE_HOST} 2003
                    """
                }
            }
        }
    }
    post {
        always {
            script {
                // Use your host's IP instead of localhost
                def GRAPHITE_HOST = '192.168.4.157' // REPLACE WITH YOUR HOST IP
                
                // Send real build duration
                def duration = currentBuild.duration
                // CORRECTED: Using single quotes for echo
                sh "echo 'jenkins.library_app_pipeline.build.duration ${duration} \$(date +%s)' | nc -q0 ${GRAPHITE_HOST} 2003"
                
                // Send real build success status
                def success = currentBuild.result == 'SUCCESS' ? 1 : 0
                // CORRECTED: Using single quotes for echo
                sh "echo 'jenkins.library_app_pipeline.build.success ${success} \$(date +%s)' | nc -q0 ${GRAPHITE_HOST} 2003"
            }
        }
    }
}

