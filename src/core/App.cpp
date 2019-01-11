#include "App.h"

App::App(int &argc, char **argv) : QApplication(argc, argv)
{
    isRunningFlag=false;

    connect( this, SIGNAL(aboutToQuit()), &core, SLOT(appDeactivation()) );
}


int App::exec()
{
    isRunningFlag=true;
    return QApplication::exec();
}


bool App::isRunning()
{
    return isRunningFlag;
}

