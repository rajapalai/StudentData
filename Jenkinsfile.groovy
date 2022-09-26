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
			}
		}
		stage ('Build'){
			steps {
				sh 'mvn clean install'
			}
		}
		stage ('Package') {
			steps {
				sh 'mvn package'
			}
		}

		stage ('Artifactory configuration') {
			steps {
				rtServer (
						id: "jfrog",
						url: "http://18.207.136.250:8082/artifactory",
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

		stage ('Deploy Artifacts') {
			steps {
				rtMavenRun (
						tool: "maven",
						pom: 'StudentData/pom.xml',
						goals: 'mvn clean install deploy',
						deployerId: "MAVEN_DEPLOYER",
						resolverId: "MAVEN_RESOLVER"
						)
			}
		}

		stage ('Publish build info') {
			steps {
				rtPublishBuildInfo (
						serverId: "jfrog"
						)
			}
		}
	}
}