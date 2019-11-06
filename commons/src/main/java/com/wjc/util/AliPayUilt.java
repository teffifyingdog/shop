package com.wjc.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AliPayUilt {

    private static AlipayClient alipayClient=null;

    static {
        alipayClient= new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                //商户id
                "2016101300677318",
                //自己的私钥
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC00NJaYo11e4Dk\n" +
                        "Hne+9jLQzDLCv4NhmaH3G7EChmvEH9udQRmyPYqevL8fltyYvfJgA7IvUoyXXosV\n" +
                        "uPWKb0AkBpRGmqMO4Fd//v+cKjMqgI0ZHQi3bNFeECjHcifIOYwscqKzwHSmgBo9\n" +
                        "i9PI9RkGn0QKbqRjPk4PEweJr+QBP5xD445ShOT30emi92TCIqn9CC9GYGo1/EjJ\n" +
                        "8IXn3PSa7QW5DT64O2jcDcOXKeuKHT/qZuT3Qxl5qeyO90QAAPD4/O6M+3W6o/TR\n" +
                        "lXkwyJRW1JFw4AzadF6VVE7ha91k5Y1CBAM8Wr2kltNFDMbIaDqFA0wTnKRMnZS9\n" +
                        "tO45if9ZAgMBAAECggEBAILgEyGw68r5VdXH58ykzysqYiNsE2B1nzIJLRoyyZXv\n" +
                        "qTotgLfuq4MyAvSOcaVs9x5gDn0KNxDpMarLcY597+Mjj/d6vkNqRmsy2zjhop9E\n" +
                        "5Nwkk8aQUQrQj3OtQPZAESGq5/FLgCNz3YpmxyhRn4eXh9w1MW9UDZQDphUW5uWv\n" +
                        "N+PzNPWkf9hgO6id57ufR0LExu/izS4UVYUkoAhVx23OIDvn7k9zicMdN9/q36R+\n" +
                        "3Ie/2YJwuwgn0OjtqbSC+5D8SSYMIrxqFnrjkhfvcO1wYjMBfkpFB13vJ6zOzFxp\n" +
                        "tJd5ViIMGDXAAAhrX2rwXMGxuSvm5KWFDQbfPoo+CXECgYEA72lks2ollvP2wXXx\n" +
                        "Juk8w8+AdbThR5e+a0GncqIQRTN/VsJhtEw0LJGooCO1jR+yrZfNVrlRG0BcQYB4\n" +
                        "My4wxevUqPYAmEPFWPSZpayqnpR3NOth/lXpFsn9pJfYa4c5ffM1GzHjga0hwuv+\n" +
                        "kd49BveRE7iUyxKK93kAcwz5ML8CgYEAwVgSZa+RrkRFbfC+vl4ub/pj8i/6ld3D\n" +
                        "k8iESDLhLChMuxAZQV/+jQ4FlBBrldU3FMI5W3UAZ7x426UYRp/K23thmGgK/IDh\n" +
                        "qJghukiX6/m/83uFWJsFELtDo2xGQ9jCvgbIna1T10vtDmccoK+gROs/8z80Zq8h\n" +
                        "NwhpPi7OvecCgYAn1Fnbe2JLjcTUhKGc/pqMXP4bFv+PoJAeWerYqFual6msrmQA\n" +
                        "0diwPX9XPPmcQ9S/Co0vy17R8NAoWY4lvZ6Bnu1fOqX4rvWrCe5x4Jr2DO8UD8Da\n" +
                        "xkIj93DKLDTmFrYa3RZNsuwNi9cdDZPpgpaWjSNGq6OdcTVLXIcVnOfsEwKBgAVu\n" +
                        "ZO3oVa5QiqbA+Yq9eI56uS4HfQGSvji7jEO+8iZcZ3Nly1/Tw4HQ4AU9xNfrqmVx\n" +
                        "40yXZ9CCMd7xr1HkkT1DJQWz/oMDV9N1mpTyDxp45JSBZsiLakQDDq5bj0m+oBHY\n" +
                        "fPA4+z70Cg+6g8pbxKPK+/voTZ+I0eUK3nH5vKMHAoGATzc9mq5j7KC2pJo0DFt/\n" +
                        "YWXAxMKtiEjOIbAwmfyn6lPcCEPcs/xPjVjF1Fn+lZyfndCUYAEYy9Pk42HCHuSX\n" +
                        "DS8bgjSFzOaUFPhOUPN954qe4Uy/fvqcnw5inoDTZIANgqdlu6ricHFq2Ifp8J0i\n" +
                        "KhqRxG6ao0VkOS6rqIHWypE=",
                "json", //返回的格式
                "utf-8",//编码集
                //支付宝公钥
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArTjjMHUaUWby9XGCcpyRFXJ9g6IiVL6Vh2yLCXv5MMiJnu1LgAUxmEax5W7c+ntShRtMlwotOIsAemRnuNUaxTbF8cRwnoe75va5bC71przQSCp+/42oOVzEtdyj/xcdkE5blTVrUbg9zdyBfvDhD16sc/sc5o9qw6c4N/Ga2eGUWEkCp39sLqXZtQg+NkQ1RXnTy8tirFoAQil0nxI1x0/ZGOGt9PjzKQ7am5fojR7HFdAq9cBNA80y23YYs7KYZ12A/NXObKUstoi5ksqa7qxPpoBvc7u0qLzJrj9vPTKA86+3i2PicoGtDPCjnJ/9Cny4vBChzuqp8tyFQnnWtwIDAQAB",
                "RSA2");
    }

    public static AlipayClient getAliPayClient(){
        return alipayClient;
    }
}
