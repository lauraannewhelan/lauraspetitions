pipeline {
    agent any

    environment {
        // Define environment variables for easier maintenance
        WAR_NAME = "lauraannewhelanspetitions.war" // Change this based on your desired WAR name
        TOMCAT_HOST = "ec2-xx-xx-xx-xx.compute-1.amazonaws.com" // Replace with your EC2 Public IP
        TOMCAT_PORT = "9090"
        TOMCAT_USER = "your_tomcat_user" // Replace with your Tomcat user (e.g., "ubuntu")
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git" // Your GitHub repository
    }

    stages {
        stage('Checkout') {
            steps {
                // Get the latest code from GitHub repository
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
                // Request manual approval to deploy the WAR file
                input message: 'Approve Deployment to Tomcat?', ok: 'Deploy'
                script {
                    // Deploy WAR file to Tomcat using SCP (ensure SSH keys are set up)
                    sh """
                    scp -i /path/to/your/ssh-key.pem target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/opt/tomcat/webapps/
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
