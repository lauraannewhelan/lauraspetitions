pipeline {
    agent any

    environment {
        WAR_NAME = "lauraspetitions.war"  // The WAR file name (use .war extension)
        TOMCAT_HOST = "13.60.215.49"  // Your EC2 public IP (without http:// and port)
        TOMCAT_PORT = "8080"  // Tomcat port (updated to 8080 as per your request)
        TOMCAT_USER = "ubuntu"  // Your EC2 username (ubuntu)
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git"  // Your GitHub repository URL
        SSH_CREDENTIALS = credentials('EC2-Jenkins-SSH')  // Use Jenkins credentials for SSH key
    }

    stages {
        stage('Checkout') {
            steps {
                // Get code from GitHub (no authentication required for public repositories)
                git branch: 'main', url: "${GITHUB_REPO}"
            }
        }

        stage('Build') {
            steps {
                // Use Maven to build the project
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                // Run unit tests
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                // Package the application as a WAR file
                script {
                    sh 'mvn package'
                }
            }
        }

        stage('Archive WAR') {
            steps {
                // Archive the WAR file for future reference
                archiveArtifacts artifacts: '**/target/*.war', allowEmptyArchive: true
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                // Manual approval to deploy the WAR file
                input message: 'Approve Deployment to Tomcat?', ok: 'Deploy'
                script {
                    // Deploy WAR to Tomcat using SCP with Jenkins credentials
                    sh """
                    sshpass -p '${SSH_CREDENTIALS_PASSPHRASE}' scp -o StrictHostKeyChecking=no -i ${SSH_CREDENTIALS} target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/opt/tomcat/webapps/ROOT.war
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment succeeded!'
        }

        failure {
            echo 'Build or deployment failed.'
        }
    }
}
