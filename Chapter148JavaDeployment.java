//// 2023/04/22 // 10:26 //

//// Chapter 148: Java deployment

// There are a variety of technologies for "packaging" Java applications, webapps and so forth, for deployment to the
// platform on which they will run. They range from simple library or executable JAR files, WAR and EAR files, through to
// installers and self-contained executables.

class Chapter148JavaDeployment {

    //// Section 148.1: Making an executable JAR from the command line

    // To make a jar, you need one or more class files. This should have a main method if it is to be run by a double click/

    // For this example, we will use:
    import javax.swing .*;
    import java.awt.Container;

    public class HelloWorld {

        public static void main(String[] args) {
            JFrame f = new JFrame("Hello, World");
            JLabel label = new JLabel("Hello, World");
            Container cont = f.getContentPane();
            cont.add(label);
            f.setSize(400, 100);
            f.setVisible(true);
            f.setDefaultCloseOperations(JFrame.EXIT_ON_CLONSE);\
        }

    }

    // It has been named HelloWorld.java

    // Next, we want to compile this program.

    // You may use any program you want to do this. To run from the command line, see the documentation
    // on compiling and running your first java program.

    // Once you have HelloWorld.class, make a new folder and call it whatever you want.

    // Make another file called manifest.txt and paste into it
    Main-Class: HelloWorld
    Class-Path: HelloWorld.jar

    // Put it in the same folder with HelloWorld.class
    // Use the command line to make your current directory (cd C:\Your\Folder\Path\Here on windwos) your folder.

    // Use Terminal and change directory to the directory (cd /Users/user/Documents/Java/jarfolder on Mac) your
    // folder

    // When that is done, type in java -cvfm HelloWorld.jar manifest.txt HelloWorld.class and press enter. This
    // makes a jar file (in the folder with your manifest and HelloWorld.class) using the .class files specified
    // and named HelloWorld.jar. See the Syntax section for information about the options (like -, and -v).
    // After these steps, go to your directory with the manifest file and you should find HelloWorld.jar
    // Clicking on it should display Hello, World in a text box.


    //// Section 148.2: Creating an UberJAR for an application and its dependencies

    // A common requirement for a Java application is that it can be deployed by copying a single file. For simple
    // applications that depends only on the standard Java SE libraries, this requirement is satisfied by creating a JAR
    // file containing all of the (compiled) application classes.

    // Things are not so straightforward if the application depends on third-party libraries. If you simply put dependency
    // JAR files inside an application JAR, the standard Java class loader will not be able to find the library classes,
    // and your application will not start. Instead, you need to create a single JAR file that contains the application
    // classes and associated resources together with the dependency classes and resources. These need to be organized
    // as a single namespace for the classloader to search.

    // The such a JAR file is often referred to as an UberJAR.

    // Creating an UberJAR using the "jar" command

    // The procedure for creating an UberJAR is straight-forward. (I will use Linux commands for simplicity.
    //  The commands should be identical for Mac OS, and similar for Windows.)

    // 1. Create a temporary directory, and change directory to it.
    // $ mkdir tempDir
    // # cd tempDir
    // 2. For each dependent JAR file, in the reverse order that they need to appear on the application's classpath,
    // used for the jar command yo unpack the JAR into the temporary directory.
    // $ jar -xf <path/to/file.jar>
    // Doing this for multiple JAR files will *overlay* contents of the JARs.
    // 3. Copy the application classes from the built tree into the temporary directory.
    // $ cp -r path/to/classes .
    // 4. Create the UberJAR from the contents of the temporary directory:
    // $ jar -cf ../myApplication.jar
    // If you are creating an executable JAR file, include the appropriate MANIFEST.MF as described here.

    // Creating an UberJAR using Maven

    // If your project is built using Maven, you can get it to create an UberJAR using either the "maven-assembly" or
    // "maven-shade" plugins. See the Maven Assembly topic (in the Maven documentation) for details.

    // The advantages and rawbacks of UberJARs

    // Some of the advantages of UberJARs are self-evident:
    // * An UberJAR is easy t odistribute.
    // * You cannot break the library dependencies for an UberJAR, since the libraries are self-contained.

    // In addition, if you use an appropriate tooling to create the UberJAR, you will have the option of excluding
    // library classes that are not used from the JAR file. However, that is typically done by static analysis of the
    // classes. If your application uses reflection, annotation processing and similar techniques, you need to
    // be careful that classes are not excluded incorrectly.

    // * UberJARs also have some disadvantages:
    // * If you have lots of UberJARs with the same dependencies, then each one will contain a copy of the
    // dependencies.
    // * Some open source libraries have licenses which may preclude 1 their use in an UberJAR.

    // 1 - Some open source library licenses allow you to sue the library only of the end-user is able to replace
    // one version of the library with another. UberJARs can make replcaement of version dependencies difficult.

    //// Section 148.3: Creating JAR, WAR and EAR files

    // The JAR, WAR and EAR file types are fundamentally ZIP files with a "manifest" file and (for WAR and EAR files) a
    // particular internal directory / file structure.

    // The recommended way to create these files is to use a Java-specific build tool which "understands" the
    // requirements for the respective file types. If you don't use a build tool, then IDE "export" is the next option
    // to try.

    // (Editorial note: the descriptions of how to create these files are best placed in the documentation for the
    // respecitve tools. Put them there. Please show some self-restraint and DON'T show-hort them into this example!)

    // Creating JAR and WAR files using Maven

