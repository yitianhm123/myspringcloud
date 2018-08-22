package com.estate.mapper.user;

import com.estate.model.user.CsUser;
import com.estate.model.user.CsUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CsUserMapper {
    int countByExample(CsUserExample example);

    int deleteByExample(CsUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CsUser record);

    int insertSelective(CsUser record);

    List<CsUser> selectByExample(CsUserExample example);

    CsUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CsUser record, @Param("example") CsUserExample example);

    int updateByExample(@Param("record") CsUser record, @Param("example") CsUserExample example);

    int updateByPrimaryKeySelective(CsUser record);

    int updateByPrimaryKey(CsUser record);
}