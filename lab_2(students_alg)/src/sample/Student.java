package sample;

import java.io.Serializable;

public class Student extends BasicInfo implements Serializable {
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

    @Override
    public double getAverageMark(){
        return ((double)(markMath + markEP))/2;
    }

}
