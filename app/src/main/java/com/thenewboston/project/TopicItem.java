package com.thenewboston.project;

/**
 * Created by Anurata on 26-May-16.
 */

public class TopicItem {
    String topicName;
    int rowId;

    @Override
    public String toString() {
        return "TopicItem{" +
                "Topic='" + topicName + '\'' +
                ", rowId=" + rowId +
                '}';
    }

    public String getTopicName()
    {
        return topicName;
    }

    public int getRowId()
    {
        return rowId;
    }

    public TopicItem(String topicName,int rowId) {
        this.topicName = topicName;

        this.rowId = rowId;
    }

    public TopicItem(String topicName) {
        this.topicName = topicName;

    }

}
