package com.thenewboston.project;

/**
 * Created by Anurata on 26-May-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHelper extends SQLiteOpenHelper {

    private String TAG ="DBHelper";

    public static String DATABASE_NAME ="project.db";
    public static String TABLE_NAME = "Topics";
    public static String TOPIC_NAME = "topicName";
    public static String ROW_ID = "topicId";

    public static String TABLE_CARD = "Flashcards";
    public static String CARD_NAME = "cardName";
    public static String PATH = "path";
    public static String SERIAL= "serial";
    public static String TABLE_LESSON = "Lessons";

    public static ArrayList<String> tables = new ArrayList<String>();
    public String[] allTable = new String[] { "Topics", "Lessons", "Flashcards", "Animals and insects1","Exercises"};


    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        tables.addAll(Arrays.asList(allTable));
        Log.d(TAG,TABLE_NAME);
        //String query = "CREATE TABLE " + TABLE_NAME + " IF NOT EXISTS ( " +
        //        TOPIC_NAME + " TEXT, " + ROW_ID + " INTEGER PRIMARY KEY ) ";
        //db.execSQL(query);
        //db.execSQL("create table Topics (topicName TEXT, topicId integer primary key)");
        Log.d(TAG, "table created successfully");
    }

    public void createLessonTable(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String createQuery = "create table '" + name + "' (serial integer primary key autoincrement, path TEXT)";
        db.rawQuery(createQuery, null);
        tables.add(name);
    }
    public void insertDetails(DetailsItem newItem,String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();

        contentValue.put(PATH,newItem.path);
        long rowId = db.insert(table,null,contentValue);

        if(rowId!=-1){
            Log.d(TAG,"row inserted");
        }
        else{
            Log.d(TAG,"row not inserted");
        }
        db.close();
    }

    public void insertCard(CardItem newItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(CARD_NAME,newItem.getCardName());
        contentValue.put(PATH,newItem.getPath());
        contentValue.put("mostHit",newItem.mostHit);
        long rowId = db.insert(TABLE_CARD,null,contentValue);

        if(rowId!=-1){
            Log.d(TAG,"row inserted");
        }
        else{
            Log.d(TAG,"row not inserted");
        }
        db.close();
    }

    public void insertLesson(LessonItem newItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(ROW_ID,newItem.topicId);
        contentValue.put("lessonId",newItem.lessonName);
        contentValue.put(PATH,newItem.path);
        contentValue.put("mostHit",newItem.count);
        long rowId = db.insert("Lessons",null,contentValue);

        if(rowId!=-1){
            Log.d(TAG,"row inserted");
        }
        else{
            Log.d(TAG,"row not inserted");
        }
        db.close();
    }

    public ArrayList<TopicItem> retrieveData() {
        ArrayList<TopicItem> allItems = new ArrayList<TopicItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int rowId = cursor.getInt(cursor.getColumnIndex(ROW_ID));
            String topicName = cursor.getString(cursor.getColumnIndex(TOPIC_NAME));
            TopicItem newItem = new TopicItem(topicName,rowId);
            allItems.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return allItems;
    }

    public ArrayList<QnItem> retrieveQn(String lesson) {
        ArrayList<QnItem> allItems = new ArrayList<QnItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Exercises ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String less = cursor.getString(cursor.getColumnIndex("lesson"));
            if(less.equals(lesson))
            {
                String qns = cursor.getString(cursor.getColumnIndex("qn"));
                String path = cursor.getString(cursor.getColumnIndex(PATH));
                String ans = cursor.getString(cursor.getColumnIndex("ans"));
                String correct=cursor.getString(cursor.getColumnIndex("correct"));
                QnItem newItem = new QnItem(less,qns,path,ans,correct);
                allItems.add(newItem);

            }
            cursor.moveToNext();
        }
        cursor.close();
        return allItems;
    }

    public ArrayList<LessonItem> getLessonName(int topicId)
    {
        ArrayList<LessonItem> allItems = new ArrayList<LessonItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Lessons ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int rowId = cursor.getInt(cursor.getColumnIndex(ROW_ID));
            String lessonName = cursor.getString(cursor.getColumnIndex("lessonId"));
            String path = cursor.getString(cursor.getColumnIndex(PATH));
            int count = cursor.getInt(cursor.getColumnIndex("mostHit"));
            if(topicId==rowId)
            {
                LessonItem newItem = new LessonItem(rowId,lessonName,path,count);
                allItems.add(newItem);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return allItems;
    }

    public String findLesson(String table,int count)
    {
        String selectQuery = "SELECT  * FROM '" + table + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int rowNo = cursor.getInt(cursor.getColumnIndex(SERIAL));
            String path = cursor.getString(cursor.getColumnIndex(PATH));
            if(rowNo==count)
            {
                StringTokenizer multiTokenizer = new StringTokenizer(path, "\\.");
                while (multiTokenizer.hasMoreTokens())
                {
                    if(multiTokenizer.nextToken().equals("drawable"))
                    {
                        return multiTokenizer.nextToken();
                    }
                    //System.out.println(multiTokenizer.nextToken());
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }

    public int getLessonRow(String table)
    {
        String selectQuery = "SELECT  * FROM '" + table + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int LessonRowCount=0;
        while(!cursor.isAfterLast()){
            LessonRowCount++;
            cursor.moveToNext();
        }
        cursor.close();
        return LessonRowCount;
    }

    public String findPath(String card)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from Flashcards ";
        //return query;
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        String path=null;
        while(!cursor.isAfterLast()){
            String cardName = cursor.getString(cursor.getColumnIndex(CARD_NAME));
            path = cursor.getString(cursor.getColumnIndex(PATH));
            if(card.toLowerCase().equals(cardName))
            {
                return path;
            }
            cursor.moveToNext();
        }
        cursor.close();
        //return path;
        return "No such item found.";
    }

    public String getAns(String less, String qstn)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from Exercises ";
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String  lesson= cursor.getString(cursor.getColumnIndex("lesson"));
            String question = cursor.getString(cursor.getColumnIndex("qn"));
            String answer= cursor.getString(cursor.getColumnIndex("correct"));
            if(lesson.equals(less) && question.equals(qstn))
            {
                return answer;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }

    private List<String> Parse(String str)
    {
        List<String> output=new ArrayList<>();
        Matcher match= Pattern.compile("[0-9]+|[a-z]+|[A-Z]+").matcher(str);
        while(match.find())
        {
            output.add(match.group());
        }
        return output;
    }

    public int updateCard(CardItem newItem)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PATH, newItem.getPath());
        values.put("mostHit",newItem.mostHit);
        return db.update(TABLE_CARD, values, "cardName = ?",
                new String[] { newItem.getCardName()});
    }

    public void updateMostHit(String table, String element)
    {
        List<String> newOut = Parse(element);

        String query = "select * FROM " + table ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String upQ= null;

        int count=0;
        while(!cursor.isAfterLast()){
            if(table.equals("Flashcards")) {
                String name= cursor.getString(cursor.getColumnIndex(CARD_NAME));
                if(name.equals(element)) {
                    count = cursor.getInt(cursor.getColumnIndex("mostHit"));
                    upQ = "update " + table + " set mostHit = " + (count + 1) + " where cardName= '" + element + "'";
                    break;
                }
            }
            else if(table.equals("Lessons"))
            {
                int topicid = cursor.getInt(cursor.getColumnIndex("topicId"));
                String lessonid=cursor.getString(cursor.getColumnIndex("lessonId"));
                String lname=newOut.get(0);
                int lid=Integer.parseInt(newOut.get(1));
                if(topicid==lid && lessonid.equals(lname))
                {
                    count= cursor.getInt(cursor.getColumnIndex("mostHit"));
                    upQ = "update "+ table + " set mostHit = " + (count+1) + " where topicId = " + newOut.get(1) +
                            " and lessonId = '" + newOut.get(0) + "'";
                    break;
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.rawQuery(upQ,null);
    }

    public ArrayList<CardItem> retrieveCard(){
        ArrayList<CardItem> allItems = new ArrayList<CardItem>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CARD + " order by mostHit desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String cardName = cursor.getString(cursor.getColumnIndex(CARD_NAME));
            String path = cursor.getString(cursor.getColumnIndex(PATH));
            int rowId = cursor.getInt(cursor.getColumnIndex("mostHit"));
            CardItem newItem = new CardItem(cardName,path,rowId);
            allItems.add(newItem);
            cursor.moveToNext();
        }
        cursor.close();
        return allItems;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS Things2");

        // Create tables again
//        onCreate(db);
    }
}
