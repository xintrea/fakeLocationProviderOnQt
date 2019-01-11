#ifndef __MAIN_H__
#define __MAIN_H__

#include <QString>
#include <QByteArray>

#include "core/App.h"
#include "core/Core.h"


// Макрос единой точки обращения к объекту ядра
#define APPCORE static_cast<App*>(qApp)->core

#endif // __MAIN_H__
