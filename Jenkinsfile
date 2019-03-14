pipeline {
	environment {
		REGISTRY = 'hub.micromata.de'
		REGISTRY_CREDS = 'play-its-registry'
	}

	agent any

	options {
		skipDefaultCheckout true
		disableConcurrentBuilds()
		timestamps()
		timeout(time: 1, unit: 'HOURS')
	}

	stages {
		stage("Build") {
			agent {
				docker {
					// Java 11 with mvn and docker
					image 'mlesniak/build-11'
				}
			}
			steps {
				checkout scm
				sh 'ls -la'
				sh 'java -version'
				sh 'curl -LSs https://raw.githubusercontent.com/fnproject/cli/master/install | sh'
				sh 'fn build'
			}
		}
	}
}
