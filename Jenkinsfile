pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean assemble'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Publish JUnit Test Results') {
            steps {
                sh 'find . -name "TEST-*.xml" -exec touch {} \\;'
                junit '**/test-results/test/*.xml'
            }
        }

        stage('SonarQube') {
            steps {
                //sh './gradlew sonarqube -Dsonar.host.url=http://ec2-3-235-176-239.compute-1.amazonaws.com:9000/'
                sh 'echo skipping for now'
            }
        }

        stage('Publish Test Coverage Report') {
         steps {
           step([$class: 'JacocoPublisher', 
                execPattern: '**/build/jacoco/*.exec',
                classPattern: '**/build/classes',
                sourcePattern: 'src/main/java',
                exclusionPattern: 'src/test*'
                ])
            }
        }

        stage('Build Docker image') {
            steps {
                sh './gradlew docker'
            }
        }

        stage('Push Docker image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('docker-hub')
            }
            steps {
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                sh './gradlew dockerPush -PdockerHubUsername=$DOCKER_HUB_LOGIN_USR'
            }
        }

        stage('Deploy to AWS') {
            environment {
                DOCKER_HUB_LOGIN = credentials('docker-hub')
            }
            steps {
                withAWS(credentials: 'aws-credentials', region: env.REGION) {
                    sh './gradlew awsCfnMigrateStack awsCfnWaitStackComplete -PsubnetId=$SUBNET_ID -PdockerHubUsername=$DOCKER_HUB_LOGIN_USR'
                }
            }
        }
    }
}
