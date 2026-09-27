#!/bin/bash
set -e

APK_PATH="/workspaces/mbolotv/app/build/outputs/apk/debug/app-debug.apk"
PACKAGE_NAME="ga.mbolo.app"
MAIN_ACTIVITY="ga.mbolo.app.MainActivity"

if [ ! -f "$APK_PATH" ]; then
    echo "🔨 Compilation de l'APK debug..."
    /workspaces/mbolotv/gradlew assembleDebug
fi

echo "🔍 Vérification des appareils connectés :"
adb devices -l

# Sélectionner le premier transport/appareil disponible
FIRST_TRANSPORT=$(adb devices -l | grep -E "device " | head -n 1 | grep -o -E "transport_id:[0-9]+" | cut -d':' -f2)

if [ -z "$FIRST_TRANSPORT" ]; then
    echo "❌ Aucun appareil détecté en mode 'device'."
    exit 1
fi

echo "📡 Transfert de l'APK vers l'appareil (transport_id $FIRST_TRANSPORT)..."
adb -t "$FIRST_TRANSPORT" push "$APK_PATH" /data/local/tmp/app-debug.apk

echo "📲 Installation de l'application..."
adb -t "$FIRST_TRANSPORT" shell pm install -r /data/local/tmp/app-debug.apk

echo "🚀 Lancement de l'application ($PACKAGE_NAME)..."
adb -t "$FIRST_TRANSPORT" shell am start -n "$PACKAGE_NAME/$MAIN_ACTIVITY"

echo "✅ Application installée et lancée avec succès !"

