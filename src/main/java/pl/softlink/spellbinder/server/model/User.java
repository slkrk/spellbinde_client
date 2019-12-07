package pl.softlink.spellbinder.server.model;

import pl.softlink.spellbinder.server.security.Security;

public class User {

    private int id;
    private String email;
    private String password;
    private boolean isNew;

    public User(String email, String rawPasword) {
        isNew = true;
        this.email = email;
        this.password = Security.md5(rawPasword);
    }

    public User(String email, String password, int id) {
        this.isNew = false;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public void save() {



    }




}
