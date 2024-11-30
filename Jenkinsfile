pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out code from GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application...'
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application as WAR file...'
                sh 'mv target/lauraspetitions-0.0.1-SNAPSHOT.war target/lauraspetitions.war'
                archiveArtifacts artifacts: 'target/lauraspetitions.war', fingerprint: true
            }
        }

        stage('Deploy to Tomcat') {
            when {
                expression {
                    input(message: 'Deploy to EC2?', ok: 'Deploy')
                }
            }
            steps {
                echo 'Deploying to Amazon EC2...'
                sh '''
                    scp -i /path/to/ec2-key.pem target/lauraspetitions.war ec2-user@EC2_PUBLIC_IP:/var/lib/tomcat9/webapps/lauraspetitions.war
                '''
                sh '''
                    ssh -i /path/to/ec2-key.pem ec2-user@EC2_PUBLIC_IP "sudo systemctl restart tomcat"
                '''
            }
        }
    }
}
