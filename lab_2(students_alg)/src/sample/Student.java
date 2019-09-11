package sample;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String surname;
    private int group;
    private int markMath;
    private int markEP;

    public Student(String surname, int group, int markMath, int markEP) {
        this.surname = surname;
        this.group = group;
        this.markMath = markMath;
        this.markEP = markEP;
    }

    public String getSurname(){
        return surname;
    }

    public double getAverageMark(){
        return ((double)(markMath + markEP))/2;
    }

}
