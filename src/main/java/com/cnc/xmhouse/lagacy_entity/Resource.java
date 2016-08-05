package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by linsy1 on 2016/7/25.
 */
//@Entity
public class Resource {
    private long id;
    private Integer courseId;
    private String source;


    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Column(name = "course_id")
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }


    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (id != resource.id) return false;
        if (courseId != null ? !courseId.equals(resource.courseId) : resource.courseId != null) return false;
        if (source != null ? !source.equals(resource.source) : resource.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }

    public Resource(){}
    public Resource(Integer courseId, String source) {
        this.courseId = courseId;
        this.source = source;
    }
}
