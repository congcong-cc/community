package life.majiang.community.service;

import life.majiang.community.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createOrUpdate(User user);
}
