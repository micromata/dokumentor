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
			steps {
				sh 'ls -la'
				sh 'java -version'
				sh 'curl -LSs https://raw.githubusercontent.com/fnproject/cli/master/install | sh'
				sh 'fn build'
			}
		}
	}
}
