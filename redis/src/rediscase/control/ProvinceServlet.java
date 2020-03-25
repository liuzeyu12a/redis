package rediscase.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import rediscase.domain.Province;
import rediscase.service.ProvinceService;
import rediscase.service.impl.ProvinceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/provinceServlet")
public class ProvinceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用service查询
//        ProvinceService service = new ProvinceServiceImpl();
//        List<Province> list = service.findAll();
//        //2.转换成list
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(list);
//        System.out.println(json);

        //使用redis进行优化
        ProvinceService service = new ProvinceServiceImpl();
        String allJson = service.findAllJson();
        System.out.println(allJson);
        //3.写出到后台
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(allJson);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
