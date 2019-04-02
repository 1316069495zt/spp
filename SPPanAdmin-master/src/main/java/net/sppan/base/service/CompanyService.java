package net.sppan.base.service;


import net.sppan.base.common.utils.IdWorker;
import net.sppan.base.dao.CompanyDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.Company;
import net.sppan.base.license.EncryUtil;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyService extends BaseServiceImpl<Company,String> {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 保存企业
     *  1.配置idwork到工程
     *  2.在service中注入idwork
     *  3.通过idwork生成id
     *  4.保存企业
     */
    public void add(Company company) {
        //基本属性的设置
        String id = idWorker.nextId()+"";
        company.setId(id);
        //默认的状态
        company.setAuditState("0");//0：未审核，1：已审核
        company.setState(1); //0.未激活，1：已激活
        company.setCreateTime(new Date());
        String encrypt = EncryUtil.encrypt(company.getLicense());
        System.out.println(encrypt);
        company.setLicense(encrypt);
        companyDao.save(company);
    }
    /**
     * 更新企业
     *  1.参数：Company
     *  2.根据id查询企业对象
     *  3.设置修改的属性
     *  4.调用dao完成更新
     */
    @Override
    public Company update(Company company) {
        Company temp = companyDao.findOne(company.getId());
        temp.setName(company.getName());
        temp.setCompanyPhone(company.getCompanyPhone());
        temp.setAuditState(company.getAuditState());
        temp.setLicense( EncryUtil.encrypt(company.getLicense()));
        companyDao.save(temp);
        return temp;
    }
    /**
     * 删除企业
     */
    public void deleteById(String id) {
        companyDao.delete(id);
    }
    /**
     * 根据id查询企业
     */
    public Company findById(String id) {
        return companyDao.findOne(id);
    }
    @Override
    public IBaseDao<Company, String> getBaseDao() {
        return null;
    }
    /**
     * 查询企业列表
     */
    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
