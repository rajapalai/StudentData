pipeline {
	agent any
	tools {
		maven 'maven'
	}
	stages {
		stage ('Checkout SCM'){
			steps {
				checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [
						[credentialsId: 'github', url: 'https://github.com/rajapalai/StudentData.git']
					]])
				sh 'mvn clean install'
			}
		}
		stage ('Maven build') {
			steps {
				sh 'mvn package'
			}
		}

		stage ('Jfrog artifactory configuration') {
			steps {
				rtServer (
						id: "jfrog",
						url: "http://34.228.64.200:8082/artifactory",
						credentialsId: "jfrog"
						)

				rtMavenDeployer (
						id: "MAVEN_DEPLOYER",
						serverId: "jfrog",
						releaseRepo: "libs-release-local",
						snapshotRepo: "libs-snapshot-local"
						)

				rtMavenResolver (
						id: "MAVEN_RESOLVER",
						serverId: "jfrog",
						releaseRepo: "libs-release",
						snapshotRepo: "libs-snapshot"
						)
			}
		}
		stage ('Jfrog artifactory deployment') {
			steps {
				rtMavenRun (
						tool: "maven",
						pom: 'pom.xml',
						goals: 'clean install deploy',
						deployerId: "MAVEN_DEPLOYER",
						resolverId: "MAVEN_RESOLVER"
						)
			}
		}
		stage ('Deploy artifacts into libs-release-local file') {
			steps {
				rtUpload (
						serverId: "jfrog",
						spec: '''{
		 						"files" : [
		 							{
		 							"pattern" : "StudentData.war",
		 							"target" : "libs-release-local"
		 							}
		 						]
		 					}''',
						)
			}
		}

		stage ('Publish Build Info') {
			steps {
				rtPublishBuildInfo (
						serverId: "jfrog"
						)
			}
		}
	}
}