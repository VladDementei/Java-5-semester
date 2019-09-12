package sample;

import java.io.Serializable;

public class BasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int group;
    private double averageMark;

    public BasicInfo(int group) {
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
