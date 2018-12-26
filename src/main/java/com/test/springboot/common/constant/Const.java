package com.test.springboot.common.constant;

import java.util.HashMap;
import java.util.Map;

public class Const {

	public static final Map<Byte, String> ORDER_STATUS_MAP = new HashMap<Byte, String>(){{
		put((byte) 0, "待提交");
		put((byte) 1, "待审核");
		put((byte) 2, "待确认");
		put((byte) 3, "待结款");
		put((byte) 4, "已结款");
		put((byte) 5, "已到期");
		put((byte) 6, "已违约");
		put((byte) 7, "已完成");
		put((byte) 8, "已关闭");
	}};
	
	public static final Map<String, String> RISK_YOUDUN_RULE_MAP = new HashMap<String, String>(){{
		put("NAME0001", "姓名身份证命中法院失信名单");
		put("NAME0002", "直接关联身份证号命中法院失信名单");
		put("NAME0003", "身份证命中网贷失信名单");
		put("NAME0004", "直接关联身份证命中网贷失信名单");
		put("NAME0005", "身份证命中网贷不良名单");
		put("NAME0006", "直接关联身份证命中网贷不良名单");
		put("NAME0007", "身份证命中网贷短时逾期名单");
		put("NAME0008", "身份证命中网贷短时逾期名单");
		put("NAME0009", "身份证号命中活体攻击名单");
		put("NAME0010", "设备号命中活体攻击名单");
		put("NAME0011", "直接关联身份证命中活体攻击名单");
		put("NAME0012", "直接关联设备号命中活体攻击名单");
		put("NAME0013", "手机号命中虚假手机号");
		put("GANG0001", "团伙成员数量为10人以下");
		put("GANG0002", "团伙成员数量为11-50人");
		put("GANG0003", "团伙成员数量为51-100人");
		put("GANG0004", "团伙成员数量为100-501人");
		put("GANG0005", "团伙成员数量为501人以上");
		put("GANG0006", "用户与团伙的关系为共用设备");
		put("GANG0007", "用户与团伙的关系为共用银行卡");
		put("GANG0008", "用户与团伙的关系为共用手机号");
		put("GANG0009", "用户与团伙的关系为通话");
		put("GANG0010", "当前用户直接关联的用户数量／团伙总数量 <5%");
		put("GANG0011", "当前用户直接关联的用户数量／团伙总数量 >=5% & <15%");
		put("GANG0012", "当前用户直接关联的用户数量／团伙总数量 >=15% & <30%");
		put("GANG0013", "当前用户直接关联的用户数量／团伙总数量 >=30%");
		put("GANG0014", "当前用户直接关联核心节点");
		put("GANG0015", "（含当前用户）同商户在该团伙中的历史用户总数量>5个");
		put("DEVI0001", "30天内借款时设备缺失的次数 >0");
		put("DEVI0002", "累计借款时设备缺失的次数 >0");
		put("DEVI0003", "安卓设备当前安装的借款类app数量 >10 & <=20");
		put("DEVI0004", "安卓设备当前安装的借款类app数量 >20");
		put("DEVI0005", "安卓设备历史安装的借款类app的最大数量比当前数量的差值 >5");
		put("DEVI0006", "安卓设备当前安装借款app占比总app的比例 >50%");
		put("DEVI0007", "安卓设备当前安装赌博类app的数量 >3");
		put("DEVI0008", "安卓设备历史安装赌博类app的最大数量 >3");
		put("DEVI0009", "（qq、微信、支付宝、淘宝、滴滴打车）安卓设备当前安装常用app的数量 =0");
		put("DEVI0010", "（含当前）安卓设备历史安装作弊app的最大数量 >0");
		put("DEVI0011", "（含当前）安卓设备历史篡改关键信息 >0");
	}};
	
	public static final Map<Integer, String> USER_BOOK_RELATION_MAP = new HashMap<Integer, String>() {{
		put(1, "父母");
		put(2, "配偶");
		put(3, "朋友");
	}};
	
	public  static final String REMOTE_LOG_PLATFORM_CK_LOAN_ADMIN = "ck_loan_admin";
	public  static final String REMOTE_LOG_PLATFORM_CK_LOAN_TIMER = "ck_loan_timer";
	public  static final String REMOTE_LOG_ENV_PROD = "prod";
	public  static final String REMOTE_LOG_ENV_DEV = "dev";
}
