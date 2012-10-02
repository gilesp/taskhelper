Hakken
======

Hakken is a dynamic Android application that allows for "wizard" style applications to be defined using JSON and synced with the device in the form of a ToDo list. There is also a server side component that handles the synchronisation and data storage.

It's essentially a dynamic mobile workflow application, useful for managing jobs produced by other systems.

Building
========
The project uses Maven to build things, and takes the form of a multi module maven project.

Development was done using Eclipse, and guidelines found here: http://www.workreloaded.com/2011/09/android-build-automation/ were followed to get Eclipse, ADT and maven playing nicely together. 

An unsigned version of the apk will be built and packaged in the server war file automatically.

Releasing
=========
In order to package the apk for release, you need to configure a few things

First, create your key following the instructions at http://developer.android.com/guide/publishing/app-signing.html#cert

Then create a profile like this

    <settings>
    <profiles>
        <profile>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <sign.keystore>/absolute/path/to/your.keystore</sign.keystore>
                <sign.alias>youralias</sign.alias>
                <sign.keypass>keypass</sign.keypass>
                <sign.storepass>storepass</sign.storepass>
            </properties>
        </profile>
    </profiles>
    </settings>

in your settings.xml file in ~/.m2

Once this is done, you can create a production version of the apk with the following command:

    mvn clean install -P release

which will in turn sign and zipalign the apk.

