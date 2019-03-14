pipeline {
    environment {
        REGISTRY = 'hub.micromata.de'
        REGISTRY_CREDS = 'play-its-registry'
    }
    
    agent {
        docker {
            image 'mlesniak/build-11'
        }
    }

	options {
		skipDefaultCheckout true
		disableConcurrentBuilds()
		timestamps()
		timeout(time: 1, unit: 'HOURS')
	}

	stages {
		stage("Build") {
			steps {
				checkout scm
				sh 'ls -la'
				sh 'java -version'
				sh 'curl -LSs https://raw.githubusercontent.com/fnproject/cli/master/install | sh'
				sh 'fn build'
			}
		}
		stage("Deploy") {
			steps {
				sh 'fn create context play --provider default --api-url https://fnproject.play.micromata.de:443 --registry hub.play.micromata.de'
				sh 'fn list contexts'
				sh 'fn use context play'
				// only needed once, "create-if-not-exists": sh 'fn create app dokumentor-app'
				sh 'fn --verbose deploy --local --app dokumentor-app'
			}
		}
        stage("Publish") {
            steps {
                script {
                    docker.withRegistry("https://${REGISTRY}", "${REGISTRY_CREDS}") {
                        def image = docker.image("hub.play.micromata.de/dokumentor:0.0.2");
                        image.push();
                    }
                }
            }
        }
	}
}
