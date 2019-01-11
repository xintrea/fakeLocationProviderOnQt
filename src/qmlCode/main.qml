import QtQuick 2.6
import QtQuick.Window 2.2
import QtQuick.Controls 1.4


Window {
    id: mainWindow

    title: "JniSample"

    visible: true

    width: 256
    height: 256

    Text {
        id: titleLabel

        anchors.horizontalCenter: mainWindow.contentItem.horizontalCenter
        anchors.verticalCenter: mainWindow.contentItem.verticalCenter

        text: "It is JNI sample"
    }

    Rectangle {
        id: setupButton

        width: 640
        height: 250

        anchors.top: titleLabel.bottom
        anchors.horizontalCenter: titleLabel.horizontalCenter

        color: "yellow"

        Text {
            anchors.horizontalCenter: parent.horizontalCenter
            anchors.verticalCenter: parent.verticalCenter

            text: "Setup Location Provider"
        }

        MouseArea {
            anchors.fill: parent

            onClicked: {
                core.setupFakeLocationProvider();
            }
        }
    }

    Rectangle {
        id: updateButton

        width: 640
        height: 250

        anchors.top: setupButton.bottom
        anchors.horizontalCenter: setupButton.horizontalCenter

        color: "brown"

        Text {
            anchors.horizontalCenter: parent.horizontalCenter
            anchors.verticalCenter: parent.verticalCenter

            text: "Update Location Provider"
        }

        MouseArea {
            anchors.fill: parent

            onClicked: {
                core.updateFakeLocationProvider();
            }
        }
    }

}
