package com.cesgroup.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.cesgroup.auth.user.entity.User;
import com.cesgroup.core.utils.CSRFTokenManager;

/**
 * 未登录，登陆失效等过滤器
 * 
 * @author 国栋
 *
 */
public class SecurityFilter implements Filter
{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String uri = req.getRequestURI();
		if (uri.contains("/druid"))
		{
			chain.doFilter(request, response);
			return;
		}
		User user = (User) session.getAttribute("CURRENTUSER");
		String requestToken = CSRFTokenManager.getTokenFromRequest(req);

		if (user != null)
		{
			boolean isAjaxRequest = isAjaxRequest(req);
			String solidToken = CSRFTokenManager.getTokenForSession(session);
			boolean isEqual = StringUtils.equals(solidToken, requestToken);
			if (isAjaxRequest)// 如果是ajax请求，需要特殊处理。
			{
				if (!isEqual)
				{
					// // 踢回登陆
					rep.setStatus(300469);
					
					return;
				}
			}
		}
		else if (StringUtils.isNotBlank(requestToken))
		{
			rep.setStatus(300468);
			return;
		}
		else if (user == null && "".equals(requestToken))
		{
			rep.setStatus(300467);
			return;
		}
		chain.doFilter(request, response);
	}

	public static boolean isAjaxRequest(HttpServletRequest request)
	{
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	@Override
	public void destroy()
	{
	}

}
