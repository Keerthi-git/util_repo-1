def uploadWarArtifactory() {
	stage('common upload') {
		script {
			server = Artifactory.server props.ARTIFACTORY_ID
			uploadSpec = """{
				"files":[{
				"pattern": "target/*.war",
				"target": "Jenkins-war-snapshots/${artifactId}/1.0.${buildNo}/"
				}]
			}"""
			server.upload(uploadSpec) 	
			echo 'common upload success'
		}
	}
}
return this