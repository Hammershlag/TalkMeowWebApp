package uni.projects.talkmeow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uni.projects.talkmeow.components.message.Message;
import uni.projects.talkmeow.components.user.User;
import uni.projects.talkmeow.repositories.MessageRepository;
import uni.projects.talkmeow.repositories.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uni.projects.talkmeow.Utility.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    @DisplayName("User can access home page")
    void adminPanelIsAccessible() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(get("/home").session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("User cannot access admin panel")
    void adminPanelIsNotAccessible() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(get("/admin/home").session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("User cannot access manager panel")
    void managerPanelIsNotAccessible() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(get("/manager/home").session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("User can log out of the session")
    void userCanLogOut() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(get("/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login/form"));
    }

    @Test
    @DisplayName("User can access all conversations page")
    void userCanAccessAllConversations() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(get("/api/messages/conversation/all").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("All Conversations")))
                .andExpect(model().attributeExists("userMessages"));
    }

    @Test
    @DisplayName("User can view a specific conversation")
    void userCanViewSpecificConversation() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        List<User> list = userRepository.findAll();
        User user = list.get(0).getUsername().equals(username) ? list.get(1) : list.get(0);

        this.mockMvc.perform(get("/api/messages/conversation")
                        .param("username", user.getUsername())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Conversation with")))
                .andExpect(content().string(containsString(user.getUsername())))
                .andExpect(model().attributeExists("messages"))
                .andExpect(model().attributeExists("otherUser"));
    }

    @Test
    @DisplayName("User can send a message")
    void userCanSendMessage() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        List<User> list = userRepository.findAll();
        User user = list.get(0).getUsername().equals(username) ? list.get(1) : list.get(0);

        MvcResult result = this.mockMvc.perform(post("/api/messages/send")
                        .param("id", user.getId().toString())
                        .param("messageContent", "Hi! :)")
                        .session(session))
                .andExpect(status().isOk())
                .andReturn();

        Message message = messageRepository.findFirstBySenderAndReceiverAndMessageContentOrderByTimestampDesc(userRepository.findByUsername(username).orElse(null), user, "Hi! :)");
        messageRepository.delete(message);
    }

    @Test
    @DisplayName("User can view their own profile")
    void userCanViewProfile() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(get("/profile").session(session))
                .andExpect(status().isOk())
                .andExpect(xpath("//title").string("TalkMeow - Profile"))
                .andExpect(xpath("//img[@alt='Avatar Image']").exists())
                .andExpect(xpath("//*[@id=\"avatar-change-button\"]").exists())
                .andExpect(xpath("//*[@id=\"settings-container\"]/div[2]/form/p[1]/button").exists())
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("User can change their avatar")
    void userCanChangeAvatar() throws Exception {
        MockHttpSession session = login(username, password, mockMvc);
        this.mockMvc.perform(post("/profile/avatar-change")
                        .param("selectedAvatarId", "1")
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

}