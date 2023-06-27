package sitemesh;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter{
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/*", "/layout/mainlayout.jsp")
			.addExcludedPath("/admin/*")
			.addExcludedPath("/ajax/*")
			.addExcludedPath("/mem/email*")
			.addExcludedPath("/mypage/newD*")
			.addExcludedPath("/mem/pwChg*");
		
		builder.addDecoratorPath("*/admin/*", "/layout/adminlayout.jsp")
			.addExcludedPath("/admin/login");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String uri = request.getRequestURI();		// 요청된 uri 정보
		if(uri.contains("/admin/product/")) uri = "product";
		else if(uri.contains("/admin/opt/")) uri="opt";
		else if(uri.contains("/admin/stock/")) uri="stock";
		else if(uri.contains("/admin/point/")) uri="point";
		else if(uri.contains("/admin/order/")) uri="order";
		else if(uri.contains("/admin/refund/")) uri="refund";
		else if(uri.contains("/admin/review/")) uri="review";
		else if(uri.contains("/admin/stat/")) uri="stat";
		else if (uri.contains( "/product/productList")) uri = "productList";
		else uri="";
		request.setAttribute("url", uri);	
		super.doFilter(servletRequest, servletResponse, filterChain);	// 다음 프로세스 진행
	}
}
