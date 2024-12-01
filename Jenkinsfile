pipeline {
    agent any

    environment {
        WAR_NAME = "lauraspetitions.war"
        TOMCAT_HOST = "13.60.215.49"
        TOMCAT_PORT = "8080"
        TOMCAT_USER = "ubuntu"
        GITHUB_REPO = "https://github.com/lauraannewhelan/lauraspetitions.git"
        SSH_KEY_PATH = "/var/lib/jenkins/.ssh/id_rsa_jenkins"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Change the branch reference to 'master' if your branch is 'master'
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: 'refs/heads/master']],  // Use master here
                        userRemoteConfigs: [[url: 'https://github.com/lauraannewhelan/lauraspetitions.git']]
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
