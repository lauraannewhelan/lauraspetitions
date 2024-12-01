pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        WAR_NAME = "lauraspetitions.war"
        TOMCAT_HOST = "13.60.215.49"
        TOMCAT_PORT = "8080"
        TOMCAT_USER = "ubuntu"
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git"
        SSH_KEY_PATH = "/Users/laurawhelan/.ssh/id_rsa_jenkins"
        SSH_CREDENTIALS_ID = '5d7dffdc-0cd2-47db-a18c-4860e22e26f5'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: 'refs/heads/master']],
                        userRemoteConfigs: [[url: GITHUB_REPO]]
                    ]
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Run the Maven build command
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run Maven tests
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    // Package the application into a WAR file
                    sh 'mvn package'
                }
            }
        }

        stage('Archive WAR') {
            steps {
                // Archive the WAR file as an artifact
                archiveArtifacts artifacts: '**/target/*.war', allowEmptyArchive: true
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                input message: 'Approve Deployment to Tomcat?', ok: 'Deploy'
                script {
                    sshagent([SSH_CREDENTIALS_ID]) {
                        // First, copy the WAR to a temporary directory on Tomcat server
                        sh """
                        scp -i ${SSH_KEY_PATH} target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/tmp/${WAR_NAME}
                        """
                        // Then, move the WAR file to Tomcat's webapps directory as a new webapp
                        sh """
                        ssh -i ${SSH_KEY_PATH} ${TOMCAT_USER}@${TOMCAT_HOST} 'sudo mv /tmp/${WAR_NAME} /opt/tomcat/webapps/lauraspetitions.war'
                        """
                        // Restart Tomcat to deploy the new webapp
                        sh """
                        ssh -i ${SSH_KEY_PATH} ${TOMCAT_USER}@${TOMCAT_HOST} 'sudo systemctl restart tomcat'
                        """
                    }
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
