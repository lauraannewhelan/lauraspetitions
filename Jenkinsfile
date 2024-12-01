pipeline {
    agent any

    tools {
        // Use the Maven tool that you configured in Jenkins' Global Tool Configuration
        maven 'Maven'  // Ensure 'Maven' matches the name you set in Global Tool Configuration
    }

    environment {
        WAR_NAME = "lauraspetitions.war"
        TOMCAT_HOST = "13.60.215.49"
        TOMCAT_PORT = "8080"
        TOMCAT_USER = "ubuntu"
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git"
        SSH_KEY_PATH = "/var/lib/jenkins/.ssh/id_rsa_jenkins"
        SSH_CREDENTIALS_ID = "jenkins-ssh-key" // Set this to your Jenkins credentials ID for the SSH key
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Change the branch reference to 'master' if your branch is 'master'
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: 'refs/heads/master']],  // Use master here
                        userRemoteConfigs: [[url: GITHUB_REPO]]
                    ]
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Use Maven to build the project
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests using Maven
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    // Package the application into a WAR file using Maven
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
                    // Use sshagent to provide the SSH credentials for scp
                    sshagent(['${SSH_CREDENTIALS_ID}']) {
                        // Copy the WAR file to Tomcat's webapps directory
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
