package lk.sliit.appointment.appointment_manager_sliit;

/**
 * Created by User G on 3/29/2016.
 */
public class Appointment {
    private String app_id;
    private String student_id;
    private String date;
    private String time;
    private String title;
    private String description;
    private String st_name;
    private String st_email;
    private String st_year;
    private String st_sem;

    public Appointment(String app_id, String student_id, String date,String time, String title, String description,
                       String st_name, String st_email, String st_year, String st_sem) {
        this.setApp_id(app_id);
        this.setStudent_id(student_id);
        this.setDate(date);
        this.setTime(time);
        this.setTitle(title);
        this.setDescription(description);
        this.setSt_name(st_name);
        this.setSt_email(st_email);
        this.setSt_year(st_year);
        this.setSt_sem(st_sem);
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_email() {
        return st_email;
    }

    public void setSt_email(String st_email) {
        this.st_email = st_email;
    }

    public String getSt_year() {
        return st_year;
    }

    public void setSt_year(String st_year) {
        this.st_year = st_year;
    }

    public String getSt_sem() {
        return st_sem;
    }

    public void setSt_sem(String st_sem) {
        this.st_sem = st_sem;
    }
}
