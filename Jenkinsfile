pipeline{
    agent any
    stages{
        stage("Check Maven"){
            steps{
                echo "========Maven version check========"
                sh "mvn -version"
            }
        }
        stage("Build Package"){
            steps{
                echo "========Maven clean package========"
                sh "mvn package -Dmaven.test.skip=true"
            }
        }
    }
}