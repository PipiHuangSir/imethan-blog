package cn.imethan.service.security;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.imethan.common.repository.SearchFilter;
import cn.imethan.dto.common.ReturnDto;
import cn.imethan.entity.security.User;

/**
 * UserService.java
 *
 * @author Ethan Wong
 * @time 2014年3月16日下午5:00:04
 */
public interface UserService{
	
	/**
	 * 根据用户名获取
	 * @param username
	 * @return
	 */
	public User getByUsername(String username);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	public User getById(Long id);
	
	/**
	 * 获取分页列表
	 * @param filters
	 * @param pageable  new PageRequest(page, size, Direction.DESC, "id")
	 * @return
	 */
	public Page<User> findPage(List<SearchFilter> filters , PageRequest pageable);
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public ReturnDto save(User entity);
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public ReturnDto deleteById(Long id);
	
	/**
	 * 更新用户基本信息
	 * @param user
	 * @return
	 */
	public ReturnDto updateProfile(User user);
	
	/**
	 * 更新头像
	 * @param userId
	 * @param saveFileName
	 */
	public ReturnDto updateAvatar(Long userId, String saveFileName);
	
	/**
	 * 更新密码
	 * @param username
	 * @param password
	 * @return
	 */
	public ReturnDto updatePassword(String username, String password);
	
}