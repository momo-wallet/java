# Comprehensive Guide to Deploy a Package to Maven Central 

### Pre-reqs

- Set up Maven 
- Make sure your code is okay (can be compiled, run, and pass all the tests) 

### Maven Requirements

There are 3 requirements to publish your library to Maven Central:
- Sufficient Metadata
- Supply Javadoc and Sources
- Sign artifacts with GPG/PGP

#### I. Sufficient Metadata in POM.XML 

Make sure your POM.XML has all the following information 

###### Correct coordinates: 

Make sure you own the groupId as you will need to verify account ownership at later steps. Read more on how to choose your groupId [here](http://central.sonatype.org/pages/choosing-your-coordinates.html).
```
    <groupId>com.domain</groupId>
    <artifactId>sample</artifactId>
    <version>0.1</version>
    <packaging>jar</packaging>
```

For the version, the released version (NOT end with -SNAPSHOT) will be published on [Central Repository](http://repo.maven.apache.org/maven2/). If your version is a snapshot (indicating a work in progress), it will be published to the snapshot repository in your distributionManagement section instead of the Maven Central. 
Anyone wishing to use your snapshot version as a dependency library will have to manually add the remote repository into their project's POM.XML to download the snapshot version from and use it. Example: 
If your package's POM is like this:
```
<distributionManagement>
	<snapshotRepository>
		<id>ossrh</id>
		<url>https://oss.sonatype.org/content/repositories/snapshots</url>
	</snapshotRepository>
	...
</distributionManagement>
```
Then the projects using your snapshot package will have to add this part to their POM:  
```
<project>
	<repositories>
		<repository>
			<id>ossrh</id>
	    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
		</repository>
	</repositories>
	...
	<dependencies>
		<dependency>
	    <groupId>com.domain</groupId>
	    <artifactId>sample</artifactId>
	    <version>1.0-SNAPSHOT</version>
    </dependency>
    ...
	</dependencies>
	...
</project>
```

###### Detailed Information: 

- Project Name <name>, Description <description>, URL <url>
- License Information <license>. The common ones are [The Apache License](http://www.apache.org/licenses/LICENSE-2.0.txt) and [MIT License](http://www.opensource.org/licenses/mit-license.php).
- Developers (and Contributors if any) Information <developers>, <contributors> 
- SCM Information: 
```
<scm>
	<connection>scm:git:git://github.com/username/sample.git</connection>
	<developerConnection>scm:git:git@github.com:username/sample.git</developerConnection>
	<url>https://github.com/username/sample</url>
	<tag>HEAD</tag>
</scm>
```

Refer to [this Maven Central Instruction article](https://central.sonatype.org/pages/requirements.html) for more formats to declare your SCM as well as an example complete POM.XML

#### II. Supply Javadoc and Sources 

###### Create A Jar 

Maven provides 3 plugins built specifically for creating a jar from your code:
- Jar (`maven-jar-plugin`): can build and sign jars, however just the files under `src/main/java` and `src/main/resources`.  It does not include any dependencies in your package. 
- Assembly (`maven-assembly-plugin`): extracts all dependency JARs into raw classes and groups them together. It works in project with less dependencies only; for large project with many dependencies, it will cause Java class names to conflict.
- Shaded(`maven-shade-plugin`): packages all dependencies into one uber-JAR. This plugin is particularly useful as it merges content of specific files instead of overwriting them by relocating classes. This is needed when there are resource files that have the same name across the JARs and the plugin tries to package all the resource files together.

You can create executable jars from the `maven-assembly-plugin` and the `maven-shade-plugin` by specifying the main class in `META-INF/MANIFEST.MF`:
```
Manifest-Version: 1.0
Main-Class: com.domain.sample-project.MyMainClass
```

For more information, please refer to[ this article](https://gist.github.com/simonwoo/04b133cb0745e1a0f1d6). 

###### Create Bundle Jar 

To generate the javadoc and sources jars, simply add the plugins: 
```
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>2.2.1</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>2.9.1</version>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ...
        </plugins>
    </build>
```

At the minimum, your package should have the executable jar, java doc and sources after the Maven package stage. Examples: 
> momopayment-1.0-SNAPSHOT.jar
> momopayment-1.0-SNAPSHOT-javadoc.jar
> momopayment-1.0-SNAPSHOT-sources.jar

#### III. Sign Artifacts  

To release your package to Maven central, you will need to sign and authenticate the artifacts by adding a GPG passphrase.

1. Create a GPG key pair using [GNU PG](https://www.gnupg.org/download/). You can either generate keys from Terminal or download the GPG Keychain. You have to enter your real name, email address and a passphrase. Remember to distribute your public key if you actually want to use it to sign your artifacts. For more details, please consult [this article](https://github.com/sevntu-checkstyle/dsm-maven-plugin/wiki/How-to-config-GPG-and-sign-artifact-with-it).   

2. Add Maven release plugin. Do NOT insert your passphrase into POM.XML because the POM will be publicly available. You will enter your passphrase (only once) when you run the deploy plugin, instead of hard coding it somewhere.
```
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-release-plugin</artifactId>
			<version>2.5.3</version>
				<configuration>
					<localCheckout>true</localCheckout>
					<pushChanges>false</pushChanges>
					<mavenExecutorId>forked-path</mavenExecutorId>
					<arguments>-Dgpg.passphrase=${gpg.passphrase}</arguments>
				</configuration>
				<dependencies>
					<dependency>
						<groupId>org.apache.maven.scm</groupId>
						<artifactId>maven-scm-provider-gitexe</artifactId>
						<version>1.9.5</version>
					</dependency>
				</dependencies>
		</plugin>
		...
	</plugins>
	...
</build>
```

3. Add to the Maven SETTINGS.XML file (No need to change anything)
```
<settings>
	...
	<profiles>
		<profile>
			<id>ossrh</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
				<properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.passphrase>pass-phrase</gpg.passphrase>
        </properties>
		</profile>
	</profiles>
	...
</settings>
```

4. Configure to sign your artifact while releasing. Add to your POM.XML
```
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-gpg-plugin</artifactId>
			<version>1.6</version>
			<executions>
				<execution>
					<id>sign-artifacts</id>
					<phase>verify</phase>
					<goals>
						<goal>sign</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
		...
	</plugins>
	...
</build>
```

5. Build the project, enter your passphrase when prompted. Your target should be similar to this (after the verify stage): 
> momopayment-1.0-SNAPSHOT.jar
> momopayment-1.0-SNAPSHOT.jar.asc
> momopayment-1.0-SNAPSHOT.pom
> momopayment-1.0-SNAPSHOT.pom.asc
> momopayment-1.0-SNAPSHOT-javadoc.jar
> momopayment-1.0-SNAPSHOT-javadoc.jar.asc
> momopayment-1.0-SNAPSHOT-sources.jar
> momopayment-1.0-SNAPSHOT-sources.jar.asc

### Publishing Your Artifacts 

#### Get a valid groupId on Maven Central

1. [Sign up](https://issues.sonatype.org/secure/Signup!default.jspa) for a Sonatype Jira account 

2. [Create a Jira issue](https://issues.sonatype.org/secure/CreateIssue.jspa?issuetype=21&pid=10134) for new project hosting.

3. After creating the issue, Jira will require you to verify account ownership like these two examples: [Ex1](https://issues.sonatype.org/browse/OSSRH-49577?filter=-2) [Ex2](https://issues.sonatype.org/browse/OSSRH-49574?filter=-2). After completing their request, remember to change the status of your issue. 

4. It should take 10 minutes for Jira to verify and prepare your groupId. Once finished, you can deploy your library to Maven Central.

5. Do NOT deploy your artifacts until you have received an email from Jira confirming that the issue created has been resolved and your account has been prepared. If there is any problem, you can always comment on that issue (or create a new issue ticket) for help and explanation. 

#### Setting Up 

In your project's POM.XML file, add the distribution management sections:
``` 
<distributionManagement>
	<snapshotRepository>
		<id>ossrh</id>
		<url>https://oss.sonatype.org/content/repositories/snapshots</url>
	</snapshotRepository>
	<repository>
		<id>ossrh</id>
		<url>https://oss.sonatype.org/service/local/staging/deploy/maven2/
	</url>
	</repository>
</distributionManagement>
```

In your local SETTINGS.XML file (should be in m2_repo home), add your Jira account information:
```
<settings>
	...
	<servers>
		<server>
			<id>ossrh</id>
			<username>your-jira-id</username>
			<password>your-jira-pwd</password>
		</server>
	</servers>
	...
</settings>
```

Note: the ID element of your servers/server in settings.xml should be identical to the ID element of snapshotRepository and the repository in your POM file.

#### Release 
You will need to add a plugin into your POM.XML for this stage. Basically, the plugin will take all your separate jar files and signed files and then compress them into one single bundle jar, and then deploy to wherever your distributionManagement repository and version point to. 

There are 2 options (`maven-deploy-plugin` and `nexus-staging-maven-plugin`). You can choose either one of these plugins to deploy your package. Just make sure you use the correct command to run the chosen plugin. 
  
- **Maven Deploy Plugin** 
```
<build>
	<plugins>
		<plugin>
			<artifactId>maven-deploy-plugin</artifactId>
			<version>2.8.2</version>
			<executions>
					<execution>
						<id>default-deploy</id>
						<phase>deploy</phase>
							<goals>
								<goal>deploy</goal>
							</goals>
					</execution>
			</executions>
		</plugin>
	...
	</plugins>
...
</build>
```

- **Nexus Staging Plugin**
```
<project>
		...
		<build>
				<plugins>
            <plugin>
                <groupId>org.sonatype.plugins</groupId>
                <artifactId>nexus-staging-maven-plugin</artifactId>
                <version>1.6.7</version>
                <extensions>true</extensions>
                <configuration>
                    <serverId>ossrh</serverId>
                    <nexusUrl>https://oss.sonatype.org/</nexusUrl>
                    <autoReleaseAfterClose>true</autoReleaseAfterClose>
                </configuration>
            </plugin>
            ...
        </plugins>
    </build>
		...
</project>
```

After adding your choice of plugin, simply run the deploy stage. 
After a while (either immediately or maximum 2 hours), you will be able to find your packages on the [Nexus Repository Manager](https://oss.sonatype.org/#nexus-search;quick~)
/Screen Shot 2019-06-25 at 13.44.53.png

**Note**: Even though you can find your snapshot version on the website, that version is NOT synchronized to the Central Repository 
> "SNAPSHOT versions are not synchronized to the Central Repository. If you wish your users to consume your SNAPSHOT versions, they would need to add the snapshot repository to their Nexus Repository Manager, settings.xml, or pom.xml. Successfully deployed SNAPSHOT versions will be found in https://oss.sonatype.org/content/repositories/snapshots"

### What to Do If Something Goes Wrong

###### Step 1: Undo the release:

git reset –hard HEAD~1 (You may have to do it a second time, depending upon when the error occurred.)

git reset –hard HEAD~1

###### Step 2: Delete the tag.

git tag -d tagName

git push origin :refs/tags/tagName


