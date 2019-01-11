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
		pom = readMavenPom file: 'pom.xml'
		artifactId=pom.artifactId
		echo 'read success'
	}
	/*stage('scan') {
        sh props.SONAR_SCAN+' '+props.SONAR_HOST
		echo 'scan success'
	}*/
	stage('build') {
        sh props.MAVEN_BUILD+buildNo
		echo 'build success'
    }
	stage('upload') {
        script {
			server = Artifactory.server props.ARTIFACTORY_ID
			uploadSpec = """{
				"files":[{
				"pattern": "target/*.war",
				"target": "Jenkins-snapshot/${artifactId}/${buildNo}/"
				}]
			}"""
			server.upload(uploadSpec) 	
			echo 'upload success'
		}
    }
	stage('deploy') {
        sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
		echo 'deploy success'
    }
}
return this