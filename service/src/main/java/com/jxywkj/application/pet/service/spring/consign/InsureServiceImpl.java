package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.common.utils.AesEncodeUtil;
import com.jxywkj.application.pet.common.utils.MD5Util;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.common.utils.dadi.ConstantNames;
import com.jxywkj.application.pet.common.utils.dadi.GZipUtils;
import com.jxywkj.application.pet.common.utils.dadi.Md5UtilForJt;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderTransport;
import com.jxywkj.application.pet.model.dto.dadi.XmlHead;
import com.jxywkj.application.pet.model.dto.dadi.request.*;
import com.jxywkj.application.pet.model.dto.dadi.response.InsureResponseDto;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.StringUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.utils.network.HttpUtil;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-15 19:45
 * @Version 1.0
 */
@Service
public class InsureServiceImpl implements InsureService {

    @Autowired
    HttpUtil httpUtil;

    @Value("${insur.mchid}")
    String mchid;

    @Value("${insur.key}")
    String key;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    OrderTransportService orderTransportService;

    @Resource
    StationService stationService;

    /**
     * 猫
     */
    private static final String PET_TYPE_CAT = "猫类";
    /**
     * 狗
     */
    private static final String PET_TYPE_DOG = "犬类";

    /**
     * 其他
     */
    private static final String OTHER = "其他";

    /**
     * 投保概率
     */
    private final static double INSURE_PROBABILITY = 0.5D;

    /**
     * 身份集合大小 注意必须和构造代码块中的添加身份证数量一致！！！！！
     */
    private final int ID_CARD_LIST_SIZE = 26;
    /**
     * 身份证集合
     * */
    private final List<String> ID_CARD_LIST = new ArrayList<>(ID_CARD_LIST_SIZE);
    /**
     * 狗类集合
     */
    private String[] dogTypeArray;
    /**
     * 猫类集合
     */
    private String[] catTypeArray;


