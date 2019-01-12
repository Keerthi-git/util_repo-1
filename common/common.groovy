def uploadWarArtifactory() {
	stage('common upload') {
		script {
			server = Artifactory.server props.ARTIFACTORY_ID
			uploadSpec = """{
				"files":[{
				"pattern": "target/*.war",
				"target": "Jenkins-snapshot/${artifactId}/${buildNo}/"
				}]
			}"""
			server.upload(uploadSpec) 	
			echo 'common upload success'
		}
	}
}
return this