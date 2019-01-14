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

def createAwsInstance() {
	script {
		sh 'terraform --version'
		sh 'mkdir terraform'
		sh 'cp util_repo/common/launch_aws_instance.tf /terraform
		sh 'cd terraform'
		sh 'ls'
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
		sh 'rm -rf ../'+jobName+'/*'
	}
	echo 'cleaned workspace'
}
return this