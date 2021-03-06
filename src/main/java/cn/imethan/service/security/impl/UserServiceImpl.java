package cn.imethan.service.security.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.imethan.common.repository.DynamicSpecifications;
import cn.imethan.common.repository.SearchFilter;
import cn.imethan.dto.common.ReturnDto;
import cn.imethan.entity.security.Role;
import cn.imethan.entity.security.User;
import cn.imethan.repository.jpa.security.RoleRepository;
import cn.imethan.repository.jpa.security.UserRepository;
import cn.imethan.service.security.UserService;

/**
 * UserServiceIMpl.java
 *
 * @author Ethan Wong
 * @time 2014年3月16日下午5:01:34
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	
	private Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User getByUsername(String username) {
		try {
			return userRepository.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Page<User> findPage(List<SearchFilter> filters , PageRequest pageable) {
		Page<User> result = null;
		try {
			Specification<User> spec = DynamicSpecifications.bySearchFilter(filters, User.class);
			result = userRepository.findAll(spec, pageable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public User getById(Long id) {
		try {
			return userRepository.findOne(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto save(User entity) {
		System.out.println("---------------entity:"+entity);
		boolean isSuccess = true;
		String message = "保存成功";
		try {
			//设置角色信息
			Long roleId = entity.getRoleId();
			System.out.println("-----------roleId:"+roleId);
			Role role = roleRepository.findOne(roleId);
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
			entity.setRoles(roles);
			
			if(entity.getId() != null){
				entity.setModifyTime(new Date());
			}
			userRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "保存失败";
		}
		return new ReturnDto(isSuccess,message);
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto deleteById(Long id) {
		boolean isSuccess = true;
		String message = "删除成功";
		try {
			userRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "删除失败";
		}
		return new ReturnDto(isSuccess,message);
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto updateProfile(User user) {
		boolean isSuccess = true;
		String message = "更新成功";
		
		try {
			User userDb = userRepository.findOne(user.getId());
			userDb.setEmail(user.getEmail());
			userDb.setLocate(user.getLocate());
			userDb.setNickname(user.getNickname());
			userDb.setPhone(user.getPhone());
			userDb.setQq(user.getQq());
			userRepository.saveAndFlush(userDb);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "更新失败";
		}
		
		return new ReturnDto(isSuccess,message);
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto updateAvatar(Long userId, String saveFileName) {
		boolean isSuccess = true;
		String message = "更新成功";
		
		try {
			User userDb = userRepository.findOne(userId);
			userDb.setAvatar(saveFileName);
			userRepository.saveAndFlush(userDb);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "更新失败";
		}
		
		return new ReturnDto(isSuccess,message);
	}

	@Override
	@Transactional(readOnly = false)
	public ReturnDto updatePassword(String username, String password) {
		boolean isSuccess = true;
		String message = "更新成功";
		
		try {
			int result = userRepository.updatePassword(username,password);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			message = "更新失败";
		}
		
		return new ReturnDto(isSuccess,message);
	}

}


