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
 * Servlet Filter implementation class vnote modģʽ��0�༭ģʽ 1ֻдģʽ 2����ģʽ
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
		// System.out.println("�������ݵ����ݿ�");
		// update();//����ʱ�޷�����
		/*
		 * sess.flush(); sess.clear(); sf.close();
		 */
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	static String fengezifuchuan = ":Rizon:";// ����ģʽ��

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		String id = url.substring(url.lastIndexOf("/") + 1);
		id = id.toLowerCase();
		// ���������ַ
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
		// // �༭״̬
		// if (id.equals("ajaxeditstatus")) {
		// chain.doFilter(request, response);
		// }
		// ��ȡ�������ı����ͻ���
		if (id.equals("ajaxget")) {
			Ajaxget(request, response);
			return;
		}

		// ����ͻ����ı���������
		if (id.equals("ajaxtxt")) {
			Ajaxtxt(usrid, request, response);
			return;
		}
		// ��ȡ�༭Ȩ��
		if (id.equals("ajaxgetedit")) {
			long currenttime = new Date().getTime();
			if (mydata.getEdited().containsKey(usrid)) {
				long oldtime = mydata.getEdited().get(usrid);
				if (currenttime - oldtime < 6000) {// ���ϴν���ʱ���С������
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
		// ɾ���༭Ȩ��
		if (id.equals("ajaxloseedit")) {
			if (mydata.getEdited().containsKey(usrid)) {
				mydata.getEdited().remove(usrid);
			}
			return;
		}
		// �������
		if (id.equals("addpwd")) {
			String myid = request.getParameter("id").toString();
			String pwd = request.getParameter("pwd").toString();

			/* ������һ�����ݿ�����ʲô�Ĵ���ԭ���������ݳ־û������⣬�������ݿ�Ӧ�ð��ֶ�����Ϊ����Ϊ�� */
			mydata.getPwd().put(myid, pwd);
			// sess.flush();
			return;
		}
		// ��֤���벢��ת
		if (id.equals("pwd")) {
			String myid = request.getParameter("id").toString();
			int mod = Integer.parseInt(request.getParameter("mod").toString());
			String frm = request.getParameter("frm").toString();
			response.setCharacterEncoding("utf-8");

			// ���û������.��Ϊ�л�ģʽ������ֱ����ת
			if (((!mydata.getPwd().containsKey(myid) || mydata.getPwd()
					.get(myid).equals("")) && frm.equals("0"))) {
				mydata.getMod().put(myid, mod);
				// sess.flush();
				response.getWriter().write(
						"<script>self.location.href='" + myid + "'</script>");
				return;
			} else {// �������������������

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

				// �л�Ϊģʽ
				mydata.getMod().put(myid, mod);
				// sess.flush();
				response.getWriter().write("true");
			}
			return;
		}

		// ��ȡ����url
		if (id.equals("shareurl")) {
			try {
				shareurl(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		// ���ع����ı�
		if (id.equals("shareget")) {
			try {
				shareget(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}

		// �������ݵ����ݿ�
		if (id.equals("backup")) {
			update(request, response);
			return;
		}

		// ���Ĵ�����ҳ�����ݴ���
		if (id.indexOf(".") != -1) {
			// ɾ�������ַ�
			id = id.replace(".", "");
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/"
					+ id);
		} else {
					
			
			if (mydata.getMod().containsKey(id)) {

				Integer mod = mydata.getMod().get(id);
				System.out.println(mod);
				
				//����ģʽ���в�ͬ�Ĵ���
				
				// ֻдģʽ
				if (mod == 1) {
					request.getRequestDispatcher("/a/vnote2.jsp?id=" + id)
							.forward(request, response);
					return;
				}
				// ����ģʽ
				if (mod == 2) {
					request.getRequestDispatcher("/a/share.jsp?id=" + id)
					.forward(request, response);
					return;
				}
				
			}
			// �༭ģʽ
			// ������ʱ
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

		// �������ݿ�
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

		// �������ݿ�
		// updatedatabase();
	}

	// ����ģʽ
	private void shareget(ServletRequest request, ServletResponse response)
			throws IOException {
		String id = request.getParameter("id").toString();
		response.setCharacterEncoding("utf-8");

		if (mydata.getContents().containsKey(id)) {
			String targetid = mydata.getContents().get(id);
			targetid = targetid.split(fengezifuchuan)[1];// ��ȡ����url
			// System.out.println(targetid);
			if (mydata.getContents().containsKey(targetid)) {
				response.getWriter().write(mydata.getContents().get(targetid));
				return;
			}
		}

		// û��ʱ

		response.getWriter().write("nothing to show.");
		return;

	}

	// ��ȡ����ģʽurl
	private void shareurl(ServletRequest request, ServletResponse response)
			throws IOException {
		String id = request.getParameter("id").toString();
		response.setCharacterEncoding("utf-8");

		if (mydata.getContents().containsKey(id + ".shareurl")) {

			String targetid = mydata.getContents().get(id + ".shareurl");
			response.getWriter().write(targetid);
			return;

		} else { // û��ʱ
			String url = "share";
			do {
				for (int i = 0; i < 3; i++) {
					url = url + (char) (Math.random() * 26 + 'A');
				}
			} while (mydata.getContents().containsValue(url));
			// ���뵽���ݿ�
			mydata.getContents().put(id + ".shareurl", url.toLowerCase());
			mydata.getMod().put(url.toLowerCase(), 2);// ģʽ2������ģʽ
			mydata.getContents().put(url.toLowerCase(), fengezifuchuan+id+fengezifuchuan);// ����url��Դ�ļ�����ϵ

			response.getWriter().write(url);
			return;
		}

	}

	/*
	 * void updatedatabase() { // �������ݿ� if (!sessflush.isAlive()) {
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
	 * Thread.sleep(60000);// 1���� } catch (InterruptedException e) {
	 * e.printStackTrace(); } } };
	 */
	public void init(FilterConfig fConfig) throws ServletException {

		// �������ݿ�
		conf = new Configuration().configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				conf.getProperties()).buildServiceRegistry();
		SessionFactory sf = conf.buildSessionFactory(serviceRegistry);
		Session sess = sf.openSession();

		mydata = (myData) sess.get(myData.class, 1);
		// ��ʼ��
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
		synchronized (mydata) {// ����
			try {
				SessionFactory sf = conf.buildSessionFactory(serviceRegistry);
				Session sess = sf.openSession();
				Transaction tx = sess.beginTransaction();
				// ����ط�Ҫ��update���»ָ��йܡ������save���棬����Ϊ�µ����ݴӶ����������µ�id
				sess.saveOrUpdate(mydata);
				tx.commit();
				sess.close();
				sf.close();
				result = "���ݱ��浽���ݿ�ɹ�";
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
