"use client"
import { getRequest } from "@/service/carservice";
import React, { useEffect, useState } from "react";
import { useCarContext } from "../../context/page";
import Link from "next/link";
import { useRouter } from "next/navigation";

const Rent = ({params}) => {
const { carData,car, setCar } = useCarContext();
    
     
    
      const resolvedParams = React.use(params);
      const router = useRouter();
      useEffect(() => {
        
          const fetchCarDetails = async () => {
            const car = await getRequest(`/api/customer/cars/details/${resolvedParams.id}`);
            console.log(car);
            setCar(car);
           
          };
          fetchCarDetails();
        
      }, [resolvedParams,carData]);

      async function handleSubmit(e) {
        e.preventDefault();
        const rent_token=JSON.parse(localStorage.getItem("rent_token"))
        const requestData = {
          pickupLocation: {
            id: carData.pickupLocation, 
          },
          customerId: rent_token.customerId, 
          carId: car.id, 
          startDate: `${carData.pickupDate}T${carData.pickupTime}:00`, 
          endDate: `${carData.returnDate}T${carData.returnTime}:00`, 
        };
        const res=await fetch("http://localhost:8080/api/customer/rented-cars/rent",{
       method: "POST", 
       body: JSON.stringify(requestData),
       headers:{
        "Content-Type":"application/json",
        "Authorization":`Bearer ${rent_token.accessToken}`
       }
       
        });
      
        if(res.ok){
          const json= await res.json()
          //console.log(json.payload.accessToken)
          router.push("/succes"); 
        } else {
          const error = await res.json(); 
          alert(`Başarısız: ${error.exception?.message}`);
          
        }
      }
    
    
      if (!car) {
        return <p>Loading...</p>;
      }
  return (
    <div style={{ maxWidth: "1200px", margin: "0 auto", padding: "20px", fontFamily: "Arial, sans-serif" }}>

  <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", borderBottom: "1px solid #ccc", paddingBottom: "10px" }}>
 
    
        <div style={{ flex: 1 }}>
          <div style={{ marginBottom: "5px" }}>
            <strong>Alış</strong>: {carData?.pickupLocationDetails.locationName} {carData?.pickupLocationDetails.city}
          </div>
          <div>
            <strong>Alış Tarih</strong>:  {carData?.pickupDate} Saat: {carData?.pickupTime} 
          </div>
        </div>
        <div style={{ flex: 1, textAlign: "center" }}>
          <span style={{ fontSize: "20px" }}>➡️</span>
        </div>
        <div style={{ flex: 1, textAlign: "right" }}>
          <div style={{ marginBottom: "5px" }}>
            <strong>Dönüş</strong>:  {carData?.pickupLocationDetails.locationName} {carData.pickupLocationDetails.city} 
          </div>
          <div>
            <strong>Dönüş Tarih</strong>:  {carData?.returnDate} Saat: {carData?.returnTime}
          </div>
        </div>
        <div style={{ marginLeft: "20px", textAlign: "center" }}>
          <div>
            <strong>  {(() => {
  const pickupDate = new Date(carData?.pickupDate); 
  const returnDate = new Date(carData?.returnDate); 

  if (!isNaN(pickupDate) && !isNaN(returnDate)) {
    
    const diffInMs = returnDate - pickupDate;

    
    const diffInDays = Math.ceil(diffInMs / (1000 * 60 * 60 * 24)); 

    return `${diffInDays} Gün`;
  }

  return "Tarih bilgisi eksik";
})()}
</strong>
          </div>
          <Link href={"/"} style={{ marginTop: "10px", padding: "5px 10px", backgroundColor: "#ccc", border: "none", cursor: "pointer" }}>
            Değiştir
          </Link>
        </div>
      </div>

      
      <div style={{ display: "flex", alignItems: "center", marginTop: "20px" }}>
       
        <img
          src={car.image} 
          alt="Renault Clio"
          style={{ width: "200px", height: "auto", marginRight: "20px" }}
        />

       
        <div style={{ flex: 1 }}>
          <h2 style={{ fontSize: "18px", marginBottom: "10px" }}>
            {car.brand}{car.model}
          </h2>
          <div style={{display:"flex", flexDirection:"column",alignItems:"flex-end"}}>
          <div style={{ fontSize: "18px", color: "green", fontWeight: "bold" }}>
            Araç Fiyatı: {car.onlinePrice}
          </div>
          <div onClick={handleSubmit}
               
               className="w-32 bg-green-500 text-white py-3 rounded-lg hover:bg-green-600 transition" style={{cursor:"pointer",textAlign:"center"}}
              
             >
              Onayla ve Kirala
             </div>
             </div>
        </div>
      </div>
    </div>
  );
};

export default Rent;
