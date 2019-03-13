pipeline {
	agent any
	stages {
		stage("Parallel") {
			parallel {
				stage("Hello") {
					steps {
						echo 'Hello World'
					}
				}
				stage("Bonjour") {
					steps {
						echo 'Bonjour Monde'
					}
				}
			}
		}
		stage("Fun") {
			agent {
				docker {
					image 'fnproject/fn'
				}
			}
			steps {
				sh 'ls -la'
				sh 'java -version'
				sh 'fn build'
			}
		}
	}
}
