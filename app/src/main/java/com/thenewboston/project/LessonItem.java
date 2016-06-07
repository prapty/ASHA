package com.thenewboston.project;

/**
 * Created by Anurata on 02-Jun-16.
 */
public class LessonItem {
    int topicId;
    String lessonName;
    String path;
    int count;

    //@Override
    //public String toString() {
      //  return path;
    //}

    public String getLessonName()
    {
        return lessonName;
    }

    public String getPath()
    {
        return path;
    }

    public int getCount()
    {
        return count;
    }
    public LessonItem(int topicId,String lessonName,String path,int count) {
        this.lessonName = lessonName;
        this.topicId = topicId;
        this.path = path;
        this.count = count;
    }
}
