// IBookManager.aidl
package tech.sx.slidingmenu;

// Declare any non-default types here with import statements
import tech.sx.slidingmenu.Book;

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
}
