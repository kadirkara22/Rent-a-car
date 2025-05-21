import React, { useEffect, useState } from 'react'
import OfficeList from './officeList/page';
import CreateOffice from './createOffice/page';
import UpdateOffice from './updateOffice/page';

const Office = () => {

    const [view, setView] = useState("default");
    const [car, setCar] = useState({});
 const [formData, setFormData] = useState(null);
  const [pickupLocations, setPickupLocations] = useState([]);
  const [pickupLocation, setPickupLocation] = useState("");


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
       })
       .catch((error) => {
         console.error("API Hatası:", error);
       });
   }, []);
 
 

  const handleNewClick = () => {
    setView("create"); 
  };

  const handleUpdateClick = (location) => {
    setFormData(location)
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
      Yeni Ofis Ekle
    </div>
}
    
    {view === "create" && (
      <CreateOffice
        setView={setView} pickupLocation={pickupLocation}
      />
    )}
    {view === "update" && (
      <UpdateOffice
        setView={setView}  setFormData={setFormData} formData={formData} 
      />
    )}
    {view === "default" && (
      <>

        {pickupLocations && (
          <OfficeList
            data={pickupLocations}
            onUpdateClick={handleUpdateClick} setView={setView}
          />
        )}
      </>
    )}
  </>
  )
}

export default Office