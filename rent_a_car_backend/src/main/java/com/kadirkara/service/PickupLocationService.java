package com.kadirkara.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kadirkara.dto.DtoCar;
import com.kadirkara.dto.DtoPickupLocation;
import com.kadirkara.dto.DtoPickupLocationIU;
import com.kadirkara.model.Car;
import com.kadirkara.model.PickupLocation;
import com.kadirkara.repository.PickupLocationCarRepository;
import com.kadirkara.repository.PickupLocationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PickupLocationService {

	  private final PickupLocationRepository pickupLocationRepository;
	    private final PickupLocationCarRepository pickupLocationCarRepository;

	    public PickupLocationService(PickupLocationRepository pickupLocationRepository, PickupLocationCarRepository pickupLocationCarRepository) {
	        this.pickupLocationRepository = pickupLocationRepository;
	        this.pickupLocationCarRepository = pickupLocationCarRepository;
	    }
	    
	    
	    
	    public DtoPickupLocation createPickupLocation(DtoPickupLocationIU dtoPickupLocationIU) {
	    	
	    	PickupLocation pickupLocation=new PickupLocation();
	    	DtoPickupLocation dtoPickupLocation=new DtoPickupLocation();
	    	
	    	pickupLocation.setCreateTime(new Date());
	    	
	    	
	    	BeanUtils.copyProperties(dtoPickupLocationIU, pickupLocation);
	    	
	    	PickupLocation savedPickupLocation = pickupLocationRepository.save(pickupLocation);
	    	
	    	BeanUtils.copyProperties(savedPickupLocation, dtoPickupLocation);
	    	
	    	
	    	
	    	
	        return dtoPickupLocation;
	    }
	    
	    
	    
	    public DtoPickupLocation updatePickupLocation(Long id, DtoPickupLocationIU dtoPickupLocationIU) {
	   DtoPickupLocation dtoPickupLocation=new DtoPickupLocation();
	    	Optional<PickupLocation> optional = pickupLocationRepository.findById(id);
			   
	    	 if (optional.isEmpty()) {
	             throw new EntityNotFoundException("Location bulunamadı: " + id);
	         }
	    	 
	    	 PickupLocation dbPickupLocation = optional.get();
	    	 
	    	 BeanUtils.copyProperties(dtoPickupLocationIU, dbPickupLocation);
	    	 PickupLocation savedPickupLocation = pickupLocationRepository.save(dbPickupLocation);
	    	 
	    	 BeanUtils.copyProperties(savedPickupLocation, dtoPickupLocation);
	    	 
	    	 
	        return dtoPickupLocation;
	    }
	    
	   
	    
	    public DtoPickupLocation getPickupLocationById(Long id) {
	    	Optional<PickupLocation> optional = pickupLocationRepository.findById(id);
	    	if(optional.isEmpty()) {
	    		throw new EntityNotFoundException("Location bulunamadı: " + id);
			}
	    	
            PickupLocation dbPickupLocation = optional.get();
	    	DtoPickupLocation dtoPickupLocation = new DtoPickupLocation();
	    	
	        BeanUtils.copyProperties(dbPickupLocation, dtoPickupLocation);
	        return dtoPickupLocation;
	    }
	    
	   
	
	    public List<DtoPickupLocation> getAllPickupLocations() {
	    	List<DtoPickupLocation> dtoList=new ArrayList<>();
	    	List<PickupLocation> allPickupLocations = pickupLocationRepository.findAll();
	    	
	    	for(PickupLocation pickupLocation:allPickupLocations) {
	    		DtoPickupLocation dto=new DtoPickupLocation();
				BeanUtils.copyProperties(pickupLocation, dto);
				dtoList.add(dto);
			}
	    	
	        return dtoList;
	    }

	  
	   
	    public void deletePickupLocation(Long id) {
	        Optional<PickupLocation> optional = pickupLocationRepository.findById(id);
	        if(optional.isPresent()) {
	        	 pickupLocationRepository.delete(optional.get());
	     	   
	        }
	       
	    }
	    

}
