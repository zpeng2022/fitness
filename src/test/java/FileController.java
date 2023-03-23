/*
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.spinehealth.common.Constants;
import com.ruoyi.spinehealth.common.Result;
import com.ruoyi.spinehealth.domain.*;
import com.ruoyi.spinehealth.mapper.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


*/
/**
 * 文件上传相关接口
 *//*

@Api("文件上传下载")
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String port;

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TokenService tokenService;

    */
/**
     * 文件上传接口
     *
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     *//*

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file,
                         @RequestParam String username) throws IOException {
        long userId = userMapper.selectIdByUsername(username);
        Long role = userRoleMapper.getRoleIdsByUserId(userId);
        Role roleInfo = roleMapper.getRoleInfoById(role);
        String userRole = roleInfo.getRoleName();

        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }


        switch (userRole) {
            case "教体局用户": {
                User user = userMapper.selectByPrimaryKey(userId);
                Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
                List<Long> deptIds = deptMapper.selectChildIds(dept.getDeptId()); //学校对应的detpId
                for(Long deptId : deptIds) {
                    String schoolNo = schoolMapper.selectSchoolNoByDeptId(deptId);

                    String url;
                    // 获取文件的md5
                    String md5 = SecureUtil.md5(file.getInputStream());
                    // 从数据库查询是否存在相同的记录
                    Files dbFiles = getFileByMd5AndSchoolNo(md5, schoolNo);
                    if (dbFiles != null) {
                        url = dbFiles.getUrl();
                    } else {
                        // 上传文件到磁盘
                        file.transferTo(uploadFile);
                        // 数据库若不存在重复文件，则不删除刚才上传的文件
                        url = "http://" + serverIp + ":" + port + "/file/" + fileUUID;
                    }

                    // 存储数据库
                    Files saveFile = new Files();
                    saveFile.setName(originalFilename);
                    saveFile.setType(type);
                    saveFile.setSize(size / 1024 / 1024); // 单位 mb
                    saveFile.setUrl(url);
                    saveFile.setMd5(md5);
                    saveFile.setSchoolNo(schoolNo);

                    fileMapper.insert(saveFile);
                }

                break;
            }
            case "学校用户": {
                User user = userMapper.selectByPrimaryKey(userId);
                Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
                String schoolNo = schoolMapper.selectSchoolNoByDeptId(dept.getDeptId());
                String url;
                // 获取文件的md5
                String md5 = SecureUtil.md5(file.getInputStream());
                // 从数据库查询是否存在相同的记录
                Files dbFiles = getFileByMd5AndSchoolNo(md5, schoolNo);
                if (dbFiles != null) {
                    url = dbFiles.getUrl();
                } else {
                    // 上传文件到磁盘
                    file.transferTo(uploadFile);
                    // 数据库若不存在重复文件，则不删除刚才上传的文件
                    url = "http://" + serverIp + ":" + port + "/file/" + fileUUID;
                }

                // 存储数据库
                Files saveFile = new Files();
                saveFile.setName(originalFilename);
                saveFile.setType(type);
                saveFile.setSize(size / 1024 / 1024); // 单位 mb
                saveFile.setUrl(url);
                saveFile.setMd5(md5);
                saveFile.setSchoolNo(schoolNo);

                fileMapper.insert(saveFile);

                break;
            }
            case "教师用户": {
                User user = userMapper.selectByPrimaryKey(userId);
                Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
                Teacher teacher = teacherMapper.selectByDeptIdAndName(dept.getDeptId(), user.getNickName());
                String schoolNo = teacher.getSchoolNo();
                String url;
                // 获取文件的md5
                String md5 = SecureUtil.md5(file.getInputStream());
                // 从数据库查询是否存在相同的记录
                Files dbFiles = getFileByMd5AndSchoolNo(md5, schoolNo);
                if (dbFiles != null) {
                    url = dbFiles.getUrl();
                } else {
                    // 上传文件到磁盘
                    file.transferTo(uploadFile);
                    // 数据库若不存在重复文件，则不删除刚才上传的文件
                    url = "http://" + serverIp + ":" + port + "/file/" + fileUUID;
                }

                // 存储数据库
                Files saveFile = new Files();
                saveFile.setName(originalFilename);
                saveFile.setType(type);
                saveFile.setSize(size / 1024 / 1024); // 单位 mb
                saveFile.setUrl(url);
                saveFile.setMd5(md5);
                saveFile.setSchoolNo(schoolNo);

                fileMapper.insert(saveFile);

                break;
            }
            case "超级管理员": {
                String url;
                // 获取文件的md5
                String md5 = SecureUtil.md5(file.getInputStream());
                // 从数据库查询是否存在相同的记录
                Files dbFiles = getFileByMd5AndSchoolNo(md5, null);
                if (dbFiles != null) {
                    url = dbFiles.getUrl();
                } else {
                    // 上传文件到磁盘
                    file.transferTo(uploadFile);
                    // 数据库若不存在重复文件，则不删除刚才上传的文件
                    url = "http://" + serverIp + ":" + port + "/file/" + fileUUID;
                }

                // 存储数据库
                Files saveFile = new Files();
                saveFile.setName(originalFilename);
                saveFile.setType(type);
                saveFile.setSize(size / 1024 / 1024); // 单位 mb
                saveFile.setUrl(url);
                saveFile.setMd5(md5);

                fileMapper.insert(saveFile);

                break;
            }
        }

        // 从redis取出数据，操作完，再设置（不用查询数据库）
//        String json = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        List<Files> files1 = JSONUtil.toBean(json, new TypeReference<List<Files>>() {
//        }, true);
//        files1.add(saveFile);
//        setCache(Constants.FILES_KEY, JSONUtil.toJsonStr(files1));


        // 从数据库查出数据
//        List<Files> files = fileMapper.selectList(null);
//        // 设置最新的缓存
//        setCache(Constants.FILES_KEY, JSONUtil.toJsonStr(files));

        // 最简单的方式：直接清空缓存
        flushRedis(Constants.FILES_KEY);

        return Result.success();
    }

    */
