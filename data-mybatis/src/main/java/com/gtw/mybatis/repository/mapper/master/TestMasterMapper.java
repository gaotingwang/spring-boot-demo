package com.gtw.mybatis.repository.mapper.master;

import com.gtw.mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by gaotingwang on 2017/7/14.
 */
@Mapper
public interface TestMasterMapper {
    List<User> getAll();

    /**
     * 通过RowBounds采用的是逻辑分页：将数据库中所有数据全部取出，然后通过Java代码控制分页逻辑。
     */
    List<User> getAll(RowBounds rb);

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);

}
