def execute() {
	stage('stageCheckoutProject') {
		git url: props.JAVA_APP_REPO_GIT_URL,
        branch: props.BRANCH
		pom = readMavenPom file: props.POM_FILE
		artifactId=pom.artifactId
		version=pom.version
		print 'Checkout Project Success'
	}
	
	stage('stageBuildAutomation') {
		/*sh props.SONAR_SCAN+' '+props.SONAR_HOST*/
		sh props.MAVEN_BUILD
		print 'Build Automation Success'
    }
	
	stage('stageBuildManagement') {
		commonUtility.uploadWarArtifactory();
		sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION
		sh 'docker stop $(docker ps -a -q)'
		sh 'docker build -t dockerfile .'
		sh 'docker run --rm -d -p 8084:8080 dockerfile'
		print 'Build Management Success'
	}
}
return this