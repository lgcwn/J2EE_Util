/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.MemberLoginResp;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.model.Member;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 个人用户接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IMemberService {
	
	void save(Member member);

    void update(Member member);
	
	void delete(java.lang.Long id);

    MemberResp getById(java.lang.Long id);

    MemberResp getByPhone(java.lang.String phone);

    Member getId(java.lang.Long id);

    Page<MemberResp> getPage(Map params, PageBounds rowBounds);

    /**
     *
     * 根据账户检查用户是否存在
     *
     * @author liuguicheng
     * @param loginAccount 登录账户 (邮箱,用户名,手机)
     * @return null 不存在
     */
    Member checkUserAccount(String loginAccount);


    /**
     * 普通会员登录
     *
     * 登录失败返回 false
     *
     * 登录成功返回 true
     *
     * @author liuguicheng
     * @param loginAccount 登录账户 (邮箱,用户名,手机)
     * @param password
     * @return
     */
    MemberLoginResp userLogin(String loginAccount, String password, String type, HttpServletRequest request);

    /**
     *
     * 会员自动登录
     *
     * 登录失败返回 false
     *
     * 登录成功返回 true
     *
     * @author liuguicheng
     * @param loginAccount
     * @param request
     * @return
     */
    Boolean autoLogin(String loginAccount,HttpServletRequest request);

    /**
     *
     * 重设密码
     *
     * @author liuguicheng
     * @param loginAccount 登录账户 (邮箱,用户名,手机)
     * @param oldPassword
     * @param newPassword
     * @return
     */
    String resetPassword(String loginAccount,String oldPassword,String newPassword,Integer passwordLevel);


    /**
     *
     * 验证登录账户是否唯一
     *
     * @author liuguicheng
     * @param loginAccount 登录账户 (邮箱,用户名,手机)
     * @return 返回true为不存在该登录账户
     *
     */
    Boolean isLoginAccount(String loginAccount);

    /**
     *
     * 验证邮箱是否验证
     *
     * @author zhangdongdong
     *
     */
    Boolean isfindUserByEmail(String loginAccount);

    /**
     *
     * 验证邮箱是否唯一，包括修改信息时的判断
     *
     *
     */
    boolean isExistsEmail(Long id, String email);


    /**
     *
     * 普通用户注册
     *
     * @author liuguicheng
     * @param member
     * @return 返回相应逻辑码
     */
    String register(Member member, HttpServletRequest request,HttpServletResponse response);

    /**
     *
     * 个人中心的个人信息的修改
     *
     * @author zhangdongdong
     */
    String registerUser(Member user, HttpServletRequest request);



    /**
     *
     * 第三方用户绑定
     *
     * @author liuguicheng
     * @param user
     * @param request
     * @return
     */
    String thirdPartyUserBinding(Member user, HttpServletRequest request,HttpServletResponse response);


    /**
     * QQ登录
     * @author liuguicheng
     * @param code
     * @return
     */
    MemberLoginResp qqLogin(String code,String ip);

    /**
     * 微信登录
     * @param code
     * @param ip
     * @return
     */
    MemberLoginResp wxLogin(String code,String ip);
    /**
     * 微博登录
     * @param code
     * @param ip
     * @return
     */
    MemberLoginResp wbLogin(String code,String ip);

    /**
     *
     * 用户修改信息
     *
     * @author zhangdongdong
     * @return
     */
    void updateMember(Member user);

    /**
     *
     * 重设密码
     *
     * @author liuguicheng
     * @param dhsUserId
     * @param newPassword
     * @return
     */
    Boolean resetMemberPassword(Long dhsUserId,String newPassword,Integer passwordLevel);

    Long getUserCnt(Map params);

    List<Member> getAll();

    //绑定第三方用户
    Map bandThirdUser(Member member,String openId,String headImg,String nickName,Integer type);

    /**
     *
     * 手机用户注册
     *
     * @author liuguicheng
     * @param phone
     * @param password
     * @param code      手机识别码
     * @param phoneCode 手机验证码
     * @return 成功 or 失败(原因)
     * */
    Map<String,Object> phoneRegister(String loginAccount,String phone,String password,String code,String phoneCode,Map map);


    /**
     *
     * QQ登录
     *
     * @author liuguicheng
     * @param code
     * @return
     */
    MemberLoginResp appQQLogin(String code,String ip);


    /**
     *
     * wx登录
     *
     * @param code
     * @param ip
     * @return
     */
    MemberLoginResp appWxLogin(String code,String ip);

    /**
     * 判断用户
     * */
    Boolean isTrueUser(String memberId);

    /**
     * 修改密码
     * */
    Map<String,Object> upDatePwd(Member member,String oldPassword,String newPassword);


}
