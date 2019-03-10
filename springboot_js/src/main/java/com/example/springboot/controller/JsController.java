package com.example.springboot.controller;

import com.example.springboot.bean.User;
import com.example.springboot.http.AesUtil;
import com.example.springboot.http.HttpClientUtil;
import com.example.springboot.http.WXAppletUserInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
public class JsController {

    @ResponseBody
    @RequestMapping("/login/login")
    public Map index(HttpServletRequest request,String encryptedData,String code,String iv,String openId){
        String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc86dd5f69b77ff50&secret=fa977b976c36f3e02d01cf5f8b3b8f86&js_code=" + code + "&grant_type=authorization_code";
        String unionId = null;
        Map<String,Object> map = new HashMap<>();
        //通过httpClient 请求微信接口获取登陆加密的密钥 session_key openid unionid等信息
        JSONObject jsonObject = HttpClientUtil.httpsRequest(URL, "GET", null);
        //session_key为微信服务器返回的针对用户信息加密签名的密钥 不能返回给客户端
        if (null != jsonObject && jsonObject.containsKey("session_key") && iv != null && encryptedData != null){
            openId = jsonObject.getString("openid");
            String session_key = jsonObject.getString("session_key");
            if(jsonObject.containsKey("unionid")){
                unionId = jsonObject.getString("unionid");
            }
            //unionId 只有公众号关联小程序才可以拿到 不然微信是不会返回的
            if(StringUtils.isBlank(unionId)){
                try {
                    //根据微信服务器传过来的签名密钥 以及客户端传来的iv encryptedData 获取用户信息
                    JSONObject userInfoJSON = new WXAppletUserInfo().getUserInfo(encryptedData, session_key, iv);
                    if (null != userInfoJSON ) {
                        map.put("status", 1);
                        map.put("msg", "解密成功");
                        Map userInfo = new HashMap();
                        userInfo.put("openId", userInfoJSON.get("openId"));
                        userInfo.put("nickName", userInfoJSON.get("nickName"));
                        userInfo.put("gender", userInfoJSON.get("gender"));
                        userInfo.put("city", userInfoJSON.get("city"));
                        userInfo.put("province", userInfoJSON.get("province"));
                        userInfo.put("country", userInfoJSON.get("country"));
                        userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                        userInfo.put("unionId", userInfoJSON.get("unionId"));
                        map.put("userInfo", userInfo);
                        //TODO 可以根据redis 生成一个随机数的token 用于作为登陆标识 返回给客户端 客户端拿到token每次请求携带过来进行判断即可
                        return map;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            openId = jsonObject.getString("openid");
            map.put("openId",openId);
        }
        System.out.println("如果jsp页面路径写的正确 却访问不到jsp页面 需要配置configuration中的working directory 选择相应的目录即可");
        return map;
    }

    public Map userMap = new HashMap();

    @RequestMapping("/user/addUser")
    @ResponseBody
    public String user(String openid,String nickName,String avatarUrl,String province,String city){
        userMap.put(openid,nickName + "_" + avatarUrl + "_" + province +"_" + city);
        System.out.println("添加成功");
        return "success";
    }

    @RequestMapping("/user/getUser")
    @ResponseBody
    public List user(String openid){
        List dataMap = new ArrayList();
        String o = (String) userMap.get(openid);
        if(o != null) {
            String[] s = o.split("_");
            for(String stmp :s){
                dataMap.add(stmp);
            }
        }
        System.out.println("获取成功");
        return  dataMap;
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map index(){
        Map<String,Object> map = new HashMap<>();
        map.put("status", 1);
        List list = new ArrayList();
        for(int i = 0;i < 10; i++){
            Map map1 = new HashMap();
            map1.put("activityPrice","activityPrice" + i);
            map1.put("skuPrice","skuPrice" + i);
            map1.put("prodName","prodName"+ i);
            list.add(map1);
        }
        map.put("data",list);
        return map;
    }


    /**
     * 参数自动装箱 只要对应上实体类中的字段 springmvc就会自动将传递的值复制给实体类
     * @param user
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    public User userMethod(@RequestBody(required = false) User user){
        System.out.println(user);
        User user1 = new User();
        user1.setUserId(user.getUserId());
        user1.setUserName(user.getUserName());
        return user1;
    }


    /**
     * PathVariable 使用正则对参数进行校验
     * @param regexp1
     * @return
     */
    @RequestMapping(value="/javabeat/{regexp1:[a-z-]+}",method = RequestMethod.GET)
    public String getRegExp(@PathVariable("regexp1") String regexp1){
        System.out.println("URI Part 1 : " + regexp1);
        return "hello";
    }

    /**
     * @ModelAttribute
     *使用注解 可以将公用的参数都通这个方法传递进来  易于解耦
     * 在方法定义上使用 @ModelAttribute 注解：Spring MVC 在调用目标处理方法前，
     * 会先逐个调用在方法级上标注了@ModelAttribute 的方法。
     * 在方法的入参前使用 @ModelAttribute 注解：可以从隐含对象中获取隐含的模型数据中获取对象，
     * 再将请求参数–绑定到对象中，再传入入参将方法入参对象添加到模型中。
     */
    @ModelAttribute
    public void startMethod(){
        System.out.println("开始执行");
    }

    @RequestMapping("/execption")
    public void exceptionTest(){
        int i = 10/0;
    }

    /**
     * 配置异常注解后 如果项目出现异常会进入指定的方法
     */
    @ExceptionHandler(RuntimeException.class)
    public void aaaa(){
        System.out.println("异常进入方法");
    }

}
