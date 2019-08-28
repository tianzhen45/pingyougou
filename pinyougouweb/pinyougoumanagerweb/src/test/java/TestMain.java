import com.alibaba.dubbo.config.annotation.Reference;
import com.tianzhen.service.BrandService;
import com.tianzhen.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dubbo.xml")
public class TestMain {
    @Reference
    private UserService userService;

    @Reference
    private BrandService brandService;

    @Test
    public void test(){
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toString());
    }

    @Test
    public void testUserService(){
        System.out.println(userService.findAll());
    }

    @Test
    public void testBrandService(){
        System.out.println(brandService.findAll());
    }
}
