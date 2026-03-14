package com.itheima.web.controller.store;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.store.Catalog;
import com.itheima.domain.store.Company;
import com.itheima.domain.store.Course;
import com.itheima.domain.store.Question;
import com.itheima.utils.BeanUtil;
import com.itheima.web.controller.BaseServlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


//uri:/store/question?operation=list
@WebServlet("/store/question")
public class QuestionServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        if("list".equals(operation)){
            this.list(request, response);
        } else if ("toAdd".equals(operation)) {
            this.toAdd(request, response);
        }else if ("save".equals(operation)) {
            try {
                this.save(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if ("toEdit".equals(operation)) {
            this.toEdit(request,response);
        }else if ("edit".equals(operation)) {
            this.edit(request,response);
        }else if ("delete".equals(operation)) {
            this.delete(request,response);
        }else if ("toTestUpload".equals(operation)) {
            this.toTestUpload(request,response);
        }else if ("testUpload".equals(operation)) {
            try {
                this.testUpload(request,response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void toTestUpload(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        //跳转页面
        request.getRequestDispatcher("/WEB-INF/pages/store/question/testFileUpload.jsp").forward(request,response);
    }
    private void testUpload(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //1.确认该操作是否支持文件上传操作enctype="multipart/form-data"
        if(ServletFileUpload.isMultipartContent(request)){
           //2.创建磁盘工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //3.Servlet文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //4.从request中读取数据
            List<FileItem> fileItems = fileUpload.parseRequest(request);

            for (FileItem item : fileItems){
                //5.当前表单是否是文件表单
                if(!item.isFormField()){
                    //6.从临时存储文件的地方将内容写入到指定位置
                    item.write(new File(this.getServletContext().getRealPath("upload"),item.getName()));
                }
            }
        }
    }

    private void list(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //进入列表页
        //获取数据
//        QuestionService = new QuestionServiceImpl();
        int page =1;
        int size =5;
        if(StringUtils.isNotBlank(request.getParameter("page"))){
            page = Integer.parseInt(request.getParameter("page"));
        }
        if(StringUtils.isNotBlank(request.getParameter("size"))){
            page = Integer.parseInt(request.getParameter("size"));
        }
        PageInfo all = questionService.findAll(page,size);
        //将数据保存到指定的位置
        request.setAttribute("page",all);
        //跳转页面
        request.getRequestDispatcher("/WEB-INF/pages/store/question/list.jsp").forward(request,response);
    }
    private void toAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //加载企业信息
        List<Company> campanyList = companyService.findAll();
        request.setAttribute("companyList",campanyList);
        //加载目录信息
        List<Catalog> catalogList = catalogService.findAll();
        request.setAttribute("catalogList",catalogList);
        //跳转页面
        request.getRequestDispatcher("/WEB-INF/pages/store/question/add.jsp").forward(request,response);
    }
    private void save(HttpServletRequest request,HttpServletResponse response) throws Exception {

        //1.确认该操作是否支持文件上传操作enctype="multipart/form-data"
        if(ServletFileUpload.isMultipartContent(request)){
            //2.创建磁盘工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //3.Servlet文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //4.从request中读取数据
            List<FileItem> fileItems = fileUpload.parseRequest(request);

            //①处理form表单提交过来的普通数据
            //将数据获取到，封装成一个对象
            Question question = BeanUtil.fillBean(fileItems, Question.class);
            //调用业务层接口Save
//           QuestionService = new QuestionServiceImpl();
            questionService.save(question);

            //②处理form表单提交过来的文件数据
            for (FileItem item : fileItems){
                //5.当前表单是否是文件表单
                if(!item.isFormField()){
                    //6.从临时存储文件的地方将内容写入到指定位置
                    item.write(new File(this.getServletContext().getRealPath("upload"),item.getName()));
                }
            }
        }
        //跳转回到页面list
        //list(request, response);
        response.sendRedirect(request.getContextPath()+"/store/question?operation=list");
    }

    private void toEdit(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        //查询要修改的数据findById
        String id = request.getParameter("id");
//        QuestionService = new QuestionServiceImpl();
        Question question = questionService.findById(id);
        //将数据加载到指定区域，供页面获取
        request.setAttribute("question",question);
        //加载企业信息
        List<Company> campanyList = companyService.findAll();
        request.setAttribute("companyList",campanyList);
        //加载目录信息
        List<Catalog> catalogList = catalogService.findAll();
        request.setAttribute("catalogList",catalogList);
        //跳转页面
        request.getRequestDispatcher("/WEB-INF/pages/store/question/update.jsp").forward(request,response);
    }
    private void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //将数据获取到，封装成一个对象
        Question question = BeanUtil.fillBean(request, Question.class,"yyyy-MM-dd");
        //调用业务层接口Save
//        QuestionService = new QuestionServiceImpl();
        questionService.update(question);
        //跳转回到页面list
        //list(request, response);
        response.sendRedirect(request.getContextPath()+"/store/question?operation=list");
    }
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将数据获取到，封装成一个对象
        Question question = BeanUtil.fillBean(request, Question.class);
        //调用业务层接口Save
//        QuestionService = new QuestionServiceImpl();
        questionService.delete(question);
        //跳转回到页面list
        //list(request, response);
        response.sendRedirect(request.getContextPath()+"/store/question?operation=list");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}

