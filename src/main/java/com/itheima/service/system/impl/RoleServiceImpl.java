package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.RoleDao;
import com.itheima.domain.system.Role;
import com.itheima.factory.MapperFactory;
import com.itheima.service.system.RoleService;
import com.itheima.utils.TransactionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;


public class RoleServiceImpl implements RoleService {
    @Override
    public void save(Role role) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            RoleDao roleDao = MapperFactory.getMapper(sqlSession, RoleDao.class);
            //id使用UUID的生成策略来获取
            String id = UUID.randomUUID().toString();
            role.setId(id);
            //3.调用Dao层操作
            roleDao.save(role);
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
    public void delete(Role role) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            RoleDao roleDao = MapperFactory.getMapper(sqlSession, RoleDao.class);
            //3.调用Dao层操作
            roleDao.delete(role);
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
    public void update(Role role) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            RoleDao roleDao = MapperFactory.getMapper(sqlSession, RoleDao.class);
            //3.调用Dao层操作
            roleDao.update(role);
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
    public Role findById(String id) {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            RoleDao roleDao = MapperFactory.getMapper(sqlSession, RoleDao.class);
            //3.调用Dao层操作
            return roleDao.findById(id);
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
    public List<Role> findAll() {
        SqlSession sqlSession = null;
        try {
            //1.获取sqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            RoleDao roleDao = MapperFactory.getMapper(sqlSession, RoleDao.class);
            //3.调用Dao层操作
            return roleDao.findAll();
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
            RoleDao roleDao = MapperFactory.getMapper(sqlSession, RoleDao.class);
            //3.调用Dao层操作
            PageHelper.startPage(page,size);
            List<Role> all = roleDao.findAll();
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

