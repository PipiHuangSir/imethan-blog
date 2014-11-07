package com.the3.web.console.security;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.util.WebUtils;

import com.the3.base.web.SearchFilter;
import com.the3.base.web.SuperController;
import com.the3.dto.web.WebReturnDto;
import com.the3.entity.security.Resource;
import com.the3.service.security.ResourceService;
import com.the3.utils.JsonUtils;

/**
 * ResourceController.java
 *
 * @author Ethan Wong
 * @time 2014年3月17日下午10:12:07
 */
@Controller
@RequestMapping("/console/security/resource")
public class ResourceController extends SuperController {
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="", method = {RequestMethod.GET,RequestMethod.POST})
	public String index(Model model, ServletRequest request) {
		return "console/security/resource";
	}
	
	@ResponseBody
	@RequestMapping(value="json",method=RequestMethod.POST)
	public String json(){
		List<Resource> resource = resourceService.getRootResource();
		return JsonUtils.writeValueAsString(resource);
	}
	
//	@RequestMapping(value="/{page}/{size}", method = {RequestMethod.GET,RequestMethod.POST})
//	public String list(Model model, ServletRequest request, @PathVariable int page, @PathVariable int size) {
//		Map<String,Object> parameters = WebUtils.getParametersStartingWith(request, SearchFilter.prefix);
//		
//		page = page >=0 ? page : defaultPage;
//		size = size >0 ? size : defaultSize;
//		
//		Page<Resource> result = resourceService.getPage(parameters,new PageRequest(page,size,Direction.DESC,"createTime"));
//		model.addAttribute("result", result);
//		
//		
//		return "console/security/resource";
//	}


//	@RequestMapping(value="/input/{isRoot}", method = RequestMethod.GET)
//	public String input(Model model,@PathVariable String isRoot) {
//		System.out.println("------------isRoot:"+isRoot);
//		model.addAttribute("isRoot", isRoot);
//		return "console/security/resource-input";
//	}
	
	
	@ResponseBody
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public WebReturnDto save(@ModelAttribute("resource") Resource resource, BindingResult result,ServletRequest request) {
		
		System.out.println("----------resource:"+resource);
		System.out.println("----------name:"+request.getParameter("name"));
		System.out.println("----------parentId:"+request.getParameter("parentId"));
		if(StringUtils.isBlank(request.getParameter("parentId"))){
			resource.setRoot(true);
		}else{
			String parentId = request.getParameter("parentId");
			resource.setParent(new Resource(Long.valueOf(parentId.trim())));
		}
		
		boolean isSuccess = true;
		String message = "添加成功。";
		if(result.hasErrors()){
			isSuccess = false;
			message = "添加失败";
		}else{
			isSuccess = resourceService.saveOrModify(resource).isSuccess();
		}
		
		return new WebReturnDto(isSuccess,message);
	}

	public String view(Model model, String id, ServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}


	public String forModify(Model model, String id, int page, int size,
			ServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}


//	@RequestMapping(value="/delete/{id}/{page}/{size}", method = {RequestMethod.POST,RequestMethod.GET})
//	public String delete(Model model,@PathVariable String id,@PathVariable int page,@PathVariable int size,
//			RedirectAttributesModelMap redirectAttributesModelMap,
//			ServletRequest request) {
//		boolean isSuccess = resourceService.deleteById(id);
//		String message = "";
//		if(isSuccess){
//			message = "删除成功。";
//		}else{
//			message = "删除失败。";
//		}
//		redirectAttributesModelMap.addFlashAttribute("WebReturnDto", new WebReturnDto(isSuccess,message));
//		return "redirect:/console/security/resource/"+page+"/"+size;
//	}


}