package repository;

/**
 * Created by Dragos on 3/23/2017.
 */

import model.User;
import java.sql.*;
import java.util.ArrayList;

public class UserManager
{

    private ArrayList<User> users=new ArrayList<>();
    private Statement statement;
    private Connection connection;

    public UserManager()
    {
        try {
            String url = "jdbc:mysql://localhost:3306/flights";
            String user = "root";
            String password = "";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(url, user, password);

                statement = connection.createStatement();
                statement.execute("USE flights");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String query = "select * from users";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String username = result.getString("User");
                String passw = result.getString("Password");
                User us = new User(username,passw);

                users.add(us);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        System.out.println(users);
    }

    public int autentificare(String usr, String passw)
    {
        System.out.println(users);
        String usr2 = null;
        String passw2 = null;
        for (User i : users)
            if (i.getUser().compareTo(usr) == 0)
            {
                usr2 = i.getUser();
                passw2 = i.getParola();
                break;
            }
        if (usr2!=null && !usr2.isEmpty())
            if (passw.compareTo(passw2) == 0)
                return 1;
            else
                return 0;
        else
        {
            return 0;
        }
    }

}