    {
        // 添加的数量要和ID_CARD_LIST_SIZE一致不可多也不可少
        ID_CARD_LIST.add("41138119831111483X");
        ID_CARD_LIST.add("152321198609141516");
        ID_CARD_LIST.add("132335197109030051");
        ID_CARD_LIST.add("32010719810306341X");
        ID_CARD_LIST.add("362202200208050108");
        ID_CARD_LIST.add("431124198606204475");
        ID_CARD_LIST.add("420683198905044269");
        ID_CARD_LIST.add("370304197809095129");
        ID_CARD_LIST.add("452122199801086327");
        ID_CARD_LIST.add("362424198206246456");
        ID_CARD_LIST.add("360425200010107328");
        ID_CARD_LIST.add("362527200104030026");
        ID_CARD_LIST.add("360521199703241022");
        ID_CARD_LIST.add("330781199810250970");
        ID_CARD_LIST.add("362322199510204513");
        ID_CARD_LIST.add("360782199512260013");
        ID_CARD_LIST.add("360122199404080622");
        ID_CARD_LIST.add("360103199407011236");
        ID_CARD_LIST.add("362222197810268418");
        ID_CARD_LIST.add("341102199412260211");
        ID_CARD_LIST.add("51072519970418006X");
        ID_CARD_LIST.add("360103197702152251");
        ID_CARD_LIST.add("362322200012280041");
        ID_CARD_LIST.add("210782198610310224");
        ID_CARD_LIST.add("360111198706036076");
        ID_CARD_LIST.add("362228199312271814");

        // 狗类
        String petClassify = "京巴,博美,吉娃娃,比格犬,沙皮,斑点,巴吉度,可卡银狐,贵宾,腊肠犬,西地高梗,西施,小鹿犬,哈士奇,杜宾,苏格兰梗,西高地白梗,拉布拉多,柯基犬,英国斗牛犬,蝴蝶犬,藏獒,马尔济斯犬,秋田犬,喜乐蒂,松狮,金毛,德国牧羊犬,约克夏,雪纳瑞,圣伯纳,比熊,边境牧羊犬,牛头梗,苏牧,泰迪,阿拉斯加,古牧,萨摩耶,茶杯犬,法国贵宾犬,斯开岛梗,中华田园犬,卷毛比熊犬,中国冠毛犬,法国斗牛犬,北京犬,拉萨犬,圣伯纳犬,八哥犬,日本尖嘴犬,西施犬,约克夏梗,比利时格里芬犬,沙皮犬,丝毛梗,西藏獚犬,半点,迷你杜宾犬,美国确架犬,凯安梗,查理王列鹬犬,西部高地白梗,布鲁塞尔犬,西里汉梗,日本狆,澳大利亚牧羊犬,标准贵宾犬,标准腊肠犬,查尔斯王骑士猎犬,柴犬,德国博美犬,高加索牧羊犬,杰克罗素梗,卡迪根威尔士柯基,拉布拉多寻回猎犬,罗威纳犬,玛尔济斯犬,玛丽诺斯比利时牧羊犬,美国恶霸犬,迷你宾莎犬,迷你牛头梗,迷你雪纳瑞,彭布罗克威尔士柯基,美国斯塔福德郡梗犬,玩具贵宾犬,喜乐蒂牧羊犬,意大利凯因克尔索犬,意大利灵缇犬,长须牧羊犬,伯恩山犬,英国斯宾格猎犬,白色瑞士牧羊犬,长毛腊肠犬,其他";
        dogTypeArray = petClassify.split(",");

        //  猫类
        String catClassify = "美国短毛猫,英国短毛猫,异国短毛猫,苏格兰折耳猫,阿比西尼亚猫,暹罗猫,哈瓦那猫,东方短毛猫,雪鞋猫,日本短尾猫,孟加拉豹猫,波斯猫,喜马拉雅猫,布偶猫,美国卷耳猫,东方长毛猫,俄罗斯蓝猫,金吉拉猫,柯尼斯卷毛猫,肯尼亚猫,拉波猫,褴褛猫,美国硬毛猫,缅甸猫,内华达猫,挪威森林猫,塞舌尔猫,土耳其梵猫,威尔士猫,新加坡猫,埃及猫,阿舍拉猫,奥西猫,澳大利亚迷雾猫,巴厘猫,北美洲短毛猫,彼得无毛猫,波米拉猫,伯曼猫,德文卷毛猫,蒂凡尼猫,东奇尼猫,高地折耳猫,加州闪亮猫,柯拉特猫,曼岛无尾猫,曼基康猫,美国短尾猫,孟买猫,缅因库恩猫,欧斯亚史烈斯猫,欧洲短毛猫,欧洲缅甸猫,赛尔凯克卷毛猫,索马里猫,土耳其安哥拉猫,西伯利亚森林猫,夏特尔猫,约克巧克力猫,临清狮猫,异国长毛猫,泰国猫,曼基康矮脚猫,高地猫,斯芬克斯,英国长毛猫,其他";
        catTypeArray = catClassify.split(",");
    }


