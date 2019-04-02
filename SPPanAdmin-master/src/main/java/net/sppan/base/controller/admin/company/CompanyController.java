package net.sppan.base.controller.admin.company;


import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.Company;
import net.sppan.base.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value="/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    //保存企业
    @RequestMapping(value="",method = RequestMethod.POST)
    public JsonResult save(@RequestBody Company company)  {
        //业务操作
        companyService.add(company);
        return JsonResult.success();
    }
    //根据id更新企业
    /**
     * 1.方法
     * 2.请求参数
     * 3.响应
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public JsonResult update(@PathVariable(value="id") String id, @RequestBody Company company ) {
        //业务操作
        company.setId(id);
        companyService.update(company);
        return JsonResult.success();
    }

    //根据id删除企业
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable(value="id") String id) {
        companyService.deleteById(id);
        return JsonResult.success();
    }

    //根据id查询企业
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public JsonResult findById(@PathVariable(value="id") String id){
        //throw new CommonException(ResultCode.UNAUTHORISE);
        Company company = companyService.findById(id);
        return JsonResult.success(company);
    }

    //查询全部企业列表
    @RequestMapping(value="",method = RequestMethod.GET)
    public JsonResult findAll() {
        //int i = 1/0;
        List<Company> list = companyService.findAll();
        return JsonResult.success(list);
    }
}
