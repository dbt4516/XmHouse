package com.cnc.xmhouse.lagacy_entity;

import javax.persistence.*;

/**
 * Created by linsy1 on 2016/7/24.
 */
//@Entity
public class Course {

    private Integer id;
    private String name;
    private int courseType;
    private String outline;
    private String target;
    private String teacher;
    private int isOnline;
    private String imgSource;
    private Integer categoryId;
    private String categoryName;

    public Course(){}

    public Course(String name, int courseType, String outline, String target,
                  String teacher, int isOnline,String imgSource,Integer categoryId,String categoryName) {
        this.name = name;
        this.courseType = courseType;
        this.outline = outline;
        this.target = target;
        this.teacher = teacher;
        this.isOnline = isOnline;
        this.imgSource = imgSource;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "course_type")
    public int getCourseType() {return courseType;}

    public void setCourseType(int courseType) {this.courseType = courseType;}

    @Column(name = "outline")
    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    @Column(name = "target")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Column(name = "teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Column(name = "is_online")
    public int getIsOnline() {return isOnline;}

    public void setIsOnline(int isOnline) {this.isOnline = isOnline;}

    @Column(name = "category_id")
    public Integer getCategoryId() {return categoryId;}

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "img_source")
    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    @Transient
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (courseType != course.courseType) return false;
        if (isOnline != course.isOnline) return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (outline != null ? !outline.equals(course.outline) : course.outline != null) return false;
        if (target != null ? !target.equals(course.target) : course.target != null) return false;
        if (teacher != null ? !teacher.equals(course.teacher) : course.teacher != null) return false;
        if (imgSource != null ? !imgSource.equals(course.imgSource) : course.imgSource != null) return false;
        if (categoryId != null ? !categoryId.equals(course.categoryId) : course.categoryId != null) return false;
        return categoryName != null ? categoryName.equals(course.categoryName) : course.categoryName == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + courseType;
        result = 31 * result + (outline != null ? outline.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + isOnline;
        result = 31 * result + (imgSource != null ? imgSource.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "Course[id:"+getId()+",name:"+getName()+",courseType:"+getCourseType()
                +"outline:"+getOutline()+",target:"+getTarget()+",teacher:"+getTeacher()
                +",isOnline:"+getIsOnline()+",imgSource:"+getImgSource()+",categoryId:"+getCategoryId()
                +"]";
    }
}
