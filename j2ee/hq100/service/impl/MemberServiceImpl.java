/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.fivestars.interfaces.bbs.util.UcenterUtil;
import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.holder.ApplicationContextHolder;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.MemberLoginResp;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;

import com.up72.framework.util.page.PageBounds;

import com.up72.hq.redis.RedisCacheClient;
import com.up72.hq.utils.*;
import com.up72.hq.utils.third.dto.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IMemberService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人用户DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class MemberServiceImpl implements  IMemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private UserLoginLogMapper userLoginLogMapper;
	@Autowired
	private ThirdPartyUsersMapper thirdPartyUsersMapper;
	@Autowired
	private MemberMessageMapper memberMessageMapper;

	public void save(Member member){
		memberMapper.insert(member);
	}

    public void update(Member member){
		memberMapper.update(member);
	}
	
    public void delete(java.lang.Long id){
		memberMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public MemberResp getById(java.lang.Long id){
		return memberMapper.findById(id);
	}
    @Transactional(readOnly=true)
    public MemberResp getByPhone(java.lang.String phone){
		return memberMapper.findByPhone(phone);
	}

	@Override
	public Member getId(Long id) {
		return memberMapper.findId(id);

	}

	@Transactional(readOnly=true)
    public Page<MemberResp> getPage(Map params, PageBounds rowBounds){
        PageList list = memberMapper.findPage(params, rowBounds);
		return new Page<MemberResp>(list,list.getPagination());
	}

	@Override
	public Member checkUserAccount(String loginAccount) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("loginAccount",loginAccount.trim());
		Member member = memberMapper.findUserByLoginAccount(map);
		return member;
	}

	@Override
	public MemberLoginResp userLogin(String loginAccount, String password, String type, HttpServletRequest request) {
		MemberLoginResp resp=new MemberLoginResp();
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("loginAccount",loginAccount.trim());
			map.put("password", MD5Util.toMD5(password.trim()));
			MemberResp member=memberMapper.findUserByLoginAccountAndPassword(map);
			UserLoginLog loginLog=new UserLoginLog();
			if(member!=null){
				if(member.getIsDisable() ==null || member.getIsDisable() == 0){//是否禁用
					MemberResp memberResp=memberMapper.findById(member.getId());
					resp.setMember(member);
					if(member.getType() == Cnst.Member.MEMBER_TYPE) { // 普通用户
						resp.setLoginType("1");
						resp.setLoginStatus(true);
					}
					request.getSession().setAttribute(Cnst.Member.LOGIN_MEMBER,memberResp);
				}else{
					resp.setDisableRemarks(member.getDisableRemarks());
					resp.setLoginStatus(false);
					resp.setDisableRemarksStatus(1);
				}
				loginLog.setLoginTime(Cnst.getCurTime());
				loginLog.setMemberId(member.getId());
				loginLog.setLoginType(0L);
				loginLog.setLoginUserType(0L);
				loginLog.setLoginIp(HqUtil.getIpRemoteHost(request));
				loginLog.setMessage("PC login");
				userLoginLogMapper.insert(loginLog);
			}else{
				resp.setLoginStatus(false);
			}
			return resp;
		}catch (Exception e ){
			e.printStackTrace();
			resp.setLoginStatus(false);
			return resp;
		}
	}

	@Override
	public Boolean autoLogin(String loginAccount, HttpServletRequest request) {
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("loginAccount",loginAccount.trim());
			Member member = memberMapper.findUserByLoginAccount(map);
			request.getSession().setAttribute(Cnst.Member.LOGIN_MEMBER,member);
			return true;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	public String resetPassword(String loginAccount, String oldPassword, String newPassword, Integer passwordLevel) {
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("loginAccount",loginAccount.trim());
			map.put("password", oldPassword.trim());
			Member user = memberMapper.findUserByLoginAccountAndPassword(map);
			if(user != null){
				user.setPassword(MD5Util.toMD5(newPassword.trim()));
				user.setPasswordLevel(passwordLevel);
				memberMapper.update(user);
				UcenterUtil.editUser(user.getUserName(),newPassword,user.getEmail()!=null?user.getEmail():"");
				return "success";
			}else{
				return "user_error";
			}
		}catch (Exception e){
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public Boolean isLoginAccount(String loginAccount) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("loginAccount",loginAccount.trim());
		Member user = memberMapper.findUserByLoginAccount(map);
		if(user != null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Boolean isfindUserByEmail(String loginAccount) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("loginAccount",loginAccount.trim());
		Member user = memberMapper.findUserByEmail(map);
		if(user != null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isExistsEmail(Long id, String email) {
		if(email == null) {
			return false;
		} else {
			Map<String,String> map = new HashMap<String,String>();

			map.put("loginAccount",email.trim());

			Member member = memberMapper.findUserByLoginAccount(map);
			return id == null && member != null?true:id != null && member != null && member.getId().longValue() != id.longValue();
		}
	}

	@Override
	public String register(Member member, HttpServletRequest request,HttpServletResponse response) {
		String password=member.getPassword();
		//验证注册信息
		String verify=verifyRegisterInformation(member.getUserName(), member.getPhone(), member.getPicCode(), member.getPhoneCode(), request,response);
		if(verify.equals("success")) {
			member.setAddTime(Cnst.getCurTime());
			member.setIsPhone(1);
			member.setIsDelete(0);
			member.setIsDisable(0);
			member.setGender(0);
			member.setType(1);
			member.setPassword(MD5Util.toMD5(member.getPassword()));
			member.setNickName(member.getUserName());
			member.setRealName(member.getNickName());
			member.setLevel(1);
			member.setIsSysMessage(1);
			member.setIsSysNoticeMessage(1);
			member.setScore(0);
			memberMapper.insert(member);
			Long id=member.getId();
			String custNo=String.valueOf(id<10?"0"+id:id);
			member.setCustNo(DateUtils.getCurrYear()+custNo);
			update(member);
			UcenterUtil.register(member.getUserName(),password, SNCode.getTradeNo()+"@qq.com");//discuz 论坛用户注册

			//用户消息
			MemberMessage memberMessage=new MemberMessage();
			memberMessage.setTitle("欢迎加入长彩5918");
			memberMessage.setContent("亲爱的会员：恭喜您成为长彩5918正式会员");
			memberMessage.setAddTime(Cnst.getCurTime());
			memberMessage.setStatus(0);
			memberMessage.setHqMemberId(member.getId());
			memberMessage.setIsDel(0);
			memberMessage.setType(2);
			memberMessageMapper.insert(memberMessage);

			request.getSession().removeAttribute(Cnst.Member.PHONE_CODE);
			request.getSession().removeAttribute(Cnst.Member.PIC_CODE);
			//删除验证码
			String key= CookieUtil.cookiePhoneRandom(request,response);
			RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");
			redisCacheClient.delete(key);

			//上一次单点登录key值
			MemberResp memberResp=memberMapper.findById(member.getId());
			request.getSession().setAttribute(Cnst.Member.LOGIN_MEMBER,memberResp);
			String currLoginKey = CookieUtil.cookieSingleLoginRandom(request,response);
			redisCacheClient.set(member.getId()+"",currLoginKey,Cnst.SingleLogin.SINGLE_LOGIN_SECOND);
			String $ucsynlogin= UcenterUtil.login(member.getUserName(),password);//discuz 论坛信息同步登录
			redisCacheClient.set(Cnst.Member.DISCUZ_LOGIN,$ucsynlogin);
		}
		return verify;
	}

	private String verifyRegisterInformation(String userName, String phone, String picC, String phoneC, HttpServletRequest request, HttpServletResponse response){

		if(isLoginAccount(userName)){//用户名不存在记录
			if(isLoginAccount(phone)){//手机号不存在记录
				String phoneCode=request.getSession().getAttribute(Cnst.Member.PHONE_CODE)+"";
				String picCode=request.getSession().getAttribute(Cnst.Member.PIC_CODE)+"";
				if(picCode.equalsIgnoreCase(picC)){
					if(phoneCode.equalsIgnoreCase(phoneC)){
						//手机验证码 核对
						String key= CookieUtil.cookiePhoneRandom(request, response);
						RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");
						String phoneCodeStr = redisCacheClient.get(key);
						if(StringUtils.isNotEmpty(phoneCodeStr)){
							String []array = phoneCodeStr.split(",");
							if(phoneCode.equals(array[1]) && phone.equals(array[0])){
								return "success";
							}else{
								return "phoneCode";
							}
						}else{
							return "phoneCode";
						}
					}else{
						return "phoneCode";
					}
				}else{
					return "picCode";
				}
			}else{
				return "phone";
			}
		}else{
			return "nickName";
		}
	}


	@Override
	public String registerUser(Member user, HttpServletRequest request) {
		Member dhsUser = (Member) request.getSession().getAttribute(Cnst.Member.LOGIN_MEMBER);
		dhsUser.setUserName(user.getUserName());
		dhsUser.setNickName(user.getNickName());
		dhsUser.setGender(user.getGender());
		memberMapper.update(dhsUser);
		return "success";
	}

	@Override
	public String thirdPartyUserBinding(Member user, HttpServletRequest request,HttpServletResponse response) {
		String userName=user.getUserName();
		String phone=user.getPhone();
		String password=user.getPassword();
		//验证绑定信息
		String verify=verifyRegisterInformation(userName,phone,user.getPicCode(),user.getPhoneCode(),request,response);
		if(verify.equals("success")){
			Member newMember=memberMapper.findById(user.getId());
			newMember.setUserName(userName);
			newMember.setPhone(phone);
			newMember.setIsPhone(1);
			newMember.setPassword(MD5Util.toMD5(password));
			newMember.setPasswordLevel(user.getPasswordLevel());
			memberMapper.update(newMember);
		}
		return verify;
	}

	@Override
	public MemberLoginResp qqLogin(String code, String ip) {
		String accessToken=	QQUtil.getACCESS_TOKEN(code);//获取tokne
		String openId=QQUtil.getOPEN_ID(accessToken);//获取openid
		QQUserInfoDto qqUser=QQUtil.getUserInfoDto(openId,accessToken);//获取用户信息

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("thirdUserSn",openId);

		ThirdPartyUsers thirdUser=thirdPartyUsersMapper.findThirdPartyUsersByUserSn(map);
		MemberLoginResp userLoginResp=new MemberLoginResp();
		userLoginResp.setThirdPartyUserImg(qqUser.getFigureurl());
		userLoginResp.setThirdPartyUsersNickName(qqUser.getNickname());
		if(thirdUser == null){// 第一次QQ登录新增信息
			thirdUser=new ThirdPartyUsers();
			thirdUser.setAddTime(new Date().getTime());
			thirdUser.setThirdUserSn(openId);
			thirdUser.setType(1);
			thirdPartyUsersMapper.insert(thirdUser);
		}else{
			//扫码过
			if(thirdUser.getMemberId() != null ){//绑定过
				userLoginResp.setMember(memberMapper.findById(thirdUser.getMemberId()));
				UserLoginLog loginLog=new UserLoginLog();
				loginLog.setLoginTime(new Date().getTime());
				loginLog.setMemberId(thirdUser.getMemberId());
				loginLog.setLoginType(0L);
				loginLog.setLoginUserType(1L);
				loginLog.setLoginIp(ip);
				loginLog.setMessage("QQ login");
				userLoginLogMapper.insert(loginLog);
			}
		}
		userLoginResp.setThirdPartyUsers(thirdUser);
		return userLoginResp;
	}

	@Override
	public MemberLoginResp wxLogin(String code,String ip) {
		WXAccessTokenDto accessToken=	WXUtil.getACCESS_TOKENAndOpenId(code);//获取tokne
		WXUserInfoDto wxUser=WXUtil.getUserInfoDto(accessToken.getOpenid(),accessToken.getAccess_token()) ;//获取用户信息
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("thirdUserSn",accessToken.getOpenid());
		ThirdPartyUsers thirdUser=thirdPartyUsersMapper.findThirdPartyUsersByUserSn(map);
		MemberLoginResp userLoginResp=new MemberLoginResp();
		userLoginResp.setThirdPartyUserImg(wxUser.getHeadimgurl());
		userLoginResp.setThirdPartyUsersNickName(wxUser.getNickname());
		if(thirdUser == null){// 第一次WX登录新增信息
			thirdUser=new ThirdPartyUsers();
			thirdUser.setAddTime(new Date().getTime());
			thirdUser.setThirdUserSn(accessToken.getOpenid());
			thirdUser.setType(2);
			thirdPartyUsersMapper.insert(thirdUser);
		}else{
			//扫码过
			if(thirdUser.getMemberId() != null ){//绑定过
				userLoginResp.setMember(memberMapper.findById(thirdUser.getMemberId()));
				UserLoginLog loginLog=new UserLoginLog();
				loginLog.setLoginTime(new Date().getTime());
				loginLog.setMemberId(thirdUser.getMemberId());
				loginLog.setLoginType(0L);
				loginLog.setLoginUserType(2L);
				loginLog.setLoginIp(ip);
				loginLog.setMessage("WX login");
				userLoginLogMapper.insert(loginLog);
			}
		}
		userLoginResp.setThirdPartyUsers(thirdUser);
		return userLoginResp;
	}

	@Override
	public MemberLoginResp wbLogin(String code,String ip) {

		WBAccessTokenDto accessToken=	WBUtil.getAccessTokenAndUid(code);//获取tokne

		WBUserInfoDto wbUser=WBUtil.getUserInfo(accessToken.getAccess_token(),accessToken.getUid()) ;//获取用户信息

		Map<String,Object> map=new HashMap<String,Object>();

		map.put("thirdUserSn",accessToken.getUid());

		ThirdPartyUsers thirdUser=thirdPartyUsersMapper.findThirdPartyUsersByUserSn(map);
		MemberLoginResp userLoginResp=new MemberLoginResp();
		userLoginResp.setThirdPartyUserImg(wbUser.getProfile_image_url());
		userLoginResp.setThirdPartyUsersNickName(wbUser.getScreen_name());
		if(thirdUser == null){// 第一次微博登录新增信息
			thirdUser=new ThirdPartyUsers();
			thirdUser.setAddTime(new Date().getTime());
			thirdUser.setThirdUserSn(accessToken.getUid());
			thirdUser.setType(3);
			thirdPartyUsersMapper.insert(thirdUser);
		}else{
			//扫码过
			if(thirdUser.getMemberId() != null ){//绑定过
				userLoginResp.setMember(memberMapper.findById(thirdUser.getMemberId()));
				UserLoginLog loginLog=new UserLoginLog();
				loginLog.setLoginTime(new Date().getTime());
				loginLog.setMemberId(thirdUser.getMemberId());
				loginLog.setLoginType(0L);
				loginLog.setLoginUserType(4L);
				loginLog.setLoginIp(ip);
				loginLog.setMessage("WB login");
				userLoginLogMapper.insert(loginLog);
			}
		}
		userLoginResp.setThirdPartyUsers(thirdUser);
		return userLoginResp;
	}

	@Override
	public void updateMember(Member user) {
		Member member = memberMapper.findById(user.getId());
		if(StringUtils.isNotBlank(user.getNickName())){
			member.setNickName(user.getNickName());
		}
		member.setGender(user.getGender());
		if(StringUtils.isNotBlank(user.getEmail())){
			member.setEmail(user.getEmail());
		}
		if(StringUtils.isNotBlank(user.getUserName())){
			member.setUserName(user.getUserName());
		}
		if(StringUtils.isNotBlank(user.getHeadImg())){
			member.setHeadImg(user.getHeadImg());
		}
		memberMapper.update(member);
	}

	@Override
	public Boolean resetMemberPassword(Long dhsUserId, String newPassword, Integer passwordLevel) {
		try{
			Member member=memberMapper.findById(dhsUserId);
			member.setPassword(MD5Util.toMD5(newPassword));
			member.setPasswordLevel(passwordLevel);
			memberMapper.update(member);
			return true;
		}catch (Exception e){
			return false;
		}

	}
	@Transactional
	public Long getUserCnt(Map params) {
		return memberMapper.findUserCnt(params);
	}

	@Override
	public List<Member> getAll() {
		return memberMapper.findAll();
	}

	@Override
	public Map bandThirdUser(Member member, String openId, String headImg, String nickName,Integer type) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("thirdUserSn",openId);
		map.put("type",type);
		ThirdPartyUsers thirdUser=thirdPartyUsersMapper.findThirdPartyUsersByUserSn(map);
		if(thirdUser.getMemberId() != null){
			returnMap.put("bandStatus",1);
			returnMap.put("bandMess","用户已绑定其他第三方账号!");
		}else{
			if(StringUtils.isNotEmpty(headImg)){
				member.setHeadImg(headImg);
			}
			if(StringUtils.isNotEmpty(nickName)){
				member.setNickName(nickName);
			}
			memberMapper.update(member);
			thirdUser.setMemberId(member.getId());
			thirdPartyUsersMapper.update(thirdUser);
			returnMap.put("bandStatus",2);
			returnMap.put("bandMess","绑定成功!");
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> phoneRegister(String loginAccount,String phone, String password,String code, String phoneCode,Map bandMap) {
		Map<String,Object> result = new HashMap<String, Object>();
		if(isLoginAccount(loginAccount)){
			if(isLoginAccount(phone)){
				RedisCacheClient redisCacheClient=(RedisCacheClient) ApplicationContextHolder.getBean("redisCacheClientCore");
				String phoneCodeVal = redisCacheClient.get(code+ "phone");
				phoneCode = phone+","+phoneCode;
				if(phoneCodeVal.equals(phoneCode)){
					if(password.length() >5){
						Member member=new Member();
						member.setUserName(loginAccount);
						member.setAddTime(Cnst.getCurTime());
						member.setIsPhone(1);
						member.setIsDelete(0);
						member.setIsDisable(0);
						member.setGender(0);
						member.setType(1);
						member.setPhone(phone);
						member.setPassword(MD5Util.toMD5(password));
						member.setNickName(member.getNickName());
						member.setRealName("");
						member.setLevel(1);
						member.setIsSysMessage(1);
						member.setIsSysNoticeMessage(1);
						member.setScore(0);
						memberMapper.insert(member);
						Long id=member.getId();
						String custNo=String.valueOf(id<10?"0"+id:id);
						member.setCustNo(DateUtils.getCurrYear()+custNo);
						update(member);

						UcenterUtil.register(member.getUserName(),password, SNCode.getTradeNo()+"@qq.com");//discuz 论坛用户注册

						//用户消息
						MemberMessage memberMessage=new MemberMessage();
						memberMessage.setTitle("欢迎加入长彩5918");
						memberMessage.setContent("亲爱的会员：恭喜您成为长彩5918正式会员");
						memberMessage.setAddTime(Cnst.getCurTime());
						memberMessage.setStatus(0);
						memberMessage.setHqMemberId(member.getId());
						memberMessage.setIsDel(0);
						memberMessage.setType(2);
						memberMessageMapper.insert(memberMessage);

						result.put("success",true);
						result.put("message","注册成功,手机号码为登录用户名!");

						//绑定第三方
						String isBand = bandMap.get("isBand")+"";
						if("1".equals(isBand)){
							String openId = bandMap.get("openId")+"";
							String headImg = bandMap.get("headImg")+"";
							String nickName = bandMap.get("nickName")+"";
							String type = bandMap.get("type")+"";
							if(StringUtils.isNotEmpty(openId)){
								Map<String,Object> bandMap2 = bandThirdUser(member,openId,headImg,nickName,Integer.parseInt(type));
								result.put("bandStatus",bandMap2.get("bandStatus"));
								result.put("bandMess",bandMap2.get("bandMess"));
							}else{
								result.put("bandStatus",1);
								result.put("bandMess","第三方参数异常!");
							}
						}
					}else{
						result.put("success",false);
						result.put("message","密码过于简单,请重新设置!");
					}
				}else{
					result.put("success",false);
					result.put("message","手机验证码错误!");
				}
			}else{
				result.put("success",false);
				result.put("message","手机号码已被抢注");
			}
		}else{
			result.put("success",false);
			result.put("message","用户名不可用或已被抢注!");
		}

		return result;
	}

	@Override
	public MemberLoginResp appQQLogin(String code, String ip) {

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("thirdUserSn",code);
		ThirdPartyUsers thirdUser=thirdPartyUsersMapper.findThirdPartyUsersByUserSn(map);
		MemberLoginResp userLoginResp=new MemberLoginResp();
		if(thirdUser == null){// 第一次WX登录新增信息
			thirdUser=new ThirdPartyUsers();
			thirdUser.setAddTime(new Date().getTime());
			thirdUser.setThirdUserSn(code);
			thirdUser.setType(1);
			thirdPartyUsersMapper.insert(thirdUser);
		}else{
			//扫码过
			if(thirdUser.getMemberId() != null ){//绑定过
				userLoginResp.setMember(memberMapper.findById(thirdUser.getMemberId()));
				UserLoginLog loginLog=new UserLoginLog();
				loginLog.setLoginTime(new Date().getTime());
				loginLog.setMemberId(thirdUser.getMemberId());
				loginLog.setLoginType(0L);
				loginLog.setLoginUserType(1L);
				loginLog.setLoginIp(ip);
				loginLog.setMessage("QQ login");
				userLoginLogMapper.insert(loginLog);
			}
		}
		userLoginResp.setThirdPartyUsers(thirdUser);
		return userLoginResp;
	}

	@Override
	public MemberLoginResp appWxLogin(String code, String ip) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("thirdUserSn",code);
		ThirdPartyUsers thirdUser=thirdPartyUsersMapper.findThirdPartyUsersByUserSn(map);
		MemberLoginResp userLoginResp=new MemberLoginResp();
		if(thirdUser == null){// 第一次WX登录新增信息
			thirdUser=new ThirdPartyUsers();
			thirdUser.setAddTime(new Date().getTime());
			thirdUser.setThirdUserSn(code);
			thirdUser.setType(2);
			thirdPartyUsersMapper.insert(thirdUser);
		}else{
			//扫码过
			if(thirdUser.getMemberId() != null ){//绑定过
				userLoginResp.setMember(memberMapper.findById(thirdUser.getMemberId()));
				UserLoginLog loginLog=new UserLoginLog();
				loginLog.setLoginTime(new Date().getTime());
				loginLog.setMemberId(thirdUser.getMemberId());
				loginLog.setLoginType(0L);
				loginLog.setLoginUserType(2L);
				loginLog.setLoginIp(ip);
				loginLog.setMessage("WX login");
				userLoginLogMapper.insert(loginLog);
			}
		}
		userLoginResp.setThirdPartyUsers(thirdUser);
		return userLoginResp;
	}

	@Override
	public Boolean isTrueUser(String memberId) {
		Boolean result = false;
		if(StringUtils.isNotEmpty(memberId)){
			Member member = memberMapper.findById(Long.parseLong(memberId));
			if(ObjectUtils.isNotEmpty(member) && member.getIsDisable() == 0 && member.getIsDelete() == 0){
				result = true;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> upDatePwd(Member member, String oldPassword, String newPassword) {
		Map<String, Object> result = new HashMap<String, Object>();
		if((MD5Util.toMD5(oldPassword)).equals(member.getPassword())){
			if(newPassword.length() >= 6){
				member.setPassword(MD5Util.toMD5(newPassword));
				memberMapper.update(member);
				result.put("success",true);
				result.put("message","修改成功!");
			}else{
				result.put("success",false);
				result.put("message","新密码不合法!");
			}
		}else{
			result.put("success",false);
			result.put("message","原密码错误!");
		}
		return result;
	}

}
