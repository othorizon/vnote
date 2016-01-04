package vnote;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Servlet Filter implementation class vnote
 */
@WebFilter(dispatcherTypes = { DispatcherType.FORWARD, DispatcherType.REQUEST,
		DispatcherType.INCLUDE, DispatcherType.ERROR }, urlPatterns = { "/*" })
public class vnote implements Filter {

	/**
	 * Default constructor.
	 */
	public vnote() {
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		sess.flush();
		sess.clear();
		sf.close();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		String id = url.substring(url.lastIndexOf("/") + 1);
		id=id.toLowerCase();
		//随机生成网址
		if(id.equals("")){
			String str=""; 
			do{			
			for(int i=0;i<3;i++){ 
			 str= str+(char) (Math.random ()*26+'A'); 
			} 
			}while(mydata.getContents().containsKey(str));
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/"
					+ str);	
			return;
		}
		String usrid = "";
		if (request.getParameter(id) != null)
			usrid = request.getParameter(id).toString();
		if (url.indexOf("/a/") != -1) {
			chain.doFilter(request, response);
			return;
		}
//		// 编辑状态
//		if (id.equals("ajaxeditstatus")) {
//			chain.doFilter(request, response);
//		}
		// 读取服务器文本到客户端
		if (id.equals("ajaxget")) {
			Ajaxget(request, response);
			return;
		}

		// 保存客户端文本到服务器
		if (id.equals("ajaxtxt")) {
			Ajaxtxt(usrid, request, response);
			return;
		}
		// 获取编辑权限
		if (id.equals("ajaxgetedit")) {
			long currenttime = new Date().getTime();
			if (mydata.getEdited().containsKey(usrid)) {
				long oldtime = mydata.getEdited().get(usrid);
				if (currenttime - oldtime < 6000) {// 距上次接收时间差小于五秒
					response.getWriter().write("false");
				} else {
					mydata.getEdited().put(usrid, currenttime);
					response.getWriter().write("true");
				}
			} else {
				mydata.getEdited().put(usrid, currenttime);
				response.getWriter().write("true");
			}
			return;
		}
		// 删除编辑权限
		if (id.equals("ajaxloseedit")) {
			if (mydata.getEdited().containsKey(usrid)) {
				mydata.getEdited().remove(usrid);
			}
			return;
		}
		
		// 验证密码并跳转
		if (id.equals("pwd")) {
			response.setCharacterEncoding("utf-8");	
	
//			((HttpServletResponse) response).sendRedirect("http://www.baidu.com"
//					);
			
				request.getRequestDispatcher("/a/password.jsp").forward(
						request, response);	
				System.out.println(request.getParameter("mod")+request.getParameter("id"));
				
				return;
		}
		if (id.equals("pwd1")) {
			int mod=Integer.parseInt(request.getParameter("mod").toString());
			response.setCharacterEncoding("utf-8");			
			if(!request.getParameter("pwd").toString().equals("123")){
				response.getWriter().write("false");			
			}else{	
				
				if(mod==0){//切换为编辑模式
					
				}
				response.getWriter().write("true");
			}
			return;
		}
		
		
		
		
		
		
		
		
		
		
		if (id.indexOf(".") != -1) {
			// 删除特殊字符
			id = id.replace(".", "");

			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/"
					+ id);
		} else
			request.getRequestDispatcher("/a/vnote.jsp?id=" + id).forward(
					request, response);
		// pass the request along the filter chain

		// chain.doFilter(request, response);
	}

	private void Ajaxget(ServletRequest request, ServletResponse response) {

		// System.out.println("IP"+getIpAddr((HttpServletRequest) request));
		if(request.getParameter("id")==null)
			return;
		String id = request.getParameter("id").toString();
		try {
			response.setCharacterEncoding("utf-8");
			if (mydata.getContents().containsKey(id))
				response.getWriter().write(mydata.getContents().get(id));
			else {
				response.getWriter().write("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//更新数据库
		updatedatabase();

	}


	private void Ajaxtxt(String usrid, ServletRequest request,
			ServletResponse response) {
		
		if(request.getParameter("id")==null)
			return;
		String id = request.getParameter("id").toString();
		// 如果该页面没有创建则 创建
		if (!mydata.getContents().containsKey(id)) {
			mydata.getContents().put(id, "欢迎使用");
		}
		mydata.getContents().put(id, request.getParameter("txt").toString());		
		long date = new Date().getTime();
		mydata.getEdited().put(usrid, date);

		//更新数据库
		updatedatabase();
	}
	void updatedatabase(){
		//更新数据库
		if(!sessflush.isAlive()){
			sessflush.interrupt();
			try {
				sessflush.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sessflush=new Thread(robj);
			sessflush.start();			
		}
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	myData mydata=new myData();
	Configuration conf;
	ServiceRegistry serviceRegistry;
	SessionFactory sf;
	Session sess;
	Thread sessflush;
	
	Runnable robj=new Runnable() {
		
		@Override
		public void run() {
			sess.flush();
			System.out.println("flushe end");
			try {
				Thread.sleep(60000);//1分钟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
	public void init(FilterConfig fConfig) throws ServletException {
		// 持久化数据库
		 conf = new Configuration().configure();
		 serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(conf.getProperties()).buildServiceRegistry();
		 sf=conf.buildSessionFactory(serviceRegistry);
		 sess=sf.openSession();
		 
		 mydata=(myData) sess.load(myData.class, 1);
		sessflush=new Thread(robj);

	}
	

}
