def execute() {
	stage('stageCheckoutProject') {
		doReadCode();
		doReadPom();
		print 'Checkout Project Success'
	}
	
	stage('stageBuildAutomation') {
		doSonarScan();
		doMavenBuild();
		print 'Build Automation Success'
    }
	
	stage('stageBuildManagement') {
		doArtifactoryUpload();
		doTomcatDeploy();
		print 'Build Management Success'
	}
}

def doReadCode() {
	git url: props.JAVA_APP_REPO_GIT_URL,
    branch: props.BRANCH
}

def doReadPom() {
	pom = readMavenPom file: props.POM_FILE
	artifactId=pom.artifactId
	version=pom.version
}

def doSonarScan() {
	sh props.SONAR_SCAN+' '+props.SONAR_HOST
}

def doMavenBuild() {
	sh props.MAVEN_BUILD
}

def doArtifactoryUpload() {
	commonUtility.uploadWarArtifactory();
}

def doTomcatDeploy() {
	/*sh props.TOMCAT_DEPLOY+' '+props.TOMCAT_LOCATION*/
	sh props.DOCKER_TOMCAT_STOP
	sh props.DOCKER_FILE_BUILD
	sh props.DOCKER_TOMCAT_RUN
}
return this