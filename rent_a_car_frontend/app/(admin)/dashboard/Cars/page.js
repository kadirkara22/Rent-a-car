import React, { useEffect, useState } from 'react'
import Search from './search/page';
import FilteredCars from './filteredcars/page';
import UpdateCar from './updateCar/page';
import CreateCar from './createCar/page';

const Cars = () => {
    const [view, setView] = useState("default");
    const [car, setCar] = useState({});
 const [formData, setFormData] = useState(null);
  const [pickupLocations, setPickupLocations] = useState([]); 
  const [pickupLocation, setPickupLocation] = useState("");
  const [pickupLocationDetails, setPickupLocationDetails] = useState({
    locationName: "",
    city: "",
  });
console.log(pickupLocations)
 useEffect(() => {
     fetch("http://localhost:8080/api/customer/pickup-locations/list")
       .then((response) => {
         if (!response.ok) {
           throw new Error("Alış yerleri alınamadı.");
         }
         return response.json();
       })
       .then((data) => {
         setPickupLocations(data); 
         if (data.length > 0) {
           setPickupLocation(data[0].id); 
           setPickupLocationDetails({
             locationName: data[0].locationName,
             city: data[0].city,
           });
         }
       })
       .catch((error) => {
         console.error("API Hatası:", error);
       });
   }, []);
 
   const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      pickupLocation,
      pickupLocationDetails,
    };

    setFormData(data);
   
  };

  const handleNewClick = () => {
    setView("create"); 
  };

  const handleUpdateClick = (car) => {
    setCar(car)
    setView("update"); 
  };


  return (
    <>
{
view !="create" && view !="update" &&
<div
      style={{
        display: "block",
        background: "green",
        color: "#fff",
        textAlign: "center",
        padding: "10px",
        cursor: "pointer", 
      }}
      onClick={handleNewClick}
    >
      Yeni Araba Ekle
    </div>
}
    
    {view === "create" && (
      <CreateCar
        setView={setView} pickupLocation={pickupLocation}
      />
    )}
    {view === "update" && (
      <UpdateCar
        setView={setView}  car={car} setCar={setCar}
      />
    )}
    {view === "default" && (
      <>
        <Search
          formData={formData}
          pickupLocations={pickupLocations}
          pickupLocation={pickupLocation}
          pickupLocationDetails={pickupLocationDetails}
          setFormData={setFormData}
          setPickupLocations={setPickupLocations}
          setPickupLocation={setPickupLocation}
          setPickupLocationDetails={setPickupLocationDetails}
          handleSubmit={handleSubmit}
        />
        {formData && (
          <FilteredCars
            data={formData}
            onUpdateClick={handleUpdateClick}  setView={setView}
          />
        )}
      </>
    )}
  </>
  )
}

export default Cars
