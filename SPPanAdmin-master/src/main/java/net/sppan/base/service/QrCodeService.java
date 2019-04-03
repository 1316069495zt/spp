package net.sppan.base.service;

import net.sppan.base.common.utils.IdWorker;
import net.sppan.base.dao.QrCodeDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.QrCode;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * @author zt
 */
@Service
public class QrCodeService extends BaseServiceImpl<QrCode,String> {

    @Autowired
    private QrCodeDao qrCodeDao;

    @Autowired
    private IdWorker idWorker;
    @Override
    public IBaseDao<QrCode, String> getBaseDao() {
        return null;
    }




    /**
     * 新增二维码
     * @param qrcode
     */
    public void add(QrCode qrcode) {
        //基本属性的设置
        String id = idWorker.nextId()+"";
        qrcode.setId(id);
        //默认的状态
        qrcode.setState("1"); 
        qrCodeDao.save(qrcode);
    }
    /**
     * 修改二维码
     */
    @Override
    public QrCode update(QrCode qrcode) {
        QrCode qrCodeDaoOne = qrCodeDao.findOne(qrcode.getId());
        qrCodeDaoOne.setNumber(qrcode.getNumber());
        qrCodeDaoOne.setData(qrcode.getData());
        qrCodeDao.save(qrCodeDaoOne);
        return qrcode;
    }
    /**
     * 删除二维码
     */
    public void deleteById(String id) {
        qrCodeDao.delete(id);
    }
    /**
     * 根据id查询二维码
     */
    public QrCode findById(String id) {
        return qrCodeDao.findOne(id);
    }

    /**
     * 模糊查询
     * @param map
     * @param
     * @return
     */
    public Page<QrCode> findLike(Map map, Integer page,Integer size) {
        String number = (String) map.get("number");
        Pageable pageable = new PageRequest(page-1,size);
        number ="%"+number+"%";
        Page<QrCode> pagelist = qrCodeDao.findlike(number,pageable);
        return pagelist;
    }

    /**
     * 查询二维码
     * @param comanyId
     * @param page
     * @param size
     * @return
     */
    public Page<QrCode> findAll(String comanyId, int page, int size) {
            Specification<QrCode> spec = new Specification<QrCode>() {
                @Override
                public Predicate toPredicate(Root<QrCode> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    return cb.equal(root.get("companyId").as(String.class),comanyId);
                }
            };
        Pageable pageable = new PageRequest(page-1,size);
            return qrCodeDao.findAll(spec,pageable);
    }
}
