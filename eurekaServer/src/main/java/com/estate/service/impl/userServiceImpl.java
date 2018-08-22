package com.estate.service.impl;

import com.estate.mapper.user.TUserMapper;
import com.estate.model.user.TUser;
import com.estate.model.user.TUserExample;
import com.estate.service.UserService;
import com.estate.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.ws.util.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.Response;
import java.util.List;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private TUserMapper userMapper;


    @Override
    public Result<List<TUser>> queryListUser() throws Exception {
        Result<List<TUser>> result = new Result<>();
        PageHelper.startPage(1,10);
        TUserExample userExample = new TUserExample();
//        userExample.createCriteria();
        userExample.setOrderByClause("user_id");
        List<TUser> users= userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new Exception("用户不存在");
        }
        PageInfo pageInfo = new PageInfo(users);
        result.setCode("00000");
        result.setData(pageInfo.getList());
        result.setMessage("成功");
        return result;
    }

    @Override
    public Result<TUser> queryUserByNameAndPassword(String userName, String passwd) {
        Result<TUser> result = new Result<>();
        TUserExample tUserExample =new TUserExample();
        tUserExample.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(passwd);
        List<TUser> users =  userMapper.selectByExample(tUserExample);
        result.setData(users.get(0));
        result.setMessage("成功");
        result.setCode("00000");
        return result;
    }
}
