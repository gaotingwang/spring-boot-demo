package com.gtw.mybatis.repository.mapper.master;

import com.gtw.mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by gaotingwang on 2017/7/14.
 */
@Mapper
public interface TestMasterMapper {
    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);

}
