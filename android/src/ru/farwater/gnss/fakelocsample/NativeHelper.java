//
// NativeHelper.java
//
// Use javah utility for generating header file:
//    $ pwd
//    ../yourApplication/android/src
//    $ javah ru.farwater.gnss.fakelocsample.NativeHelper
//    $ mv *.h ../.. or to your source directory

package ru.farwater.gnss.fakelocsample;

public class NativeHelper {
    public native static void invokeVoidMethod(int arg);
}
