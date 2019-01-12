def javaRead() {
	stage('java read') {
		git url: props.JAVA_APP_REPO_GIT_URL,
        branch: props.BRANCH
		pom = readMavenPom file: props.POM_FILE
		artifactId=pom.artifactId
		echo 'java read success'
	}
}
def uploadWarArtifactory() {
	stage('war upload') {
		script {
			server = Artifactory.server props.ARTIFACTORY_ID
			uploadSpec = """{
				"files":[{
				"pattern": "target/*.war",
				"target": "Jenkins-war-snapshots/${artifactId}/1.0.${buildNo}/"
				}]
			}"""
			server.upload(uploadSpec) 	
			echo 'war upload success'
		}
	}
}
return this