    private final static String TIMESTAMP_FMT = "yyyyMMddHHmmss";
    private final static String URL = "http://oapi.kaola-bao.com/service";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrderInsure(Order order) throws Exception {
        // 如果没有购买
        if (order == null || order.getAddedInsure() == null || order.getAddedInsure().getInsureAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
            return 0;
        }

        // 获取最新的航班信息
        OrderTransport orderTransport = orderTransportService.getLastByOrderNo(order.getOrderNo());

        SimpleDateFormat fmt = new SimpleDateFormat(TIMESTAMP_FMT);
        Calendar cal = Calendar.getInstance();
        String timestamp = fmt.format(cal.getTime());

        Map<String, String> info = new HashMap<>(27);

        // 流水号
        info.put("sn", order.getOrderNo());
        // 货主姓名
        info.put("name", order.getSenderName());
        // 身份证
        info.put("idCode", ID_CARD_LIST.get(new Double(Math.random() * (ID_CARD_LIST_SIZE - 1)).intValue()));
        // 电话号码
        info.put("phone", order.getSenderPhone());
        // 保险类型编码
        info.put("pcode", "PT-001");
        // 起始地
        info.put("startPlace", order.getTransport().getStartCity());
        // 目的地
        info.put("destination", order.getTransport().getEndCity());
        // 运输类型
        info.put("transportType", getInsureType(order.getTransport().getTransportType()));
        // 运单号
        info.put("transportCode", orderTransport == null ? "" : orderTransport.getExpressNum());
        // 车次/航班
        info.put("vehicleNumber", orderTransport == null ? "" : orderTransport.getTransportNum());
        // 出发时间
        info.put("departureTime", orderTransport == null ? "" : orderTransport.getDateTime() + " 00:00:00");
        // 预计到达时间
        info.put("arriveTime", orderTransport == null ? "" : orderTransport.getDateTime() + " 23:59:59");
        // 宠物种类
        info.put("petCategory", getPetType(order.getPetSort().getPetSortName()));
        // 宠物品种
        info.put("petVariety", getPetClassify(order.getPetGenre().getPetGenreName(), info.get("petCategory")));
        // 宠物性别
        info.put("petSex", "F");
        // 联系电话种类
        info.put("contactType", "1");
        // 宠物年龄
        info.put("petAge", "1岁");
        // 证件类型
        info.put("ctype", "01");

        String bizContent = AesEncodeUtil.aesEncrypt(JsonUtil.toJson(info), key);

        String sign = MD5Util.md5(
                "bizCode=" + mchid
                        + "&bizContent=" + bizContent
                        + "&serviceName=" + "service.order.pet.add"
                        + "&signType=" + "MD"
                        + "&timestamp=" + timestamp
                        + "&version=" + "1.0.1" + key);

        Map<String, Object> param = new HashMap<>(11);

        param.put("bizCode", mchid);
        param.put("bizContent", bizContent);
        param.put("serviceName", "service.order.pet.add");
        param.put("signType", "MD");
        param.put("timestamp", timestamp);
        param.put("version", "1.0.1");
        param.put("sign", sign);

        Response response = httpUtil.post(URL, param);

        return 1;
    }

