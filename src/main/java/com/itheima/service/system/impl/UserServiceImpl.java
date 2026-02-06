package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.User;
import com.itheima.factory.MapperFactory;
import com.itheima.service.system.UserService;
import com.itheima.utils.MD5Util;
import com.itheima.utils.TransactionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public void save(User user) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);
            //id使用UUID的生成策略来获取
            String id = UUID.randomUUID().toString();
            user.setId(id);

            //密码必须经过加密处理MD5加密
            user.setPassword(MD5Util.md5(user.getPassword()));
            //3.调用Dao层操作
            userDao.save(user);
            //4.提交事务
            TransactionUtil.commit(sqlSession);

        }catch (Exception e){
            //回滚事务
            TransactionUtil.rollback(sqlSession);
            //将错误返回最上面
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                //打印异常
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(User user) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);
            //3.调用Dao层操作
            userDao.delete(user);
            //4.提交事务
            TransactionUtil.commit(sqlSession);

        }catch (Exception e){
            //回滚事务
            TransactionUtil.rollback(sqlSession);
            //将错误返回最上面
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                //打印异常
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(User user) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);

            //方案一：读取现有库中的信息，覆盖现有的数据
            //方案二：修改update语句

            //3.调用Dao层操作
            userDao.update(user);
            //4.提交事务
            TransactionUtil.commit(sqlSession);

        }catch (Exception e){
            //回滚事务
            TransactionUtil.rollback(sqlSession);
            //将错误返回最上面
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                //打印异常
                e.printStackTrace();
            }
        }

    }

    @Override
    public User findById(String id) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);
            //3.调用Dao层操作
            return userDao.findById(id);
        }catch (Exception e){
            //将错误返回最上面
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                //打印异常
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);
            //3.调用Dao层操作
            return userDao.findAll();
        }catch (Exception e){
            //将错误返回最上面
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                //打印异常
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageInfo findAll(int page, int size) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);
            //3.调用Dao层操作
            PageHelper.startPage(page,size);
            List<User> all = userDao.findAll();
            PageInfo pageInfo = new PageInfo(all);
            return pageInfo;
        }catch (Exception e){
            //将错误返回最上面
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                //打印异常
                e.printStackTrace();
            }
        }
    }
}
