package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            System.out.println("Pomyślnie dodano użytkownika do bazy");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()){
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(username, email, password);
                user.setId(userId);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("W bazie danych nie ma użytkownika o podanym id...");
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()){
            PreparedStatement statement =
                    conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()){
            PreparedStatement statement =
                    conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        User[] usersArray = new User[0];
        try (Connection conn = DbUtil.getConnection()){
            PreparedStatement statement =
                    conn.prepareStatement(FIND_ALL_USERS_QUERY);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(username,email,password);
                user.setId(userId);
                usersArray = addToArray(user, usersArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersArray;
    }

    public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

}
