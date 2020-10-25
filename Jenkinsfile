pipeline {
    agent any
    environment {
        JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk-14.jdk/Contents/Home"
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}