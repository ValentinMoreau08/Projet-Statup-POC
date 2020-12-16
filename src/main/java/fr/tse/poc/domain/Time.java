package fr.tse.poc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Time {
    private @Id
    @GeneratedValue
    Long id;
    private int time;
    private Date date;

    public Time() {
    }

    public Time(int time, Date date){
        this.time=time;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time1 = (Time) o;
        return time == time1.time &&
                Objects.equals(id, time1.id) &&
                date.compareTo(time1.date)==0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, date);
    }
}
