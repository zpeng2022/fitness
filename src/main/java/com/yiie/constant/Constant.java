package com.yiie.constant;

import io.swagger.models.auth.In;

/**
 * Time：2020-1-2 9:26
 * Email： yiie315@163.com
 * Desc：全局常量类
 *
 * @author： yiie
 * @version：1.0.0
 */
public class Constant {
    /*gym场馆照片默认背景地址*/
    public static final double nearly_gym=10d;

    public static final int gym_pictureNum=3;
    //    public static final String gym_defaultPicture="https://bigman1718.oss-cn-hangzhou.aliyuncs.com/gymPicture/default/default2.png";
    public static final String gym_defaultPicture="default2.png";
    public static final String gym_defaultPath="/pic/";
    public static final String gym_defaultPicturePath="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\整体项目\\20221018\\fitness\\src\\main\\resources\\static\\pic\\";
    public static final String fileRoot="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\";

    public static final String gym_defaultPictureBase64="data:image/png;base64,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";
    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN="authorization";
    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN="refresh_token";

    /**
     * 创建时间
     */
    public static final String CREATED = "created";

    /**
     * 权限key
     */
    public static final String JWT_PERMISSIONS_KEY="jwt-permissions-key";

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_NAME="jwt-user-name-key";

    /**
     * 角色key
     */
    public static final String JWT_ROLES_KEY="jwt-roles-key_";

    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY="jwt-refresh-key_";

    /**
     *  刷新状态(适用场景如：一个功能点要一次性请求多个接口，当第一个请求刷新接口时候 加入一个节点下一个接口请求进来的时候直接放行)
     */
    public static final String JWT_REFRESH_STATUS="jwt-refresh-status_";

    /**
     * 标记新的access_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION="jwt-refresh-identification_";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST="jwt-access-token-blacklist_";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST="jwt-refresh-token-blacklist_";

    /**
     * 组织机构编码key
     */
    public static final String DEPT_CODE_KEY="dept-code-key_";

    /**
     * 菜单权限编码key
     */
    public static final String PERMISSION_CODE_KEY="permission-code-key_";

    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY="deleted-user-key_";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY="account-lock-key_";

    /**
     * 用户权鉴缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY="shiro-cache:com.yiie.shiro.CustomRealm.authorizationCache:";

}
