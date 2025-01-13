package cn.px.zly.sf;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public class SfApiUrl {
    private SfApiUrl(){}

    public static String OAUTH2 = "/oauth2/accessToken";

    /**
     * 功能描述
     * 客户可通过此接口查询顺丰运单路由，顺丰会在响应Json报文返回当时点要求的全部路由节点信息。
     *
     * 此路由查询接口支持三类查询方式:
     *
     * 1)根据顺丰运单号查询：查询请求中提供接入编码与运单号，验证接入编码与所有请求运单号的归属关系，系统只返回具有正确归属关系的运单路由信息。
     *
     * 2)根据客户订单号查询：查询请求中提供接入编码与订单号，验证接入编码与所有请求订单号的归属关系，对于归属关系正确的订单号，找到对应的运单号，然后返回订单对应运单号的路由信息。适用于通过下单的客户订单。
     *
     * 3)根据运单号+运单对应的收寄人任一方电话号码后4位(参数checkPhoneNo中传入)查询,系统校验信息匹配将返回对应运单路由信息。一次性查多个路由，checkPhoneNo传入多个电话号码后4位，用英文逗号隔开，作为一个字符串
     */
    public static String SEARCH_ROUTES = "/std/service";



}
