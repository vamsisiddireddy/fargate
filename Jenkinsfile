pipeline {
    agent any
    
    environment {
        DOCKER_HUB_USERNAME = credentials('dockerhub')
        DOCKER_IMAGE_NAME = 'satulakhil/backend'
        DOCKER_IMAGE_NAME2 = 'satulakhil/frontend'
        DOCKER_IMAGE_TAG = 'java'
        DOCKER_IMAGE_TAG2 = 'React'
    }

    stages {
        stage('Clone Backend Git repository') {
            steps {
                git branch: 'main', url: 'https://github.com/vamsisiddireddy/backend.git'
            }
        }

        stage('Build Backend with Maven') {
            steps {
                sh 'mvn clean install' 
                sh 'pwd'
            }
        }
        
        stage('Copying Backend Artifact') {
            steps {
               sh 'sudo cp -r /var/lib/jenkins/workspace/pipeline/target/*.jar /var/lib/jenkins/workspace/pipeline/'
            }
        }
        
        stage('Building Backend image') {
            steps {
                sh "sudo docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
            }
        }
        
        stage('Clone Frontend Git repository') {
            steps {
                git branch: 'main', url: 'https://github.com/vamsisiddireddy/fargate.git'
            }
        }
        
        stage('Building Frontend image') {
            steps {
                sh "sudo docker build -t ${DOCKER_IMAGE_NAME2}:${DOCKER_IMAGE_TAG2} ."
            }
        }
        
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub', variable: 'DOCKER_HUB_USERNAME')]) {
                        sh "echo ${DOCKER_HUB_USERNAME} | sudo docker login --username satulakhil --password-stdin"
                        sh "sudo docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                        sh "sudo docker push ${DOCKER_IMAGE_NAME2}:${DOCKER_IMAGE_TAG2}"
                    }
                }
            }
        }
        
        
        stage('Pruning Artifact') {
            steps {
               sh 'rm -rf ./*.jar'
            }
        }
        
        
        stage('Run Terraform') {
            steps {
                dir('/var/lib/jenkins/workspace/pipeline/fargate/infra_terraform/infra') {
                    echo 'Executing commands within the specified directory'
                    sh 'terraform init'
                    sh 'terraform apply -auto-approve --target=module.${module}'
                }
            }
        }
    }
}
