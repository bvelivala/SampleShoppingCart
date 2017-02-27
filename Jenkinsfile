node {
    withEnv(['Maven3.3.9=/usr/local/apache-maven/apache-maven-3.3.9', 'JDK1.8=/usr/lib/jvm/java-8-openjdk-amd64 - JDK1.8']) {
        stage('Checkout') {
            checkout poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'git@github.com:bvelivala/SampleShoppingCart.git']]]
            sh 'mvn clean'
        }
        
        stage('Build, Unit Test and Code Coverage') {
            sh 'mvn org.jacoco:jacoco-maven-plugin:prepare-agent install cobertura:cobertura -Dcobertura.report.format=xml'
        }
        
        stage('Sonar Code Analysis') {
            withSonarQubeEnv {
                sh "mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=admin -Dsonar.password=admin -Dsonar.github.repository=bvelivala/SampleShoppingCart -Dsonar.projectName=InsDevopsDemo_${BUILD_NUMBER} -Dsonar.projectVersion=${BUILD_NUMBER} -Dsonar.sources=src/main -Dsonar.junit.reportsPath=target/surefire-reports -Dsonar.jacoco.reportPath=target/jacoco.exec -Dsonar.jacoco.itReportPath=target/jacoco.exec -Dsonar.cobertura.reportPath=target/site/cobertura/coverage.xml"
            }
        }
        
        stage('Publish Reports') {
            junit 'target/surefire-reports/*.xml'
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/site/jacoco', reportFiles: 'index.html', reportName: 'Code Coverage'])
	        performanceReport compareBuildPrevious: false, configType: 'PRT', errorFailedThreshold: 10, errorUnstableResponseTimeThreshold: '', errorUnstableThreshold: 10, failBuildIfNoResultFile: false, modeOfThreshold: false, modePerformancePerTestCase: true, modeThroughput: false, nthBuildNumber: 0, parsers: [[$class: 'JUnitParser', glob: '**/TEST-*.xml']], relativeFailedThresholdNegative: -10.0, relativeFailedThresholdPositive: 10.0, relativeUnstableThresholdNegative: -10.0, relativeUnstableThresholdPositive: 10.0
        }
        
		stage('Store Artifact') {
            nexusArtifactUploader artifacts: [[artifactId: 'ShoppingCart', classifier: '', file: 'target/ShoppingCart.war', type: 'war']], credentialsId: 'nexus', groupId: 'org.cdsdemo', nexusUrl: 'ec2-52-24-142-120.us-west-2.compute.amazonaws.com:8081/nexus', nexusVersion: 'nexus2', protocol: 'http', repository: 'releases', version: '${BUILD_NUMBER}'
        }
    }
}
