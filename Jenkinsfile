pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        WAR_NAME = "lauraspetitions.war"  // Keep the WAR name the same
        TOMCAT_HOST = "13.60.215.49"
        TOMCAT_PORT = "8080"
        TOMCAT_USER = "ubuntu"
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git"
        SSH_KEY_PATH = "/Users/laurawhelan/.ssh/id_rsa_jenkins"  // Corrected the SSH_KEY_PATH definition
        SSH_CREDENTIALS_ID = '5d7dffdc-0cd2-47db-a18c-4860e22e26f5'  // Using the credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: 'refs/heads/master']],  // Use master or the branch you prefer
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
                    // Deploy the WAR file to Tomcat using SCP
                    sshagent([SSH_CREDENTIALS_ID]) {
                        // First, copy the WAR to a temporary directory on the Tomcat server
                        sh """
                        scp -i ${SSH_KEY_PATH} target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/tmp/${WAR_NAME}
                        """
                        // Then, use sudo to move the WAR file to the Tomcat webapps directory under its own name (not ROOT.war)
                        sh """
                        ssh -i ${SSH_KEY_PATH} ${TOMCAT_USER}@${TOMCAT_HOST} 'sudo mv /tmp/${WAR_NAME} /opt/tomcat/webapps/lauraspetitions.war'
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