    // Creating a JAR or WAR using Maven is simply a matter of putting the correct <packaging> element into
    // the POM file; e.g.
    <packaging>jar</packaging>
    // or
    <packaging>war</packaging>

    // For more details. Maven can be configured to create "executable" JAR files by adding the requisite information
    // about the entry-point class and external dependencies as plugin propetries for the maven jar plugin. There is
    // even a plugin for creating "uberJAR" files that combine an application and its dependencies into a single JAR file.

    // Please refer to the Maven documentation ( http//stackoverflow.com/documentation/maven/topics )
    // for more information

    // Creating JAR, WAR and EAR files using Ant

    // The Ant build tool has separate "tasks" for building JAR, WAR and EAR. Please refer to the Ant documentation (
    // http://stackoverflow.com/documentation/ant/topics ) for more information.

    // Creating JAR, WAR and EAR files using an IDE

    // The three most popular Java IDEs all have build-in support for creating deployment files. The functionality
    // is often described as "exporting".
    // * Eclipse - http://stackoverflow.com/documentation/eclipse/topics
    // * NetBeans - http://stackoverflow.com/documentation/netbeans/topics
    // * Intellij-IDEA - Exporting

    // Creating JAR, WAR and EAR files using the jar command.

    // It is also possible to create these files "by hand" using the jar command. It is simply a matter of assembling
    // a file tree with the correct component files in the correct place, creating a manifest file, and running jar
    // to create the JAR file.

    // Please refer to the jar command Topic ( Creating an modifying JAR files ) for more information


    //// Section 148.4: Introduction to Java Web Start

    // The Oracle Java Tutorials summarize Web Start as follows:
    // Java Web Start software provides the power to launch full-features applications with a single click.
    // Users can download and launch applications, such as a complete spreadsheet program or an internet chat
    // client, without going through lengthy installation procedures.

    // Other advantage of Java Web Start are support for signed code and explicit declaration of platform dependencies,
    // and suport for code caching and deployment of application updates.

    // Java Web Start is also referred to as JavaWS and JAWS. The primary sources of information are:
    // * The Java Tutorials - Lesson: Java Web Start
    // * Java Web Start Guide
    // * Java Web Start FAQ
    // * JNLP Specification
    // * javax.jnlp API Documentation
    // * Java Web Start Developers Site

    // Prerequisites

    // At a high level, Web Start works by disturbing Java applications packaes as JAR files forma remote webserver.
    // The prerequisites are:
    // * A pre-existing Java installation (JRE or JDK) on the target machine where the application is to run.
    // Java 1.2.2 or higher is required:
    // * From Java 5.0 onwards, Web Start support is included in the JDRE / JDK.
    // * For earlier releases, Web Start support is instaleld separately.
    // * The Web Start infrastructure includes some Javascript that can be included in a web page to assist the
    // user to install the necessary software.
    // * The webserver that hosts the software must be accessible to the target machine.
    // * If the user is going to launch a Web Start application using a link in a web page, then:
        // * they need a compatible web browser, and
        // * foe modern (secure) browsers, they need to be told how to tell the browser to allow Java to run...
        //   without compromising web browser security

    // An example JNLP file
    // The following example is intended to illustrate the basic functionality of JNLP.
    <?xml version="1.0" encoding="UTF-8"?>
    <jnlp spec="1.0+" codebase="https://www.example.com/demo"
        href="demo_webstart.jnlp">
        <information>
            <title>Demo</title>
            <vendor>The Example.com Team</vendor>
        </information>
        <resources>
            <!-- Application Resources -->
            <j2se version="1.7+" href="http://java.sun.com/products/autodl/j2se"/>
            <jar href="Demo.jar" main="true"/>
        </resources>
        <application-desc
            name="Demo Application"
            main-class="com.example.jwsdemo.Main"
            width="300"
            height="300">
        </application-desc>
        <update check="background"/>
    </jnlp>

    // As you can see, a JNLP file XML-based, and the information is all contained in the <jnlp> element.
    // * The spec attribute gives the version of the JNLP spec that this file conforms to.
    // * The codebase attribute gives the base URL for resolving relative href URLs in the rest of the file.
    // * The <infromation< element contains metadata for the application including its title, authors,
    // description and help website.
    // * The >resources> element describes the dependencies for the application including the required
    // Java version, OS platform and JAR files.
    // * The <application-desc> (or <applet-desc>) element provides information needed to launch the application.

    // Setting up the web server

    // The webserver must be configured to use application/x-java-jnlp-file as teh MIMEtype for .jnlp files.

    // The JNLP file and the application's JAR files must be installed on the webserver so that they are avaialble
    // using the URLs indicated by the JNLP file.

    // Enabling lnaunch via a web page

    // If the application is to be launched via a web link, the page that contains the link must be created
    // on the webserver.
    // * If you can assume that Java Web Start is already installed on the user's machine, then the web page simply
    // needs to contain a link for launching the application. For example.
    <a href="https://www.example.com/demo_webstart.jnlp">Launch the application</a>
    // * Otherwise, the page should also include some scripting to detect the kind of browser the user is using
    // and request to download and install the required version of Java.

    // NOTE: It is a bad idea to encourage users to encourage to install Java this way, or even to enable Java
    // in their web browsers so that JNLP web page launch will work.

    // Launching Web Start applications form the command line

    // The instructions for launching a Web Start application from the command line are simple.
    // Assuming that the user has a Java 5.0 JRE or JDK installed, they simply need to run this:
    // $ javaws <url>
    // where <url> is the URL for the JNLP file on the remote server

}
