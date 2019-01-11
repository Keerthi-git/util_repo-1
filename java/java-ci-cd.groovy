def execute() {
	stage('load') {
		script {
			props = readProperties file: 'Properties/pipeline.properties'
			buildNo=BUILD_NUMBER
			echo 'load success'
		}
	}
	stage('read') {
		git url: props.GIT_URL,
        branch: props.BRANCH
		echo 'read success'
	}
	/*stage('scan') {
        steps {
			sh props.SONAR_SCAN+' '+props.SONAR_HOST
			echo 'scan success'
		}
    }*/
	stage('build') {
        steps {
			sh props.MAVEN_BUILD+buildNo
			echo 'build success'
        }
	}
	stage('upload') {
        steps {
			script {
				server = Artifactory.server props.ARTIFACTORY_ID
				uploadSpec = """{
					"files":[{
					"pattern": "target/*.war",
					"target": "Jenkins-snapshot"
					}]
				}"""
				server.upload(uploadSpec) 	
				echo 'upload success'
			}
        }
	}
	stage('deploy') {
        steps {
			sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
			echo 'deploy success'
        }
	}
}
return this