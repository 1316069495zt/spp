package net.sppan.base.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * @author zt
 */
@Entity
@Table(name = "qr_code")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QrCode extends BaseEntity implements Serializable {
    /**
     * 二维码id
     */
    @Id
    private String id;
    /**
     * 公司id
     */
    private String companyId;

    /**
     * 二维码状态1激活
     */
    private String state;
    /**
     * 二维码编号
     */
    private String number;
    /**
     * 二维码数据
     */
    private String data;
}
