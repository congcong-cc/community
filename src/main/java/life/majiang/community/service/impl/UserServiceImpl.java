package life.majiang.community.service.impl;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void createOrUpdate(User user) {
        User accountUser = userMapper.findByAccountId(user.getAccountId());
        if(accountUser==null){
            userMapper.save(user);
        }else{
            userMapper.updateByAccountId(user);
        }
    }
}
