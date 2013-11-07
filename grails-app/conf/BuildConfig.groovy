/**
* Copyright (c) 2012 Partners In Health.  All rights reserved.
* The use and distribution terms for this software are covered by the
* Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
* which can be found in the file epl-v10.html at the root of this distribution.
* By using this software in any fashion, you are agreeing to be bound by
* the terms of this license.
* You must not remove this notice, or any other, from this software.
**/ 
//grails.server.port.http = 8081

grails.project.class.dir = "target/classes"
grails.project.docs.output.dir = "web-app/docs"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
grails.project.work.dir = "target/work"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits( "global" ) {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
	repositories {
        inherits true // Whether to inherit repository definitions from plugins

        //grailsRepo "http://grails.org/plugins"
        grailsPlugins()
		grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        mavenRepo "http://repository.springsource.com/maven/bundles/release"
        mavenRepo "http://repository.springsource.com/maven/bundles/external"
        mavenRepo "http://repository.springsource.com/maven/libraries/release"
        mavenRepo "http://repository.springsource.com/maven/libraries/external"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repo.grails.org/grails/plugins-releases/"
	}
	
	dependencies {
        build ('org.jboss.tattletale:tattletale-ant:1.2.0.Beta2')  { excludes "ant", "javassist" }

        compile ('org.docx4j:docx4j:2.8.1') { excludes 'commons-logging:commons-logging:1.0.4', 'commons-codec', 'commons-io'}
        compile 'c3p0:c3p0:0.9.1.2'
        compile 'mysql:mysql-connector-java:5.1.20'

        compile 'com.google.zxing:javase:2.0'
        compile ('org.codehaus.groovy.modules.http-builder:http-builder:0.5.2') { excludes "xercesImpl", "groovy",  "commons-lang", "commons-codec" }
        compile 'org.apache.commons:commons-email:1.2'
        //compile 'org.apache.httpcomponents:httpcore:4.2.1'
        compile 'commons-lang:commons-lang:2.6'
		compile 'net.sourceforge.openutils:openutils-log4j:2.0.5'
        compile "org.jadira.usertype:usertype.jodatime:1.9"
        compile("net.sf.ehcache:ehcache-web:2.0.3") {
            excludes "ehcache-core", "xml-apis" // ehcache-core is provided by Grails
        }

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
        test "org.codehaus.geb:geb-spock:0.6.3"
		test 'org.seleniumhq.selenium:selenium-firefox-driver:2.25.0'
        test ('net.sourceforge.htmlunit:htmlunit:2.10') { excludes "xml-apis" }
        test ('org.seleniumhq.selenium:selenium-htmlunit-driver:2.25.0')  { excludes "htmlunit" }
		test 'org.seleniumhq.selenium:selenium-chrome-driver:2.25.0'
		test 'org.seleniumhq.selenium:selenium-ie-driver:2.25.0'
        test 'org.seleniumhq.selenium:selenium-support:2.25.0'
		test 'dumbster:dumbster:1.6'
    }
	plugins {
        build ":tomcat:7.0.42"
        build ':code-coverage:1.2.5'
        build ':codenarc:0.19'


        compile ":scaffolding:2.0.1"

        runtime (':hibernate:3.6.10.2') { excludes 'antlr' } // or ":hibernate4:4.1.11.2"

        runtime ":jquery:1.7.2"
        runtime ":jquery-ui:1.8.7"
        runtime ":resources:1.2.1"
        runtime ":zipped-resources:1.0"

        runtime( ':constraints:0.6.0' )
        runtime( ':jquery-validation:1.9' ) { // 1.7.3
            excludes 'constraints'
        }
        runtime( ':jquery-validation-ui:1.4.7' ) { // 1.1.1
            excludes 'constraints', 'spock'
        }
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }

        runtime( ':mail:1.0.1' ) { excludes 'mail', 'spring-test' }

        runtime( ':excel-import:0.3' ) { excludes 'poi', 'poi-contrib', 'poi-scratchpad' }

        compile(":liquibase:1.9.3.6") {
            exclude 'data-source'
        }

        compile(":grails-ui:1.2.3"){
            exclude 'yui'
        }

        compile(":quartz2:2.1.6.2") { exclude 'svn' }
		//runtime ':quartz:1.0-RC4'
		//compile ':quartz:1.0-RC4'

        test (name:'geb', version:'0.6.3')

        compile ":rendering:0.4.3"
        //compile ":standalone:1.0"
        //compile ":burning-image:0.5.1"
        //compile ":settings:1.4"
        //compile ":symmetricds:2.4.0"

        //compile ":grails-melody:1.46"

        compile ':app-info:1.0.3'
        compile ':barcode4j:0.3'
        compile ':bubbling:2.1.4'
        compile ':cache-headers:1.1.5'
        compile ':cached-resources:1.0'
        compile ':clickstream:0.2.0'
        compile ':console:1.1'
        compile ':csv:0.3.1'
        compile ':dynamic-controller:0.3'
        compile ':famfamfam:1.0.1'
        compile ':google-analytics:1.0'
        compile ':google-visualization:0.6.2'
        compile ':image-builder:0.2'
        compile ':joda-time:1.4'
        compile ':pretty-time:0.3'
        compile ':profile-template:0.1'
        compile ':runtime-logging:0.4'
        compile ':springcache:1.3.1'
        compile ':template-cache:0.1'
        compile ':ui-performance:1.2.2'
        compile ':webflow:2.0.8.1'
        compile ':yui:2.8.2.1'
	}
}
