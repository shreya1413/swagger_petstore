package swagger.io.swaggerpetstoreservice;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import swagger.io.swaggerpetstoreservice.common.response.BadRequestException;
import swagger.io.swaggerpetstoreservice.common.response.CommonResponse;
import swagger.io.swaggerpetstoreservice.common.response.ResponseCodes;
import swagger.io.swaggerpetstoreservice.common.response.UserNotFoundException;
import swagger.io.swaggerpetstoreservice.model.Category;
import swagger.io.swaggerpetstoreservice.model.Pet;
import swagger.io.swaggerpetstoreservice.service.PetDaoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerPetstoreServiceApplicationTests {
	@MockBean
    private PetDaoService petService;

	@Autowired
    private MockMvc mockMvc;
    

    @Test
    public void testAddPet() throws Exception {
        Pet pet = new Pet(10, new Category(1, "Dog"), "Tyson", 
        		new ArrayList<String>(Arrays.asList("https://i.pinimg.com/564x/45/a1/cd/45a1cdd8ba822cf58b8a75946502ded7.jpg")), 
        		Pet.AVAILABLE);
        CommonResponse response = ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");

        when(petService.addPet(pet)).thenReturn(response);

        mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pet)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(response)));
    }

    @Test
    public void testAddPetWithNullBody() throws Exception {

        when(petService.addPet(null)).thenThrow(new BadRequestException("No information provided"));

        mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("No information provided"))
        		.andExpect(content().string(""));
    }

    @Test
    public void testUpdatePet() throws Exception {
        int id = 4;
        Pet pet = new Pet(id, new Category(2, "Cat"), "Coco", new ArrayList<>(Arrays.asList("ulr1", "url2")), Pet.AVAILABLE);
        CommonResponse response = ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");

        when(petService.updatePet(pet, id)).thenReturn(response);

        mockMvc.perform(put("/pet/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pet)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(response)));
    }

    @Test
    public void testUpdatePetWithNullBody() throws Exception {
        int id = 1;

        when(petService.updatePet(null, id)).thenThrow(new BadRequestException("No information provided"));

        mockMvc.perform(put("/pet/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("No information provided"))
                .andExpect(content().string(""));
    }

    @Test
    public void testGetPetById() throws Exception {
        int id = 1;
        Pet pet = new Pet(id, new Category(1, "Dog"), "Molly", new ArrayList<>(Arrays.asList("ulr1", "url2")), Pet.SOLD);

        when(petService.getPetDetailsById(id)).thenReturn(pet);

        mockMvc.perform(get("/pet/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(pet)));
    }

    @Test
    public void testGetPetByIdNotFound() throws Exception {
        int id = 75;

        when(petService.getPetDetailsById(id)).thenThrow(new UserNotFoundException("Pet not found"));

        mockMvc.perform(get("/pet/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Pet not found"))
                .andExpect(content().string(""));
    }

    @Test
    public void testDeletePet() throws Exception {
        int id = 1;
        CommonResponse response = ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");

        when(petService.deletePet(id)).thenReturn(response);

        mockMvc.perform(delete("/pet/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(response)));
    }

    @Test
    public void testDeletePetNotFound() throws Exception {
        int id = 75;

        when(petService.deletePet(id)).thenThrow(new UserNotFoundException("Pet not found"));

        mockMvc.perform(delete("/pet/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Pet not found"))
                .andExpect(content().string(""));
    }

    @Test
    public void testGetAllPet() throws Exception {
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet(12, new Category(4, "Bird"), "Blue", new ArrayList<>(Arrays.asList("ulr1", "url2")), Pet.PENDING));
        pets.add(new Pet(11, new Category(3, "Rabbit"), "Oliver", new ArrayList<>(Arrays.asList("ulr1")), Pet.AVAILABLE));

        when(petService.getAllPet()).thenReturn(pets);

        mockMvc.perform(get("/all-pet"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(pets)));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
