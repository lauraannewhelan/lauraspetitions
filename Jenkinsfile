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
        SSH_KEY_PATH = "SSH_KEY_PATH = "/Users/laurawhelan/.ssh/id_rsa_jenkins
        // Correctly using the SSH credentials ID here
        SSH_CREDENTIALS_ID = '5d7dffdc-0cd2-47db-a18c-4860e22e26f5'  // Directly specifying the ID
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
                    // Using the actual credentials ID in the ssh-agent step
                    sshagent(['5d7dffdc-0cd2-47db-a18c-4860e22e26f5']) {
                        sh """
                        scp -i ${SSH_KEY_PATH} target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/opt/tomcat/webapps/ROOT.war
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
