package net.sppan.base.controller.admin.system;

import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.QrCode;
import net.sppan.base.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zt
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/qrcode")
public class QrcodeController extends BaseController {


    @Autowired
    private QrCodeService qrCodeService;

     /**
     * 查询二维码
     */
    @GetMapping("/{comanyId}/{page}/{size}")
    public JsonResult findQrcode(@PathVariable(name = "comanyId") String comanyId,
                                 @PathVariable(name = "page") Integer page,
                                 @PathVariable(name = "size") Integer size) {
        Page<QrCode> pageList = qrCodeService.findAll(comanyId, page,size);
        Map<Object,List<QrCode>> map = new HashMap<>();
        map.put(pageList.getTotalElements(),pageList.getContent());
        return JsonResult.success(map);
    }

    /**
     * 新增二维码
     *
     * @param qrCode
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult save(@RequestBody QrCode qrCode) {

        qrCodeService.add(qrCode);
        return JsonResult.success();
    }

    /**
     * 根据id更新二维码
     *
     * @param id
     * @param qrCode
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(@PathVariable(value = "id") String id, @RequestBody QrCode qrCode) {
        qrCode.setId(id);
        qrCodeService.update(qrCode);
        return JsonResult.success();
    }

    /**
     * 根据id删除二维码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable(value = "id") String id) {
        qrCodeService.deleteById(id);
        return JsonResult.success();
    }

    /**
     * 搜索
     * @param map
     * @return
     */
    @RequestMapping(value = "/like/{page}/{size}", method = RequestMethod.GET)
    public JsonResult findLike(@RequestParam Map map,
                               @PathVariable(name = "page") Integer page,
                               @PathVariable(name = "size") Integer size) {
        Page<QrCode> pageList = qrCodeService.findLike(map,page,size);
        Map<Object,List<QrCode>> map1 = new HashMap<>();
        map1.put(pageList.getTotalElements(),pageList.getContent());
        return JsonResult.success(map1);
    }

}
