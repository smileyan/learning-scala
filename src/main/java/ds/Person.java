package ds;

/**
 * Created by huay on 3/02/2016.
 */
public class Person {
    private String name;
    private int    age;

    public Person(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    public void setName(String name) { this.name = name; }
    public String getName()          { return this.name; }

    public void setAge(int age) { this.age = age; }
    public int getAge()         { return this.age; }
}

//  class Person(var name: String, var age: Int)
//  case class Person(name: String, age: Int)