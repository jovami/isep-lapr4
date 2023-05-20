package eapli.base.course.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.framework.domain.model.AggregateRoot;

@Entity
public class StaffMember implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int staffMemberId;
    @ManyToOne
    private Course course;

    @ManyToOne
    private Teacher member;

    protected StaffMember() {
        // for ORM
    }

    public StaffMember(Course course, Teacher member) {
        this.course = course;
        this.member = member;
    }

    public Course course() {
        return course;
    }

    protected void setCourse(Course course) {
        this.course = course;
    }

    public Teacher member() {
        return member;
    }

    @Override
    public String toString() {
        return "StaffMemberId=" + staffMemberId +
                "\tcourse=" + course +
                "\tmember=" + member.user().username();
    }

    protected void setMember(Teacher member) {
        this.member = member;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StaffMember staffMember = (StaffMember) o;
        return this.course.equals(staffMember.course) && this.member.equals(staffMember.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, member);
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.staffMemberId;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    public int getStaffMemberId() {
        return staffMemberId;
    }

    public void setStaffMemberId(int staffMemberId) {
        this.staffMemberId = staffMemberId;
    }

    public Course getCourse() {
        return course;
    }

    public Teacher getMember() {
        return member;
    }
}
