package com.estate.service;

import com.estate.model.user.TUser;
import com.estate.util.Result;

import javax.xml.ws.Response;
import java.util.List;


public interface UserService {

    Result<List<TUser>> queryListUser() throws Exception;

    Result<TUser> queryUserByNameAndPassword(String userName,String passwd);
}
