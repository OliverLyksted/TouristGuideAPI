package org.example.touristguideapi;

import org.example.touristguideapi.controller.TouristController;
import org.example.touristguideapi.model.TouristAttraction;
import org.example.touristguideapi.service.TouristService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TouristController.class)
class TouristControllerTest {
    private TouristAttraction touristAttraction = new TouristAttraction();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    @Test
    void showAll() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"));
    }

    @Test
    void showTags() throws Exception {
        mockMvc.perform(get("/attractions/Zoo/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(content().string(containsString("Tags for Zoo")));
    }

    @Test
    void createTouristAttraction() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cities", Matchers.empty()))
                .andExpect(model().attribute("tags", Matchers.empty()))
                .andExpect(model().attribute("touristAttraction", Matchers.any(TouristAttraction.class)))
                .andExpect(view().name("addAttraction"));
    }


    @Test
    void saveTouristAttraction() throws Exception {
        mockMvc.perform(post("/attractions/save").sessionAttr("touristAttraction", this.touristAttraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
    }

}


