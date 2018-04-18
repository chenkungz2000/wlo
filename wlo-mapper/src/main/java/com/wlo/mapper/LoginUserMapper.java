package com.wlo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wlo.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ck
 * @since 2017-12-01
 */
@Mapper
public interface LoginUserMapper extends BaseMapper<LoginUser> {

	@Select("select * from login_user")
	List<LoginUser> findListBySQL();

	@Select("select * from login_user where phone=#{phone} and name=#{name}")
	LoginUser findLoginUserByPhoneAndName(String phone, String name);


}
