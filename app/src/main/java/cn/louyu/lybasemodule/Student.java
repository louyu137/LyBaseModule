package cn.louyu.lybasemodule;

import java.util.Date;

/**
 * Created by sdj003 on 2018/7/11.
 */

public class Student {

    private Long id; //id
    private String name; //姓名
    private int age; //年龄
    private String sex; //性别
    private Date birthday; //生日
    private String address; //住址
    private String irthplace; //籍贯

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIrthplace() {
        return irthplace;
    }

    public void setIrthplace(String irthplace) {
        this.irthplace = irthplace;
    }

}
