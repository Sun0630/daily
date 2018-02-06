// IOnNewBookArrived.aidl
package tech.sx.aidl;

// Declare any non-default types here with import statements

import tech.sx.aidl.Book;

interface IOnNewBookArrivedListener {
    //新书入库
   void onNewBookArrived(in Book book);
}
