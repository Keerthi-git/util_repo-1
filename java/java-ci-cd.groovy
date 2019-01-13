def execute() {
	stage('read') {
		commonUtility.javaRead();
		echo 'read success'
	}
	/*stage('scan') {
        sh props.SONAR_SCAN+' '+props.SONAR_HOST
		echo 'scan success'
	}*/
	stage('build') {
        sh props.MAVEN_BUILD
		echo 'build success'
    }
	stage('upload') {
        script {
			commonUtility.uploadWarArtifactory();
			echo 'upload success'
		}
    }
	stage('deploy') {
        sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
		echo 'deploy success'
    }
	post {
        always {
          step([$class: 'Mailer',
            notifyEveryUnstableBuild: true,
            recipients: "niranjan.hampannavar@mindtree.com",
            sendToIndividuals: true])
        }
    }
}
return this