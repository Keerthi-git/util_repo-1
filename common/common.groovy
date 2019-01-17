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
			subject: '${env.DEFAULT_SUBJECT}', 
			body: '${env.DEFAULT_CONTENT}',
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