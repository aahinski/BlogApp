import com.ahinski.blog.BlogApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoadsTest() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sign up")));
    }

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/articles"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @Sql(value = {"/before-creating-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("bobby").password("fisher"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @Sql(value = {"/before-creating-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void invalidPasswordTest() throws Exception {
        this.mockMvc.perform(formLogin().user("bobby").password("visconsinuniversity"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

}
