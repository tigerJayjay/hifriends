package com.easybug.service.login;

import com.easybug.common.Md5Utils;
import com.easybug.dao.CodeGeneratorDao;
import com.easybug.dao.UserDao;
import com.easybug.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginImpl implements ILogin {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CodeGeneratorDao codeDao;

    /**
     * 登陆
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User u =  userDao.selectUserById(user.getuId());
        if(u!=null){
            if(!Md5Utils.verify(user.getPassword(),u.getPassword())){
                return null;
            }
        }
        return u;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(User user) {
        Integer id = codeDao.getNewCode();
        if(id==null){
            return false;
        }
        if(codeDao.updateStatus(id)>0){
            user.setuId(id);
            user.setPassword(Md5Utils.generate(user.getPassword()));
            if(userDao.insertUser(user)>0){
                return true;
            }
        }
      return false;
    }


}
