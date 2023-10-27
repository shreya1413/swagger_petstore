package swagger.io.swaggerpetstoreservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import swagger.io.swaggerpetstoreservice.common.response.CommonResponse;
import swagger.io.swaggerpetstoreservice.common.response.ResponseCodes;
import swagger.io.swaggerpetstoreservice.common.response.UserNotFoundException;
import swagger.io.swaggerpetstoreservice.model.Category;
import swagger.io.swaggerpetstoreservice.model.Pet;

@Component
public class PetDaoService {
	private static List<Pet> pets = new ArrayList<>();
	private static List<Category> categories = new ArrayList<>();

	static {
		categories.add(new Category(1, "Dog"));
		categories.add(new Category(2, "Cat"));
		categories.add(new Category(3, "Rabbit"));
		categories.add(new Category(4, "Bird"));

		pets.add(
				new Pet(1, categories.get(1), "Skiee", new ArrayList<String>(Arrays.asList("url1", "url2")), Pet.SOLD));
		pets.add(new Pet(2, categories.get(0), "Shadow", new ArrayList<String>(Arrays.asList("url1", "url2")),
				Pet.AVAILABLE));
		pets.add(new Pet(3, categories.get(3), "Yeontan", new ArrayList<String>(Arrays.asList("url1", "url2")),
				Pet.PENDING));
		pets.add(new Pet(4, categories.get(2), "Bam", new ArrayList<String>(Arrays.asList("url1", "url2")), Pet.SOLD));
	}

	

	public Pet getPetDetailsById(final int petId) throws Exception {
		Pet existingPet = getPetById(petId);
		if (existingPet == null) {
			throw new UserNotFoundException("Pet not found");
		}
		return existingPet;
	}

	public CommonResponse addPet(final Pet pet) throws Exception {
		Pet existingPet = getPetById(pet.getId());
		if(existingPet != null) {
			return ResponseCodes.getErrorResponse(ResponseCodes.ALREADY_EXIST, "Pet ID already exist");
		}
		pets.add(pet);
		return ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");
	}

	public CommonResponse updatePet(final Pet pet, final int id) throws Exception {
		Pet existingPet = getPetById(id);
		if (existingPet == null) {
			throw new UserNotFoundException("Pet not found");		}
		if(getPetById(pet.getId()) == null){
			deletePetById(existingPet.getId());
		}
		else {
			return ResponseCodes.getErrorResponse(ResponseCodes.ALREADY_EXIST, "Pet ID already exist");
		}
		return addPet(pet);
	}
	
	public CommonResponse deletePet(final int petId) {
		Pet existingPet = getPetById(petId);
		if (existingPet == null) {
			throw new UserNotFoundException("Pet not found");		}
		deletePetById(petId);
		return ResponseCodes.getSuccessResponse(ResponseCodes.SUCCESS, "Success");
	}

	public void deletePetById(final Integer petId) {
		Iterator<Pet> iterator = pets.iterator();
		while (iterator.hasNext()) {
			Pet pet = iterator.next();
			if (pet.getId() == petId) {
				iterator.remove();
				return;
			}
		}
	}
	
	public Pet getPetById(final int petId) {
		for (Pet pet : pets) {
			if (pet.getId() == petId) {
				return pet;
			}
		}
		return null;
	}
	
	public List<Pet> getAllPet(){
		return pets;
	}
}
