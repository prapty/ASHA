package com.thenewboston.project;

/**
 * Created by Anurata on 07-Jun-16.
 */
public class QnItem {
    String lessonName;
    String question;
    String path;
    String answer;
    String correctA ;

    @Override
    public String toString() {
        return path;
    }


    public QnItem(String lessonName,String question,String path, String answer, String correctA) {
        this.lessonName = lessonName;
        this.path = path;
        this.answer=answer;
        this.question=question;
        this.correctA=correctA;
    }
}
