package vnote;

import java.io.IOException;
import java.util.Date;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Servlet Filter implementation class vnote mod模式：0编辑模式 1只写模式 2分享模式
 * 
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
		// System.out.println("保存数据到数据库");
		// update();//销毁时无法保存
		/*
		 * sess.flush(); sess.clear(); sf.close();
		 */
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	static String fengezifuchuan = ":Rizon:";// 共享模式用

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		String id = url.substring(url.lastIndexOf("/") + 1);
		id = id.toLowerCase();
		// 随机生成网址
		if (id.equals("")) {
			String str = "";
			do {
				for (int i = 0; i < 3; i++) {
					str = str + (char) (Math.random() * 26 + 'A');
				}
			} while (mydata.getContents().containsKey(str));
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
		// // 编辑状态
		// if (id.equals("ajaxeditstatus")) {
		// chain.doFilter(request, response);
		// }
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
		// 添加密码
		if (id.equals("addpwd")) {
			String myid = request.getParameter("id").toString();
			String pwd = request.getParameter("pwd").toString();

			/* 遇到过一个数据库主键什么的错误，原因在于数据持久化的问题，所以数据库应该把字段设置为不能为空 */
			mydata.getPwd().put(myid, pwd);
			// sess.flush();
			return;
		}
		// 验证密码并跳转
		if (id.equals("pwd")) {
			String myid = request.getParameter("id").toString();
			int mod = Integer.parseInt(request.getParameter("mod").toString());
			String frm = request.getParameter("frm").toString();
			response.setCharacterEncoding("utf-8");

			// 如果没有密码.且为切换模式请求、则直接跳转
			if (((!mydata.getPwd().containsKey(myid) || mydata.getPwd()
					.get(myid).equals("")) && frm.equals("0"))) {
				mydata.getMod().put(myid, mod);
				// sess.flush();
				response.getWriter().write(
						"<script>self.location.href='" + myid + "'</script>");
				return;
			} else {// 如果有密码则输入密码

				request.getRequestDispatcher("/a/password.jsp").forward(
						request, response);
				System.out.println("pwd" + request.getParameter("pwd")
						+ request.getParameter("id"));

				return;
			}
		}
		if (id.equals("pwd1")) {
			int mod = Integer.parseInt(request.getParameter("mod").toString());
			String myid = request.getParameter("id").toString();

			response.setCharacterEncoding("utf-8");

			if (request.getParameter("pwd") != null
					&& mydata.getPwd().containsKey(myid)
					&& request.getParameter("pwd") != ""
					&& !request.getParameter("pwd").equals(
							mydata.getPwd().get(myid))) {
				response.getWriter().write("false");
			} else {

				// 切换为模式
				mydata.getMod().put(myid, mod);
				// sess.flush();
				response.getWriter().write("true");
			}
			return;
		}

		// 获取共享url
		if (id.equals("shareurl")) {
			try {
				shareurl(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		// 返回共享文本
		if (id.equals("shareget")) {
			try {
				shareget(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}

		// 备份数据到数据库
		if (id.equals("backup")) {
			update(request, response);
			return;
		}

		// 最后的处理，是页面内容处理
		if (id.indexOf(".") != -1) {
			// 删除特殊字符
			id = id.replace(".", "");
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/"
					+ id);
		} else {
					
			
			if (mydata.getMod().containsKey(id)) {

				Integer mod = mydata.getMod().get(id);
				System.out.println(mod);
				
				//根据模式进行不同的处理
				
				// 只写模式
				if (mod == 1) {
					request.getRequestDispatcher("/a/vnote2.jsp?id=" + id)
							.forward(request, response);
					return;
				}
				// 分享模式
				if (mod == 2) {
					request.getRequestDispatcher("/a/share.jsp?id=" + id)
					.forward(request, response);
					return;
				}
				
			}
			// 编辑模式
			// 有密码时
			if (mydata.getPwd().get(id) != null
					&& !mydata.getPwd().get(id).equals("")) {
				System.out.println(request.getParameter("pwd"));
				if (request.getParameter("pwd") != null) {

					if (request.getParameter("pwd").equals(
							mydata.getPwd().get(id))) {
						request.getRequestDispatcher("/a/vnote.jsp?id=" + id)
								.forward(request, response);
						return;
					}
				}
				request.getRequestDispatcher(
						"/a/password.jsp?frm=0&mod=0&id=" + id).forward(
						request, response);
				return;
			} else {
				request.getRequestDispatcher("/a/vnote.jsp?id=" + id).forward(
						request, response);
				return;
			}

		}

		// pass the request along the filter chain

		// chain.doFilter(request, response);
	}

	private void Ajaxget(ServletRequest request, ServletResponse response) {

		// System.out.println("IP"+getIpAddr((HttpServletRequest) request));
		if (request.getParameter("id") == null)
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

		// 更新数据库
		// updatedatabase();

	}

	private void Ajaxtxt(String usrid, ServletRequest request,
			ServletResponse response) {

		if (request.getParameter("id") == null
				|| request.getParameter("txt") == null
				|| request.getParameter("txt").equals(""))
			return;

		String id = request.getParameter("id").toString();

		mydata.getContents().put(id, request.getParameter("txt").toString());

		long date = new Date().getTime();
		mydata.getEdited().put(usrid, date);

		// 更新数据库
		// updatedatabase();
	}

	// 共享模式
	private void shareget(ServletRequest request, ServletResponse response)
			throws IOException {
		String id = request.getParameter("id").toString();
		response.setCharacterEncoding("utf-8");

		if (mydata.getContents().containsKey(id)) {
			String targetid = mydata.getContents().get(id);
			targetid = targetid.split(fengezifuchuan)[1];// 获取共享url
			// System.out.println(targetid);
			if (mydata.getContents().containsKey(targetid)) {
				response.getWriter().write(mydata.getContents().get(targetid));
				return;
			}
		}

		// 没有时

		response.getWriter().write("nothing to show.");
		return;

	}

	// 获取共享模式url
	private void shareurl(ServletRequest request, ServletResponse response)
			throws IOException {
		String id = request.getParameter("id").toString();
		response.setCharacterEncoding("utf-8");

		if (mydata.getContents().containsKey(id + ".shareurl")) {

			String targetid = mydata.getContents().get(id + ".shareurl");
			response.getWriter().write(targetid);
			return;

		} else { // 没有时
			String url = "share";
			do {
				for (int i = 0; i < 3; i++) {
					url = url + (char) (Math.random() * 26 + 'A');
				}
			} while (mydata.getContents().containsValue(url));
			// 加入到数据库
			mydata.getContents().put(id + ".shareurl", url.toLowerCase());
			mydata.getMod().put(url.toLowerCase(), 2);// 模式2，共享模式
			mydata.getContents().put(url.toLowerCase(), fengezifuchuan+id+fengezifuchuan);// 建立url和源文件的联系

			response.getWriter().write(url);
			return;
		}

	}

	/*
	 * void updatedatabase() { // 更新数据库 if (!sessflush.isAlive()) {
	 * sessflush.interrupt(); try { sessflush.join(); } catch
	 * (InterruptedException e) { e.printStackTrace(); } sessflush = new
	 * Thread(robj); sessflush.start(); } }
	 */
	/**
	 * @see Filter#init(FilterConfig)
	 */
	myData mydata = new myData();
	Configuration conf;
	ServiceRegistry serviceRegistry;

	Thread sessflush;

	/*
	 * Runnable robj = new Runnable() {
	 * 
	 * @Override public void run() {
	 * 
	 * sess.flush(); System.out.println("flush end"); try {
	 * Thread.sleep(60000);// 1分钟 } catch (InterruptedException e) {
	 * e.printStackTrace(); } } };
	 */
	public void init(FilterConfig fConfig) throws ServletException {

		// 加载数据库
		conf = new Configuration().configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				conf.getProperties()).buildServiceRegistry();
		SessionFactory sf = conf.buildSessionFactory(serviceRegistry);
		Session sess = sf.openSession();

		mydata = (myData) sess.get(myData.class, 1);
		// 初始化
		Hibernate.initialize(mydata.getContents());
		Hibernate.initialize(mydata.getEdited());
		Hibernate.initialize(mydata.getMod());
		Hibernate.initialize(mydata.getId());
		Hibernate.initialize(mydata.getPwd());

		// sessflush = new Thread(robj);
		sess.close();
		sf.close();
	}

	public void update() {
		update(null, null);
	}

	public void update(ServletRequest request, ServletResponse response) {
		String result = "";
		synchronized (mydata) {// 加锁
			try {
				SessionFactory sf = conf.buildSessionFactory(serviceRegistry);
				Session sess = sf.openSession();
				Transaction tx = sess.beginTransaction();
				// 这个地方要用update重新恢复托管。如果用save保存，会作为新的数据从而导致生成新的id
				sess.saveOrUpdate(mydata);
				tx.commit();
				sess.close();
				sf.close();
				result = "数据保存到数据库成功";
			} catch (Exception ex) {
				result = ex.getMessage();
				ex.printStackTrace();
			}
		}
		System.out.println(result);

		if (response != null) {
			try {
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
