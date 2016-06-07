package com.thenewboston.project;

/**
 * Created by Anurata on 31-May-16.
 */
public class CardItem {

    String cardName;
    String path;
    int mostHit;

    @Override
    public String toString() {
        return path;
    }

    public String getCardName()
    {
        return cardName;
    }

    public String getPath()
    {
        return path;
    }

    public CardItem(String cardName,String path) {
        this.cardName = cardName;
        this.path = path;
    }

    public CardItem(String cardName,String path, int mostHit) {
        this.cardName = cardName;
        this.path = path;
        this.mostHit=mostHit;
    }
}
