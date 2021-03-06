def uploadWarArtifactory() {
	script {
		server = Artifactory.server props.ARTIFACTORY_ID
		uploadSpec = """{
			"files":[{
			"pattern": "target/*.war",
			"target": "Java-war-snapshots/${artifactId}/${version}.${buildNo}/"
			}]
		}"""
		server.upload(uploadSpec) 	
	}
}

def sendEmail() {
	emailext( 
			attachLog: true,
			subject: '${DEFAULT_SUBJECT}', 
			body: '${DEFAULT_CONTENT}',
			to: props.BUILD_EMAIL_RECIPIENT
		);
	print 'mail sent'
}

def cleanWorkspace() {
	script {
		sh 'rm -rf ../'+jobName+'/*'
	}
	print 'cleaned workspace'
}
return this