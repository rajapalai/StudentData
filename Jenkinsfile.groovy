pipeline {
  agent any
  tools {
  maven 'maven'
  }
    stages {
        stage ('Checkout SCM'){
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/rajapalai/StudentData.git']]])
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
                    id: "Artifactory",
                    url: "http://18.207.136.250:8082/artifactory",
                    credentialsId: "Artifactory"
                )

                rtMavenDeployer (
                    id: "MAVEN_DEPLOYER",
                    serverId: "Artifactory",
                    releaseRepo: "libs-release-local",
                    snapshotRepo: "libs-snapshot-local"
                )

                rtMavenResolver (
                    id: "MAVEN_RESOLVER",
                    serverId: "Artifactory",
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
                    goals: 'clean install',
                    deployerId: "MAVEN_DEPLOYER",
                    resolverId: "MAVEN_RESOLVER"
                )
            }
        }

    stage ('Publish build info') {
            steps {
                rtPublishBuildInfo (
                    serverId: "Artifactory"
                )
            }
        }
    }
}

//stage ('Artifactory configuration') {
//    steps {
//        rtServer (
//                id: 'Artifactory',
//                url: 'http://34.228.64.200:8082/artifactory',
//                username: 'admin',
//                password: 'Raja@palai@1992',
//                bypassProxy: true,
//                timeout: 300
//        )
//    }
//}
//   stage ('Deploy Artifacts Snapshot') {
    //         steps {
    //             rtUpload (
    //                 serverId: 'jfrog',
    //                 spec: '''{
    //                     "files":[
    //                     {
    //                         "pattern" : "*.war",
    //                         "target" : "libs-snapshot/com/studentDetails/StudentRecord/"
    //                     }
    //                     ]
    //                 }''',
    //             )
    //             sh 'mvn clean install deploy'
                
    //         }
    //     }
        // stage ('Release Artifacts into artifactory') {
        //     steps {
        //         rtUpload (
        //             serverId: 'Artifactory',
        //             spec: '''{
        //                 "files":[
        //                 {
        //                     "pattern" : "*.war",
        //                     "target" : "libs-release/com/studentDetails/StudentRecord/"
        //                 }
        //                 ]
        //             }''',
        //         )
        //     }
        // }
        // stage ('Publish build info') {
        //     steps {
        //         rtServer (
        //             id: 'Artifactory',
        //             url: 'http://34.228.64.200:8082/artifactory',
        //             username: 'admin',
        //             password: 'Raja@palai@1992',
        //             bypassProxy: true,
        //             timeout: 300
        //         )
        //          rtPublishBuildInfo (
        //               serverId: 'Artifactory',
        //         )
        //     }
        // }