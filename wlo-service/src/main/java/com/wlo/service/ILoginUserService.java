package com.wlo.service;

import com.baomidou.mybatisplus.service.IService;
import com.wlo.model.LoginUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ck
 * @since 2017-12-01
 */
public interface ILoginUserService extends IService<LoginUser> {

	public boolean updateValue(LoginUser user);

	boolean insert(LoginUser user);

	void update(Integer id, String name, String password, String phone);

	boolean delete(Integer id);
	LoginUser getLoginUserById2(Integer id);

	List<LoginUser> getLoginUserListById(Integer id);

	List<LoginUser> getListBySQL();

	LoginUser getLoginUserByPhoneAndName(String phone, String name);

	LoginUser getLoginUserByPhoneAndPassword(String phone, String password);

	LoginUser getLoginUserByNameAndPassword(String name, String password);

	LoginUser getLoginUserById(Integer id);

	List<LoginUser> getLoginUserListiByEqId(Integer id);

	List<LoginUser> getLoginUserListiByWhereId(Integer id);

	List<LoginUser> getLoginUserListiByAndId(Integer id);

	LoginUser getLoginUserByandName(String name);

	LoginUser getLoginUserByandPhone(String phone);

}
