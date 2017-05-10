pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'docker-compose build'
            }
        }
        stage('Test') {
            steps {
                sh 'docker-compose up -d'
                sh '$dc=$(docker ps -qa)
                for i in $dc
                do
                    testvar=$(docker exec -it $i echo "Test | wc -l)"
                    if [[ $testvar != "1" ]]; then
                        echo "Container $i is ok"
                    fi
                done'
            }
        }
    }
}