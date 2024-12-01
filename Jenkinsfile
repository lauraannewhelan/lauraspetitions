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
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Checkout from GitHub repository (you can modify this to your branch as needed)
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: 'refs/heads/master']],  // Use 'master' or any other branch
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
                    // Ensure the SSH private key is available using the Jenkins credentials store
                    sshagent(credentials: ['jenkins-ssh-key']) {
                        // Use SCP to copy the WAR file to Tomcat's webapps directory
                        sh """
                        scp -o StrictHostKeyChecking=no target/${WAR_NAME} ${TOMCAT_USER}@${TOMCAT_HOST}:/opt/tomcat/webapps/ROOT.war
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
