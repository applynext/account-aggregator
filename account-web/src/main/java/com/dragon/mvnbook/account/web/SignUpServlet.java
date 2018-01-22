package com.dragon.mvnbook.account.web;

import java.io.IOException;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.springframework.context.ApplicationContext;  
import org.springframework.web.context.support.WebApplicationContextUtils;  
  
import com.dragon.mvnbook.account.service.AccountService;  
import com.dragon.mvnbook.account.service.AccountServiceException;  
import com.dragon.mvnbook.account.service.SignUpRequest;  
  
@SuppressWarnings("serial")  
public class SignUpServlet extends HttpServlet {  
    private ApplicationContext context; // 用于获取Spring Bean  
  
    @Override  
    /** 
     * 首先初始化ApplicationContext 
     */  
    public void init() throws ServletException {  
        super.init();  
        context = WebApplicationContextUtils  
                .getWebApplicationContext(getServletContext());  
    }  
  
    @Override  
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        // 获取HTTP POST请求，读取表单中的id，邮箱，用户名，密码，确认密码，验证主键，验证值参数  
        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String name = req.getParameter("name");  
        String password = req.getParameter("password");  
        String confirmPassword = req.getParameter("confirm_password");  
        String captchaKey = req.getParameter("captcha_key");  
        String captchaValue = req.getParameter("captcha_value");  
        // 判断各参数是否为空，存在为空值则返回报错  
        if (id == null || id.length() == 0 || email == null  
                || email.length() == 0 || name == null || name.length() == 0  
                || password == null || password.length() == 0  
                || confirmPassword == null || confirmPassword.length() == 0  
                || captchaKey == null || captchaKey.length() == 0  
                || captchaValue == null || captchaValue.length() == 0) {  
            resp.sendError(400, "Parameter Incomplete.");  
            return;  
        }  
        // 获取名为accountService的bean  
        AccountService service = (AccountService) context  
                .getBean("accountService");  
        // 初始化一个SignUpRequest实例并设置其属性  
        SignUpRequest request = new SignUpRequest();  
        request.setId(id);  
        request.setEmail(email);  
        request.setName(name);  
        request.setPassword(password);  
        request.setConfirmPassword(confirmPassword);  
        request.setCaptchaKey(captchaKey);  
        request.setCaptchaValue(captchaValue);  
        
        String path = req.getContextPath();
    	String basePath = req.getScheme() + "://"
    			+ req.getServerName() + ":" + req.getServerPort()
    			+ path + "/";
    	
        request.setActivateServiceUrl(basePath + "activate"); // 发送账户激活邮件的地址
        // 使用AccountService注册用户  
        try {
            service.signUp(request);  
            resp.getWriter()  
                    .print("Account is created, please check your mail box for activation link.");  
        } catch (AccountServiceException e) {  
            resp.sendError(400, e.getMessage());  
            return;  
        } 
    }
}  