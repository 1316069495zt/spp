package net.sppan.base.controller.admin;

import net.sppan.base.controller.BaseController;
import net.sppan.base.dao.CompanyDao;
import net.sppan.base.dao.IUserDao;
import net.sppan.base.entity.Company;
import net.sppan.base.entity.User;
import net.sppan.base.license.EncryUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController extends BaseController {


	//解密
	private Boolean desDecode(String str) {
		String t = "";
		//System.out.println("加密后：" + EncryUtil.encrypt(t));
		t = EncryUtil.decrypt(str);
		//System.out.println("解密后：" + t);
		if(t.equals("perpetual license")) {
			return true;
		}else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String nowDate = format.format(date);
			Integer result = EncryUtil.compareDate(t,nowDate);
			if(result == -1) {
				return false;
			}
		}

		return true;
	}
	//加密
	private String desCode(String str) {
		//str为加密的截止日期
		String t = EncryUtil.encrypt(str);
		//System.out.println("加密后：" + t);
		return t;
	}

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private IUserDao userDao;

	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
	public String login() {

		return "admin/login";
	}
	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,ModelMap model
			) {
		try {
			 Subject subject = SecurityUtils.getSubject();
			 UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);

			User byUserName = userDao.findByUserName(username);
			Company one = companyDao.findOne(byUserName.getCompanyId());
			String key = one.getLicense();
			if (desDecode(key)){
				return redirect("/admin/index");
			}else {
				return redirect("/index");
			}
			//return redirect("/admin/index");
		} catch (AuthenticationException e) {
			model.put("message", e.getMessage());
		}
		return "admin/login";
	}
	
	@RequestMapping(value = { "/admin/logout" }, method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return redirect("admin/login");
	}
	
}
