package sample;

import java.io.Serializable;

public class Student extends GroupInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String surname;
    private int markMath;
    private int markEP;

    public Student(String surname, int group, int markMath, int markEP) {
        super(group);
        this.surname = surname;
        this.markMath = markMath;
        this.markEP = markEP;
    }

    public String getSurname(){
        return surname;
    }

    public int getMarkMath() {
        return markMath;
    }

    public int getMarkEP() {
        return markEP;
    }

    @Override
    public double getAverageMark(){
        return ((double)(markMath + markEP))/2;
    }
}
