pipeline {
    agent any

    environment {
        // Define environment variables for easier maintenance
        WAR_NAME = "${env.USER_NAME}spetitions.war" // e.g., johnspetition.war
        TOMCAT_HOST = "ec2-xx-xx-xx-xx.compute-1.amazonaws.com" // EC2 Public IP
        TOMCAT_PORT = "9090"
        TOMCAT_USER = "your_tomcat_user"
        TOMCAT_PASSWORD = "your_tomcat_password"
        GITHUB_REPO = "https://github.com/yourusername/your-repo.git"
    }

    stages {
        stage('Checkout') {
            steps {
                // Get code from GitHub
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
                // Archive the WAR file
                archiveArtifacts artifacts: '**/target/*.war', allowEmptyArchive: true
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                // Manual approval to deploy the WAR file
                input message: 'Approve Deployment to Tomcat?', ok: 'Deploy'
                script {
                    // Deploy WAR to Tomcat using SCP or FTP
                    sh """
                    scp target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/path/to/tomcat/webapps/
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
