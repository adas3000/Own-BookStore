package com.example.mylib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.InstrumentationRegistry;

import com.example.mylib.Data.Book;
import com.example.mylib.sql.SqlManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SqlAndroidTest {

    private Context context;
    private SqlManager sqlManager;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
        sqlManager = new SqlManager(context);
    }
    @Test
    public void packageTest(){
        assertEquals("com.example.mylib", context.getPackageName());
    }

    @Test
    public void sqlTest(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile("@drawable/", options);


        sqlManager.addBookToDb("Hobbit","Tolkien","Two hobbits went somewhere",
              bmp,0,2013,10,15 );

        ArrayList<Book> bookArrayList = sqlManager.getValues(true);
        assertEquals(0,bookArrayList.size());
        bookArrayList = sqlManager.getValues(false);
        assertEquals(1,bookArrayList.size());
    }


    @Test
    public void areValuesGoodTest(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile("@drawable/ic_books.xml", options);

        sqlManager.addBookToDb("Hobbit","Tolkien","Two hobbits went somewhere",
                bmp,0,2013,10,15 );
        sqlManager.addBookToDb("Harry Potter","Rowling","Story of Big Wizard",
                bmp,1,2019,8,17 );

        ArrayList<Book> bookArrayList = sqlManager.getValues(false);

        assertEquals(2,bookArrayList.size());

        //book 1
        assertEquals("Tolkien",bookArrayList.get(0).getAuthor());
        assertEquals("Hobbit",bookArrayList.get(0).getTitle());
        assertEquals("Two hobbits went somewhere",bookArrayList.get(0).getShort_description());
        assertEquals(false,bookArrayList.get(0).isReadenByUser());
        //book 2
        assertEquals("Rowling",bookArrayList.get(1).getAuthor());
        assertEquals("Harry Potter",bookArrayList.get(1).getTitle());
        assertEquals("Story of Big Wizard",bookArrayList.get(1).getShort_description());
        assertEquals(true,bookArrayList.get(1).isReadenByUser());
        assertEquals(2019,bookArrayList.get(1).getFinish_date().year);
        assertEquals(8,bookArrayList.get(1).getFinish_date().month);
        assertEquals(17,bookArrayList.get(1).getFinish_date().day);
    }

    @After
    public void deleteDb(){
        context.deleteDatabase(SqlManager.getDbName());
    }


}
