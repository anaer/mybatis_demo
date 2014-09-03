package org.mybatis.action;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.mybatis.model.Blog;
import org.mybatis.model.BlogMapper;
import org.mybatis.model.Post;
import org.mybatis.service.SqlMapperManager;

public class SimpleMapper {
    /**
     * @param args
     * 1. 使用select *, 结果类型为Blog, 因为Blog定义的为authorId, 与数据库字段author_id不一致,所以查询时为null
     * 2. 不推荐使用*号代替字段
     */
    public static void main(String[] args) {
	BasicConfigurator.configure();
	SqlSession session = null;
	Blog blog = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
	    paramMap.put("id", 2);
	    Blog myBlog = new Blog();
	    myBlog.setId(3);
	    blog = (Blog) session.selectOne("selectBlog_by_id", 1);
	    pringBlog(blog);
	    blog = (Blog) session.selectOne("selectBlog_by_id_Map", paramMap);
	    pringBlog(blog);
	    blog = (Blog) session.selectOne("selectBlog_by_bean", myBlog);
	    pringBlog(blog);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    public static void pringBlog(Blog blog) {
	if (blog != null) {
	    System.out.println("ID:" + blog.getId());
	    System.out.println("title:" + blog.getTitle());
	    System.out.println("authorID:" + blog.getAuthorId());
	} else {
	    System.out.println("blog=null");
	}
    }

    /**
     * 1. 解决字段不匹配问题的两种方式
     * 1.1 使用ResultMap
     * 1.2 使用as
     */
    @Test
    public void test2() {
	BasicConfigurator.configure();
	SqlSession session = null;
	Blog blog = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
	    paramMap.put("id", 2);
	    Blog myBlog = new Blog();
	    myBlog.setId(3);
	    blog = (Blog) session.selectOne("selectBlog_use_as", myBlog);
	    pringBlog(blog);
	    blog = (Blog) session.selectOne("selectBlog_use_resultMap", paramMap);
	    pringBlog(blog);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * update，delete，insert
     * 测试增删改
     * 默认地MyBatis 不会自动提交,最后还需要使用session.commit()来提交修改。但可以调用
     * 下面的方法来自动提交。
     * SqlSession openSession(boolean autoCommit)
     */
    @Test
    public void test3() {
	BasicConfigurator.configure();
	SqlSession session = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    Blog myBlog = new Blog();
	    myBlog.setId(3);
	    myBlog.setTitle("I Love Photh");
	    myBlog.setAuthorId(3);
	    session.update("updateBlog_use_bean", myBlog);
	    session.delete("deleteBlog_use_bean", myBlog);
	    session.insert("insertBlog_user_bean", myBlog);
	    session.commit();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * 自动生成主键
     */
    @Test
    public void test4() {
	BasicConfigurator.configure();
	SqlSession session = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    Blog myBlog1 = new Blog();
	    myBlog1.setTitle("I Love Photh");
	    myBlog1.setAuthorId(3);
	    session.insert("insertBlog_user_autokey", myBlog1);
	    session.insert("insertBlog_user_autokey", myBlog1);
	    session.insert("insertBlog_user_autokey", myBlog1);
	    session.commit();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * test模板
     */
    @Test
    public void test0() {
	BasicConfigurator.configure();
	SqlSession session = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * 处理空值
     * 如果传递了一个空值，那这个JDBC Type 对于所有JDBC 允许为空的列来说是必须的。
     * 您可以研读一下关于PreparedStatement.setNull()的JavaDocs 文档。
     * 解决这个问题就需要在参数中指定jdbcType 属性，这个属性只在insert,update 或delete
     * 的时候针对允许空的列有用。如
     */
    @Test
    public void test5() {
	BasicConfigurator.configure();
	SqlSession session = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    Blog myBlog1 = new Blog();
	    myBlog1.setTitle(null);
	    myBlog1.setAuthorId(3);
	    /**
	     * org.apache.ibatis.exceptions.PersistenceException:
	     * ### Error updating database. Cause: org.apache.ibatis.type.TypeException: Error setting null
	     * parameter. Most JDBC drivers require that the JdbcType must be specified for all nullable
	     * parameters. Cause: java.sql.SQLDataException: 尝试从类型“OTHER”的数据值获取类型“VARCHAR”的数据值。
	     * ### The error may involve org.mybatis.model.BlogMapper.insertBlog_user_autokey-Inline
	     * ### The error occurred while setting parameters
	     */
	    //session.insert("insertBlog_user_autokey", myBlog1); // 会报异常

	    session.insert("insertBlog_user_autokey_null", myBlog1); // 会报异常
	    session.commit();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * test模板
     */
    @Test
    public void test6() {
	BasicConfigurator.configure();
	SqlSession session = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    userMapper(session);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * 使用BlogMapper映射接口
     * MyBatis 启动时首先会根据XML 配置文件中的命名空间查找是否存在这个接口类，如果存在
     * 则保存在一个名为knownMappers 的HashSet 中。当我们使用session 的方法：
     * <T> T getMapper(Class<T> type)
     * 的时候，如果type 是已知的接口类(knownMappers.contains(type)==true)，OK，继续执
     * 行，如果不是已知接口类(knownMappers.contains(type)=false)，则抛出如下异常：
     * org.apache.ibatis.binding.BindingException: Type interface org.mybatis.model.BlogMapperr is
     * not known to the MapperRegistry.
     */
    public void userMapper(SqlSession session) {
	Blog blog = new Blog();
	blog.setTitle("nothing title");
	blog.setId(1);
	blog.setAuthorId(1);
	BlogMapper blogMapper = session.getMapper(BlogMapper.class);
	Blog blog1 = blogMapper.selectBlog_by_id(1);
	pringBlog(blog1);
	blogMapper.updateBlog_use_bean(blog);
	blog1 = blogMapper.selectBlog_by_id(1);
	pringBlog(blog1);
    }

    /**
     * 使用Constructor 元素
     * 使用Constructor 元素是将数据库查询的结果通过构造器注入到结果映射类（JavaBean）
     * 中，可以理解为spring 中的构造器注入。实际上在一个映射结果类中很可能没有构造器，或者在
     * 一个resultMap 中既有构造器映射，又有指定属性映射，会造成配置不统一，反而不清晰，因此
     * 这个元素可能会很少使用。
     *
     * 注:pojo类中需要存在默认构造以及带参构造
     */
    @Test
    public void test7() {
	BasicConfigurator.configure();
	SqlSession session = null;
	Blog blog = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    blog = (Blog) session.selectOne("selectBlog_use_constructor", 3);
	    pringBlog(blog);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    /**
     * 联合嵌套结果集（Nested Results for Association）。
     * BlogMapper.xml 配置(1)和BlogMapper.xml 配置(2)的配置是等效的，谁优谁劣见仁
     * 见智，或者看具体的项目要求。BlogMapper.xml 配置(3)使用了两条查询语句，加载博客信息后
     * 再加载作者信息， 会产生N+1 问题。
     */
    @Test
    public void test8() {
	BasicConfigurator.configure();
	SqlSession session = null;
	Blog blog = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    blog = (Blog) session.selectOne("selectBlog_use_association1", 3);
	    printBlogAuthor(blog);
	    blog = (Blog) session.selectOne("selectBlog_use_association2", 3);
	    printBlogAuthor(blog);
	    blog = (Blog) session.selectOne("selectBlog_use_association3", 3);
	    printBlogAuthor(blog);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    public static void printBlogAuthor(Blog blog) {
	System.out.println("ID:" + blog.getId());
	System.out.println("title:" + blog.getTitle());
	System.out.println("authorID:" + blog.getAuthor().getId());
	System.out.println("authorName:" + blog.getAuthor().getUsername());
	System.out.println("authorPassword:" + blog.getAuthor().getPassword());
	System.out.println("authorEmail:" + blog.getAuthor().getEmail());
	System.out.println("authorBio:" + blog.getAuthor().getBio());
    }

    /**
     * BlogMapper.xml 配置(1)和BlogMapper.xml 配置(2)打印的结果都是一样的，只是查询
    的方式不一样
     */
    @Test
    public void test9() {
	BasicConfigurator.configure();
	SqlSession session = null;
	try {
	    SqlSessionFactory factory = SqlMapperManager.getFactory();
	    if (factory == null) {
		System.out.println("get SqlSessionFactory failed.");
		return;
	    }
	    session = factory.openSession();
	    System.out.println("\n===========================1");
	    List<Blog> blogList1 = session.selectList("selectBlog_use_collection1");
	    printBlogPosts(blogList1);
	    System.out.println("\n===========================2");
	    List<Blog> blogList2 = session.selectList("selectBlog_use_collection2");
	    printBlogPosts(blogList2);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    session.close();
	}
    }

    public static void printBlogPosts(List<Blog> blogList) {
	for (Blog blog : blogList) {
	    System.out.println("\n===========================");
	    System.out.println("ID:" + blog.getId());
	    System.out.println("blog_title:" + blog.getTitle());
	    System.out.println("authorID:" + blog.getAuthorId());
	    System.out.println("===========posts=============");
	    for (Post post : blog.getPosts()) {
		System.out.println("subject:" + post.getSubject());
		System.out.println("section:" + post.getSection());
		System.out.println("body:" + post.getBody());
	    }
	}
    }

}