    @Override
    public int addDaDiOrderInsure(Order order) throws Exception {
        order = consignOrderService.getConsignOrderByOrderNo(order.getOrderNo());
        // 如果没有购买
        if (order == null || order.getAddedInsure() == null || order.getAddedInsure().getInsureAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
            return 0;
        }
        // 获取最新的航班信息
        OrderTransport orderTransport = orderTransportService.getLastByOrderNo(order.getOrderNo());


        InsureRequestDto requestDto = new InsureRequestDto();

        XmlHead xmlHead = new XmlHead();
        xmlHead.setOperateType("input_pay");
        xmlHead.setSysFlag("YGTAZZCWL");
        xmlHead.setTransCode("SV001043");
        xmlHead.setSumAmount("20000");
        xmlHead.setSumPremium("20000");
        xmlHead.setPayPremium("20000");
        xmlHead.setRate("13");
        xmlHead.setModeFlag("1");

        requestDto.setXmlHead(xmlHead);

        List<TbPolicyDto> tbPolicyDtoList = new ArrayList<>();

        TbPolicyDto tbPolicyDto = new TbPolicyDto();

        TbFee tbFee = new TbFee();
        tbFee.setCurrency("CNY");
        tbFee.setCurrency1("CNY");
        tbFee.setExchangeRate1("20000");

        tbPolicyDto.setTbFee(tbFee);

        TbExpense tbExpense = new TbExpense();
        tbExpense.setCommissionRate("20");
        tbExpense.setDisRateCalType("1");

        tbPolicyDto.setTbExpense(tbExpense);

        TbMainCargo tbMainCargo = new TbMainCargo();
        tbMainCargo.setCarrybillno(order.getOrderNo());

        // 传快递单号,或者航班号
        tbMainCargo.setInvoiceNo(order.getOrderNo());

        // 起运开始地
        tbMainCargo.setStartSiteName(order.getTransport().getStartCity()==null?"":order.getTransport().getStartCity());
        // 起运结束地
        tbMainCargo.setEndSiteName(order.getTransport().getEndCity()==null?"":order.getTransport().getEndCity());

        tbMainCargo.setConveyance(orderTransport==null?"":TransportTypeEnum.getTransportTypeEnum(orderTransport.getTransportType()).getName());

        tbMainCargo.setVoyageNo(orderTransport==null?"":orderTransport.getTransportNum());
        tbMainCargo.setLadingNo(orderTransport==null?"":orderTransport.getExpressNum());
        tbMainCargo.setQuantity(StringUtil.$Str(order.getNum()));
        tbMainCargo.setItemDescription("无");
        tbMainCargo.setItemDescription("无");
        tbMainCargo.setViaSiteName("经");
        tbMainCargo.setViasiteCode("0");
        tbMainCargo.setPlusRate("100");
        tbMainCargo.setBlname("VULKAN");
        tbMainCargo.setBlNo("N");
        tbMainCargo.setInvoiceCurrency("CNY");
        tbMainCargo.setMarks("标准包厢");

        tbPolicyDto.setTbMainCargo(tbMainCargo);

        List<TbEngage> tbEngageList = new ArrayList<>();

        TbEngage tbEngage = new TbEngage();
        tbEngage.setSerialNo("1");
        tbEngage.setLineNo("1");
        tbEngage.setRiskCode("0907");
        tbEngage.setClauseCode("T9999");
        tbEngage.setClauses("特别约定");
        tbEngage.setTitleFlag("0");
        tbEngage.setFlag("1");
        tbEngageList.add(tbEngage);

        TbEngage tbEngage1 = new TbEngage();
        tbEngage1.setSerialNo("1");
        tbEngage1.setLineNo("2");
        tbEngage1.setRiskCode("0907");
        tbEngage1.setClauseCode("T9999");
        tbEngage1.setClauses("1、 每次事故绝对免赔率为损失金额的10%");
        tbEngage1.setTitleFlag("1");
        tbEngage1.setFlag("1");
        tbEngageList.add(tbEngage1);

        TbEngage tbEngage2 = new TbEngage();
        tbEngage2.setSerialNo("1");
        tbEngage2.setLineNo("3");
        tbEngage2.setRiskCode("0907");
        tbEngage2.setClauseCode("T9999");
        tbEngage2.setClauses("2、本保单承保保险标的为出生满3个月以上且自身健康无传染病的运输途中宠物 。");
        tbEngage2.setTitleFlag("1");
        tbEngage2.setFlag("1");
        tbEngageList.add(tbEngage2);

        TbEngage tbEngage3 = new TbEngage();
        tbEngage3.setSerialNo("1");
        tbEngage3.setLineNo("4");
        tbEngage3.setRiskCode("0907");
        tbEngage3.setClauseCode("T9999");
        tbEngage3.setClauses("3、兹经保险合同双方同意，本保单保险责任从宠物到达托运处起至宠物到达目的地当地的托运部止，此运输期间各环节累计的临时仓储时间不得超过24小时，否则保险人不承担任何赔偿责任。");
        tbEngage3.setTitleFlag("1");
        tbEngage3.setFlag("1");
        tbEngageList.add(tbEngage3);

        TbEngage tbEngage4 = new TbEngage();
        tbEngage4.setSerialNo("1");
        tbEngage4.setLineNo("5");
        tbEngage4.setRiskCode("0907");
        tbEngage4.setClauseCode("T9999");
        tbEngage4.setClauses("4、本保单承保运输工具为飞机、火车。");
        tbEngage4.setTitleFlag("1");
        tbEngage4.setFlag("1");
        tbEngageList.add(tbEngage4);

        tbPolicyDto.setTbEngageList(tbEngageList);

        List<TbRisk> tbRiskList = new ArrayList<>();
        TbRisk tbRisk = new TbRisk();
        tbRisk.setSerialNo("1");
        tbRisk.setKindCode("015");
        tbRisk.setRiskCode("0907");
        tbRisk.setKindName("宠物运输保险条款（2018版）");
        tbRisk.setItemCode("1306");
        tbRisk.setItemName("宠物");
        tbRisk.setAmount("20000.00");
        tbRisk.setBenchMarkRate("6");
        tbRisk.setShortTermRate("0.006");
        tbRisk.setUnitAmount("20000.00");
        tbRisk.setPremium(TypeConvertUtil.$Str(order.getAddedInsure().getInsureAmount()));
        tbRisk.setAmtFlag("Y");
        tbRisk.setMainRiskFlag("Y");
        tbRisk.setMult("1");
        tbRisk.setInsuredNumber(TypeConvertUtil.$Str(order.getNum()));
        tbRisk.setFamilyNo("1");
        tbRisk.setAddressNo("1");
        tbRiskList.add(tbRisk);

        tbPolicyDto.setTbRiskList(tbRiskList);


        String leaveDate = DateUtil.format(DateUtil.getAddHourDate(new Date(), 1));

        TbInsuranceSchema tbInsuranceSchema = new TbInsuranceSchema();
        //tbInsuranceSchema.setProposalNo("2020075f17a9b4f3b242");
        //tbInsuranceSchema.setPrintNo("YGWYHD202007300000");
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid:"+uuid);
        tbInsuranceSchema.setImportSn(uuid); // 单据编号
        tbInsuranceSchema.setInputDate(order.getOrderDate());
        tbInsuranceSchema.setInsurDate(order.getLeaveDate());
        tbInsuranceSchema.setInsuredPhone(order.getSenderPhone());
        tbInsuranceSchema.setInsurStartDate(leaveDate+" 00:00:01");
        tbInsuranceSchema.setInsurEndDate(leaveDate+" 23:59:59");
        tbInsuranceSchema.setImmeValidFlag("1");
        tbInsuranceSchema.setInsuredName(order.getReceiverName());
        tbInsuranceSchema.setImmeValidStartDate(leaveDate+" 00:00:01");
        tbInsuranceSchema.setImmeValidEndDate(leaveDate+" 23:59:59");

        tbInsuranceSchema.setMainRiskCode("0907");
        tbInsuranceSchema.setAmount("20000.00");
        tbInsuranceSchema.setPremium(TypeConvertUtil.$Str(order.getAddedInsure().getInsureAmount()));
        tbInsuranceSchema.setRate("13");
        tbInsuranceSchema.setSysFlag("YGTAZZCWL");
        tbInsuranceSchema.setHolderName("淘宠惠");
        tbInsuranceSchema.setHolderIdNo("9131011507811258X0");
        tbInsuranceSchema.setHolderMobile(order.getSenderPhone());
        tbInsuranceSchema.setHolderemail("101835518@qq.com");
        tbInsuranceSchema.setHolderidType("08");

        tbInsuranceSchema.setInsuredIdNo("362326198706250918");
        tbInsuranceSchema.setInsuredIdType("01");
        tbInsuranceSchema.setInsuredNumber("1");
        tbInsuranceSchema.setInsuredAddress("北京通州区");
        tbInsuranceSchema.setInsuredmobile("15946861857");
        tbInsuranceSchema.setCertifyCode("TA001");
        tbInsuranceSchema.setCustNumber("1");
        tbInsuranceSchema.setMult("1");
        tbInsuranceSchema.setPolFlag("0");

        tbPolicyDto.setTbInsuranceSchema(tbInsuranceSchema);


        tbPolicyDtoList.add(tbPolicyDto);
        requestDto.setTbPolicyDtoList(tbPolicyDtoList);

        java.net.URL url = null;
        url = new URL("http://testservicebus.sinosig.com/WebContent/ServicebusService.do");

        //获取连接
        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
        httpURLConnection.setUseCaches(false);// 设置不能设置缓存
        httpURLConnection.setDoInput(true);// 打开读写属性，默认为false
        httpURLConnection.setDoOutput(true);// 打开读写属性，默认为false
        httpURLConnection.setRequestMethod("POST");// 设置提交方式，默认为GET
        httpURLConnection.setAllowUserInteraction(true);
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setReadTimeout(60000);

        Map<String, Object> map = new HashMap<>();

        String serverName = Md5UtilForJt.authcodeEncode(ConstantNames.serverName,ConstantNames.signKey,"GBK");
        String requestMethod = Md5UtilForJt.authcodeEncode(ConstantNames.requestMethod, ConstantNames.signKey,"GBK");
        String sign = Md5UtilForJt.authcodeEncode(ConstantNames.sign+ConstantNames.signKey, ConstantNames.signKey,"GBK");
        String json = JsonUtil.toJson(requestDto);

        String xml =new String(json.getBytes("utf-8"));
        System.out.println("发送报文为：---------------10:20:47--------utf8>"+xml);
        System.out.println("发送报文为：---------------10:20:47--------utf8>"+xml);

        String unicode = new String(xml.getBytes(),"UTF-8");
        System.out.println("发送报文为：--------------------------unicode---->"+unicode);
        String gbk= new String(unicode.getBytes("GBK"),"GBK");
        System.out.println("发送报文为：--------------------------gbk>"+gbk);
        String sstrXML = Md5UtilForJt.authcodeEncode(gbk, ConstantNames.dataKey,"GBK");
        System.out.println("加密后的报文："+sstrXML);

        byte[] dedata = sstrXML.getBytes();
        dedata= GZipUtils.compress(dedata);

        map.put("serverName", serverName);
        map.put("requestMethod", requestMethod);
        map.put("sign", sign);
        map.put("json", dedata);


        OutputStream outputStream =httpURLConnection.getOutputStream();
        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
//				PrintWriter out = new PrintWriter(connection.getOutputStream());

        objOutputStream.writeObject(map);
        objOutputStream.flush();
        objOutputStream.close();
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedInputStream input = null; // 输入流,用于接收请求的数据
        byte[] buffer = new byte[2048]; // 数据缓冲区
        int count = 0; // 每个缓冲区的实际数据长度
        ByteArrayOutputStream streamXML = new ByteArrayOutputStream(); // 请求数据存放对象
        try {
            input = new BufferedInputStream(inputStream);
            while ((count = input.read(buffer)) != -1) {
                streamXML.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }
        byte[] iXMLData = streamXML.toByteArray(); // 得到一个byte数组,提供给平台
        iXMLData = GZipUtils.decompress(iXMLData);
        String returnStr = new String(iXMLData,"UTF-8");
        String returnStr1 = Md5UtilForJt.authcodeDecode(returnStr, ConstantNames.dataKey,"UTF-8");

        InsureResponseDto insureResponseDto = JsonUtil.formObject(returnStr1, InsureResponseDto.class);


        String policyNo = insureResponseDto.getTbresultList().get(0).getPolicyNo();
        String premium = insureResponseDto.getTbresultList().get(0).getPremium();
        httpURLConnection.disconnect();

        addInsure2(policyNo, uuid, premium);



        return 1;
    }

    private int addInsure2(String policyNo, String importSn, String payPremium) throws Exception {
        Map<String, Object> map = new HashMap<>();

        String serverName = Md5UtilForJt.authcodeEncode(ConstantNames.serverName,ConstantNames.signKey,"GBK");
        String requestMethod = Md5UtilForJt.authcodeEncode(ConstantNames.requestMethod2, ConstantNames.signKey,"GBK");
        String sign = Md5UtilForJt.authcodeEncode(ConstantNames.sign+ConstantNames.signKey, ConstantNames.signKey,"GBK");
        String json = "{\n" +
                "    \"proposalNo\":\""+policyNo+"\",\n" +
                "    \"payId\":\""+importSn+"\",\n" +
                "    \"sysflag\":\"YGTAZZCWL\",\n" +
                "    \"transCode\":\"SV001043\",\n" +
                "    \"payType\":\"3\",\n" +
                "    \"paySource\":\"BE\",\n" +
                "    \"payPremium\":\""+payPremium+"\"\n" +
                "}";

        String xml =new String(json.getBytes("utf-8"));
        System.out.println("发送报文为：---------------10:20:47--------utf8>"+xml);
        System.out.println("发送报文为：---------------10:20:47--------utf8>"+xml);

        String unicode = new String(xml.getBytes(),"UTF-8");
        System.out.println("发送报文为：--------------------------unicode---->"+unicode);
        String gbk= new String(unicode.getBytes("GBK"),"GBK");
        System.out.println("发送报文为：--------------------------gbk>"+gbk);
        String sstrXML = Md5UtilForJt.authcodeEncode(gbk, ConstantNames.dataKey,"GBK");
        System.out.println("加密后的报文："+sstrXML);

        byte[] dedata = sstrXML.getBytes();
        dedata= GZipUtils.compress(dedata);

        map.put("serverName", serverName);
        map.put("requestMethod", requestMethod);
        map.put("sign", sign);
        map.put("json", dedata);


        java.net.URL url = null;
        url = new URL("http://testservicebus.sinosig.com/WebContent/ServicebusService.do");

        //获取连接
        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
        httpURLConnection.setUseCaches(false);// 设置不能设置缓存
        httpURLConnection.setDoInput(true);// 打开读写属性，默认为false
        httpURLConnection.setDoOutput(true);// 打开读写属性，默认为false
        httpURLConnection.setRequestMethod("POST");// 设置提交方式，默认为GET
        httpURLConnection.setAllowUserInteraction(true);
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setReadTimeout(60000);

        OutputStream outputStream =httpURLConnection.getOutputStream();
        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);

        objOutputStream.writeObject(map);
        objOutputStream.flush();
        objOutputStream.close();
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedInputStream input = null; // 输入流,用于接收请求的数据
        byte[] buffer = new byte[2048]; // 数据缓冲区
        int count = 0; // 每个缓冲区的实际数据长度
        ByteArrayOutputStream streamXML = new ByteArrayOutputStream(); // 请求数据存放对象
        try {
            input = new BufferedInputStream(inputStream);
            while ((count = input.read(buffer)) != -1) {
                streamXML.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }
        byte[] iXMLData = streamXML.toByteArray(); // 得到一个byte数组,提供给平台
        iXMLData = GZipUtils.decompress(iXMLData);
        String returnStr = new String(iXMLData,"UTF-8");
        String returnStr1 = Md5UtilForJt.authcodeDecode(returnStr, ConstantNames.dataKey,"UTF-8");

        System.out.println(returnStr1);
        httpURLConnection.disconnect();


        return 1;
    }

    @Override
    public int updateInsureCode(String orderNo, String insureCode, short status) {
        if (status == 1) {
            consignOrderService.updateInsureCode(orderNo, insureCode);
            return 1;
        } else if (status == 2) {

        }
        return 0;
    }

    @Override
    public int insureRebate(Order order) {
        if (order == null
                || order.getAddedInsure() == null
                || order.getAddedInsure().getInsureAmount() == null
                || order.getAddedInsure().getInsureAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        // TODO 将数据单独存到另一张表中
        return 0;
    }

    /**
     * <p>
     * 获取订单类型
     * </p>
     *
     * @param transportType 订单类型
     * @return String
     * @author LiuXiangLin
     * @date 14:55 2019/12/25
     **/
    private String getInsureType(String transportType) {
        /* 1汽运，2飞机，3火车*/
        if (String.valueOf(TransportTypeEnum.AIRCRAFT.getType()).equals(transportType)
                || String.valueOf(TransportTypeEnum.RANDOM.getType()).equals(transportType)) {
            return "2";
        }
        if (String.valueOf(TransportTypeEnum.BUS.getType()).equals(transportType)
                || String.valueOf(TransportTypeEnum.SPECIAL_TRAIN.getType()).equals(transportType)) {
            return "1";
        }
        if (String.valueOf(TransportTypeEnum.RAILWAY.getType()).equals(transportType)) {
            return "3";
        }
        return "";
    }


    /**
     * <p>
     * 获取宠物类型
     * </p>
     *
     * @param petType 宠物类型
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 14:06 2019/12/27
     **/
    private String getPetType(String petType) {
        if (StringUtils.isBlank(petType)) {
            return PET_TYPE_CAT;
        }
        if (petType.contains(PET_TYPE_CAT.substring(0, 1))) {
            return PET_TYPE_CAT;
        }
        if (petType.contains(PET_TYPE_DOG.substring(0, 1))) {
            return PET_TYPE_DOG;
        }
        return PET_TYPE_DOG;
    }

    /**
     * <p>
     * 获取宠物类型
     * </p>
     *
     * @param petClassify 宠物类型
     * @param petType     宠物种类
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 14:47 2019/12/27
     **/
    private String getPetClassify(String petClassify, String petType) {
        // 宠物类型为空 则返回其他
        if (StringUtils.isBlank(petClassify)) {
            return OTHER;
        }

        // 猫类
        if (PET_TYPE_CAT.equals(petType)) {
            for (String tempPetClassify : catTypeArray) {
                if (tempPetClassify.contains(petClassify)) {
                    return tempPetClassify;
                }
            }
        }

        // 狗类
        if (PET_TYPE_DOG.equals(petType)) {
            for (String tempPetClassify : dogTypeArray) {
                if (tempPetClassify.contains(petClassify)) {
                    return tempPetClassify;
                }
            }
        }

        // 都未匹配
        return OTHER;
    }

}
