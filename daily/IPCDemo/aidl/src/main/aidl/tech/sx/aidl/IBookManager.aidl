// IBookManager.aidl
package tech.sx.aidl;

// Declare any non-default types here with import statements
import tech.sx.aidl.Book;

import tech.sx.aidl.IOnNewBookArrivedListener;

interface IBookManager {
   //获取图书列表
  List<Book> getBookList();
    //添加图书
  void addBook(in Book book);
  //注册监听
  void registerListener(IOnNewBookArrivedListener listener);
  //反注册
  void unregisterListener(IOnNewBookArrivedListener listener);
}
