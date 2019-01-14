def uploadWarArtifactory() {
	script {
		server = Artifactory.server props.ARTIFACTORY_ID
		uploadSpec = """{
			"files":[{
			"pattern": "target/*.war",
			"target": "Jenkins-war-snapshots/${artifactId}/${version}.${buildNo}/"
			}]
		}"""
		server.upload(uploadSpec) 	
	}
}

def sendEmail() {
	emailext( 
			subject: '${DEFAULT_SUBJECT}', 
			body: '${DEFAULT_CONTENT}',
			to: props.BUILD_EMAIL_RECIPIENT
		);
	echo 'mail sent'
}

def cleanWorkspace() {
	script {
		sh 'rm -rf ../${jobName}'
		sh 'rm -rf ../${jobName}@tmp'
	}
	echo 'cleaned workspace'
}
return this