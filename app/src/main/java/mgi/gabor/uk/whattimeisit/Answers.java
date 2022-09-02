package mgi.gabor.uk.whattimeisit;

class Answers {

    private int hour;
    private int minute;
    private int degreeM;


    public Answers() {

    }

    public int getDegreeH() {
        int degreeH = hour * 30;
        degreeH = (int) (degreeH + degreeM * 0.083333);
        return degreeH;
    }

    public int getDegreeM() {
        degreeM = minute * 6;
        return degreeM;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String makeText() {
        String text;

        if (hour < 10) {
            text = "0" + hour + ":";
        } else {
            text = hour + ":";
        }

        if (minute < 10) {
            text = text + "0" + minute;
        } else {
            text = text + minute;
        }

        return text;
    }
}
