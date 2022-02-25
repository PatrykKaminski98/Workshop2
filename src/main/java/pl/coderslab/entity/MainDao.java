package pl.coderslab.entity;

public class MainDao {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        //User user = new User("Sandra","sandra@coderslab.pl","sania");
        // User user1 = new User("Jarosław","jarosław@wp.pl","jarko");
        //User user2 = new User("Remigiusz","remigiusz@interia.pl","remas");
        //User user3 = new User("Patryk","patryk@gmail.com","patryk");
        //User user4 = new User("Mariusz","mariusz@o2.pl","mario");

        // testowanie metody create
        //userDao.create(user);
        //userDao.create(user1);
        // userDao.create(user2);
        // userDao.create(user3);
        // userDao.create(user4);

        // testowanie metody read
       // User userFromDatabase = userDao.read(2);
       // User.printUserInfo(userFromDatabase);

        
        // testowanie metody update
        //User usertoUpdate = userDao.read(1);
        //usertoUpdate.setUserName("Nowa nazwa");
        //usertoUpdate.setEmail("nowy email");
        //usertoUpdate.setPassword("nowe hasło");
       // userDao.update(usertoUpdate);
        //User.printUserInfo(usertoUpdate);

        //testowanie metody delete
        //userDao.delete(4);

        User[] users = userDao.findAll();
        for (User user : users) {
            User.printUserInfo(user);
        }
    }
}
