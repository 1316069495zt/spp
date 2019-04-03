package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.QrCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface QrCodeDao extends IBaseDao<QrCode,String> {
    @Query(value = "SELECT*FROM qr_code WHERE number LIKE ?1 ORDER BY ?#{#pageable}",nativeQuery = true)
    Page<QrCode> findlike(String number, Pageable pageable);
}
