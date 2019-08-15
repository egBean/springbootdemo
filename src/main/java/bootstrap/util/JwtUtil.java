package bootstrap.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * laowang
 */
public class JwtUtil {
    private static final String SALT = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    private static final String ISSUER = "ams";

    private static final String JSONMSG = "jsonMsg";

    //加密，传入一个对象和有效期
    public static  String sign(Object obj,long maxAge){

        String jsonString = JSON.toJSONString(obj);

        Algorithm algorithm = Algorithm.HMAC256(SALT);//选择算法，加盐
        JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER)//选择发行用户
                .withExpiresAt(new Date(System.currentTimeMillis()+maxAge))//设置过期时间
                .withClaim(JSONMSG,jsonString);

        return builder.sign(algorithm);
    }

    /**
     * 验证jwt，并返回数据
     */
    public static <T> T verifyToken(String token,Class<T> clazz) {

        T result;
        Algorithm algorithm;
        Map<String, Claim> map;
        try{
            algorithm = Algorithm.HMAC256(SALT);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            String jsonMsg = jwt.getClaim(JSONMSG).asString();
            result = JSON.parseObject(jsonMsg,clazz);
        }catch (Exception e){
            e.printStackTrace();
            result = null;
        }

        return result;
    }
    
}
