package entities;

import org.junit.Before;

public class User {
    public Integer id;
    public String email;
    public String first_name;
    public String last_name;

    @Before
    public void setUp() throws Exception {

    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
