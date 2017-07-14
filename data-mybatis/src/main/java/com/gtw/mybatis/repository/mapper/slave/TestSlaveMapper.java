package com.gtw.mybatis.repository.mapper.slave;

import com.gtw.mybatis.domain.Trans;
import com.gtw.mybatis.domain.User;

import java.util.List;

/**
 * Created by gaotingwang on 2017/7/14.
 */
public interface TestSlaveMapper {
    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
