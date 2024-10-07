# readme

ok it works BUTTT its only for the paid version, not the self run one...

trying to get the build from the repos actions

https://github.com/zotero/zotero-android/actions

## first get android sdk working properly

```
sudo apt-get install openjdk-17-jdk -y
wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
unzip ...
cd ... //bin

mkdir -p ~/Android/Sdk/cmdline-tools
mv cmdline-tools  ~/Android/Sdk/cmdline-tools/latest

export ANDROID_SDK_ROOT=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools

sdkmanager "platform-tools"
# sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"
```

## running gradle at the end will then download the deps

```
# setup jdk
# Downloading Java 17.0.12+7 (Zulu) from https://cdn.azul.com/zulu/bin/zulu17.52.19-ca-crac-jdk17.0.12-linux_x64.tar.gz ...
# python
# CPython (3.10.15)
conda deactivate
conda env remove -n antero -y
conda create -n antero python=3.10 -y
conda activate antero
conda install -c conda-forge openjdk=17 -y

# SKIP write pspdfkit key into file to be picked up by gradle (i dont have the key)
# Run echo *** | sed 's/./& /g' > pspdfkit-key.tx
# SKIP they have alot of stuff as secrets then

# download android dependencies
./gradlew androidDependencies --no-configuration-cache

wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip

# deploy to google play internal test pack
./gradlew --no-configuration-cache -PpreDexLibs=false publishInternalReleaseBundle --stacktrace
```

### making it work:

```
# remove play dependencies from app/build...kts
// id("com.github.triplet.play") version "3.7.0"
# and the entire play block

# Task 'publishInternalReleaseBundle' not found in root project 'Zotero' and its subprojects.
# comment out the first line of the build
./gradlew assembleDebug
./gradlew assembleRelease
# app/build/outputs/apk/release/. # or /debug
```

