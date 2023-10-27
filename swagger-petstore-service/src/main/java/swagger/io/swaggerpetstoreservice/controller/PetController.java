package swagger.io.swaggerpetstoreservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swagger.io.swaggerpetstoreservice.common.response.BadRequestException;
import swagger.io.swaggerpetstoreservice.common.response.CommonResponse;
import swagger.io.swaggerpetstoreservice.common.response.ResponseCodes;
import swagger.io.swaggerpetstoreservice.model.Pet;
import swagger.io.swaggerpetstoreservice.service.PetDaoService;

@RestController
@RequestMapping(value = "/petstore.swagger.io/v2")
public class PetController {
	@Autowired
	private PetDaoService petService;
	
	@PostMapping(path = "/pet")
	public CommonResponse addPet(@RequestBody Pet pet) throws Exception {
		if(pet == null) {
			throw new BadRequestException("No information provided");
		}
		return petService.addPet(pet);
		
	}
	
	@PutMapping(path = "/pet/{id}")
	public CommonResponse updatePet(@PathVariable(required = true) int id, @RequestBody Pet pet) throws Exception {
		if(pet == null) {
			throw new BadRequestException("No information provided");
		}
		return petService.updatePet(pet, id);
	}
	
	@GetMapping(path = "/pet/{id}")
	public Pet getPetById(@PathVariable int id)throws Exception {
		return petService.getPetDetailsById(id);
	}
	
	@DeleteMapping(path = "/pet/{id}")
	public CommonResponse deletePet(@PathVariable int id)throws Exception {
		return petService.deletePet(id);
	}
	
	@GetMapping(path = "/all-pet")
	public List<Pet> getAllPet(){
		return petService.getAllPet();
	}
}