/**
     * 文件下载接口
     *
     * @param fileUUID
     * @param response
     * @throws IOException
     *//*

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }


    */
/**
     * 通过文件的md5查询文件
     *
     * @param md5
     * @return
     *//*

    private Files getFileByMd5AndSchoolNo(String md5, String schoolNo) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        queryWrapper.eq("school_no", schoolNo);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    //    @CachePut(value = "files", key = "'frontAll'")
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        fileMapper.updateById(files);
        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(fileMapper.selectById(id));
    }


    //清除一条缓存，key为要清空的数据
//    @CacheEvict(value="files",key="'frontAll'")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    */
/**
     * 分页查询接口
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     *//*

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String fileType) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        long userId = loginUser.getUserId();
        Long role = userRoleMapper.getRoleIdsByUserId(userId);
        Role roleInfo = roleMapper.getRoleInfoById(role);
        String userRole = roleInfo.getRoleName();

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(fileType)) {
            queryWrapper.eq("type", fileType);
        }

        switch (userRole) {
            case "教体局用户": {
                User user = userMapper.selectByPrimaryKey(userId);
                Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
                List<Long> deptIds = deptMapper.selectChildIds(dept.getDeptId()); //学校对应的detpId
                List<String> schoolNo = new ArrayList<>();
                for(Long deptId : deptIds) {
                    schoolNo.add(schoolMapper.selectSchoolNoByDeptId(deptId));
                }
                queryWrapper.in("school_no", schoolNo);
                break;
            }
            case "学校用户": {
                User user = userMapper.selectByPrimaryKey(userId);
                Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
                String schoolNo = schoolMapper.selectSchoolNoByDeptId(dept.getDeptId());
                queryWrapper.eq("school_no", schoolNo);
                break;
            }
            case "教师用户": {
                User user = userMapper.selectByPrimaryKey(userId);
                Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
                Teacher teacher = teacherMapper.selectByDeptIdAndName(dept.getDeptId(), user.getNickName());
                String schoolNo = teacher.getSchoolNo();
                queryWrapper.eq("school_no", schoolNo);
                break;
            }
            case "超级管理员": {
                break;
            }
        }

        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 设置缓存
    private void setCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    // 删除缓存
    private void flushRedis(String key) {
        stringRedisTemplate.delete(key);
    }

}
*/
