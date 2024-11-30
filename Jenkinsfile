pipeline {
    agent any

    stages {
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

        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                sh './mvnw spring-boot:run'
            }
        }
    }
}
