pipeline {
    agent any

    environment {
        WAR_NAME = "lauraspetitions.war"  // The WAR file name (use .war extension)
        TOMCAT_HOST = "13.60.215.49"  // Your EC2 public IP (without http:// and port)
        TOMCAT_PORT = "8080"  // Tomcat port (updated to 8080 as per your request)
        TOMCAT_USER = "ubuntu"  // Your EC2 username (ubuntu)
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git"  // Your GitHub repository URL
        SSH_KEY_PATH = "/var/lib/jenkins/.ssh/id_rsa_jenkins"  // Correct path to your SSH private key
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${GITHUB_REPO}"
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: '**/target/*.war', allowEmptyArchive: true
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                input message: 'Approve Deployment to Tomcat?', ok: 'Deploy'
                script {
                    sh """
                    scp -i ${SSH_KEY_PATH} target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/opt/tomcat/webapps/ROOT.war
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
