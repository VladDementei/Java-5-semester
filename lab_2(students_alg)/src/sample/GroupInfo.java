package sample;

import java.io.Serializable;

public class GroupInfo implements Serializable {
    private static final long serialVersionUID = 2L;
    private int group;
    private double averageMark;

    public GroupInfo(int group) {
        this.group = group;
        this.averageMark = 0.0;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public double getAverageMark(){return averageMark;}

    public int getGroup() {
        return group;
    }
}
