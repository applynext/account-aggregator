package com.dragon.mvnbook.account.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dragon.mvnbook.account.service.AccountService;
import com.dragon.mvnbook.account.service.AccountServiceException;

@SuppressWarnings("serial")
public class CaptchaImageServlet extends HttpServlet {
	private ApplicationContext context; // Spring的ApplicationContext

	@Override
	/**
	 * 首先初始化ApplicationContext
	 */
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取key参数
		String key = request.getParameter("key");
		// 检查key是否为空
		if (key == null || key.length() == 0) {
			// 返回HTTP400错误，请求不合法
			response.sendError(400, "No Captcha Key Found");
		} else {
			// 获取Spring Bean并强制类型转换
			AccountService service = (AccountService) context
					.getBean("accountService");

			try {
				// 设置response格式为image/jpeg
				response.setContentType("image/jpeg");
				// 将产生的字节流写入到Servlet的输出流中
				OutputStream out = response.getOutputStream();
				out.write(service.generateCaptchaImage(key));
				out.close();
			} catch (AccountServiceException e) {
				response.sendError(400, e.getMessage());
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
