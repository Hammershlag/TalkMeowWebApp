package uni.projects.talkmeow.controllers.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uni.projects.talkmeow.components.avatar.*;
import uni.projects.talkmeow.services.AvatarService;

import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/avatars")
    public String filterAvatars(@RequestParam(value = "furColor", required = false) Color furColor,
                                @RequestParam(value = "eyeColor", required = false) Color eyeColor,
                                @RequestParam(value = "pattern", required = false) Pattern pattern,
                                @RequestParam(value = "breed", required = false) Breed breed,
                                @RequestParam(value = "age", required = false) Age age,
                                Model model) {
        List<Avatar> filteredAvatars = avatarService.getFilteredAvatars(age, breed, furColor, eyeColor, pattern);
        model.addAttribute("avatars", filteredAvatars);
        return "fragments/avatarList :: avatarList";
    }

}
