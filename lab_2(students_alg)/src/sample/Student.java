package sample;

public class Student {
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
