"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

import FilteredCars from "../component/filteredcars/page";
import SearchCars from "../component/searchCars/page";

export default function Home() {
  const [formData, setFormData] = useState(null);
  const [pickupLocations, setPickupLocations] = useState([]); 
  const [pickupLocation, setPickupLocation] = useState("");
  const [pickupLocationDetails, setPickupLocationDetails] = useState({
    locationName: "",
    city: "",
  });
  const [pickupDate, setPickupDate] = useState("");
  const [pickupTime, setPickupTime] = useState("");
  const [returnDate, setReturnDate] = useState("");
  const [returnTime, setReturnTime] = useState("");
  const [isDifferentReturnLocation, setIsDifferentReturnLocation] = useState(false);

  const router = useRouter();
 


  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      pickupLocation,
      pickupLocationDetails,
      pickupDate,
      pickupTime,
      returnDate,
      returnTime,
      isDifferentReturnLocation: false,
    };

    setFormData(data);
   
  };

  return (
    <>
   <SearchCars handleSubmit={handleSubmit} setPickupLocations={setPickupLocations} setPickupLocation={setPickupLocation} setPickupDate={setPickupDate} setPickupTime={setPickupTime} setReturnDate={setReturnDate}setReturnTime={setReturnTime} pickupDate={pickupDate} pickupTime={pickupTime} returnDate={returnDate} returnTime={returnTime} pickupLocations={pickupLocations} pickupLocation={pickupLocation} pickupLocationDetails={pickupLocationDetails}  setPickupLocationDetails={setPickupLocationDetails}/>
    {formData && <FilteredCars data={formData} />}
    </>
  );
}


