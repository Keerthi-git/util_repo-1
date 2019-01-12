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
		pom = readMavenPom file: props.POM_FILE
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
			commonUtility=load 'util_repo/common/common.groovy'
			commonUtility.uploadWarArtifactory();
			echo 'upload success'
		}
    }
	stage('deploy') {
        sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
		echo 'deploy success'
    }
}
return this