package model.repository;

import model.domain.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class UserRepository {

    private CopyOnWriteArraySet<User> users;

    private static UserRepository userRepository;

    private UserRepository(){
        users  = new CopyOnWriteArraySet<>();
    }

    public synchronized static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public User find(User user){
        return users.stream().filter(user::equals).findFirst().orElse(null);
    }

    public boolean add(User user){
       return users.add(user);
    }

    public List<User> getAll(){
        return users.stream().collect(Collectors.toList());
    }

    public boolean remove(User user){
        return users.remove(user);
    }

}
