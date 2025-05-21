import { getRequest } from "@/service/carservice";
import React, { useEffect, useState } from "react";

const MyAddress = () => {
  const [formData, setFormData] = useState(
    {
      address: {
        district: "",
        city:"",
        street:"",
      },
    }
  );

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      address: {
        ...prevState.address,
        [name]: value,
      },
    }));
  };
  

    useEffect(() => {
        
            const fetchCars = async () => {
              const customerId=JSON.parse(localStorage.getItem("rent_token")).customerId;
              const customer = await getRequest(`api/customer/hesabim/bakiye/${customerId}`);
              console.log(customer);
              setFormData(customer || []);
             
            };
            fetchCars();
          }, []);

          
  const handleUpdateAddress  = async (e) => {
    e.preventDefault();
    const rent_token=JSON.parse(localStorage.getItem("rent_token"))
    const requestData = {
      "street": formData.address.street,
       "city": formData.address.city,
       "district": formData.address.district,
    };
    const res=await fetch(`http://localhost:8080/api/customer/hesabim/${formData.id}/update-address`,{
   method: "PATCH", 
   body: JSON.stringify(requestData),
   headers: {
    "Content-Type": "application/json",
    "Authorization":`Bearer ${rent_token.accessToken}`
  }
    });
  
    if(res.ok){
      const json= await res.json()
      setFormData(json)
      alert(`Güncelleme Başarılı`);
    } else {
        console.log(res)
      const error = await res.json(); 
      alert(`Başarısız: ${error.exception?.message}`);
      
    }
  }


  return (
    <div style={{ maxWidth: "500px", margin: "0 auto", padding: "20px" }}>
      <h2 style={{ textAlign: "center", marginBottom: "20px" }}>Adres Bilgileri</h2>
     
      <div style={{ marginBottom: "10px" }}>
        <label style={{ display: "block", marginBottom: "5px" }}>Şehir*</label>
        <select
          name="city"
          value={formData?.address?.city || ""}
          onChange={handleInputChange}
          style={{
            width: "100%",
            padding: "8px",
            borderRadius: "4px",
            border: "1px solid #ccc",
          }}
        >
          <option value="istanbul">İstanbul</option>
          <option value="ankara">Ankara</option>
          <option value="izmir">İzmir</option>
          <option value="bursa">Bursa</option>
          <option value="antalya">Antalya</option>
        </select>
      </div>

         <div style={{ marginBottom: "10px" }}>
        <label style={{ display: "block", marginBottom: "5px" }}>İlçe*</label>
        <input
          type="text"
          name="district"
          value={formData?.address?.district || ""}
          onChange={handleInputChange}
          style={{
            width: "100%",
            padding: "8px",
            borderRadius: "4px",
            border: "1px solid #ccc",
          }}
        />
      </div>
     

     
      <div style={{ marginBottom: "10px" }}>
        <label style={{ display: "block", marginBottom: "5px" }}>Sokak*</label>
        <input
          type="text"
          name="street"
          value={formData?.address?.street || ""}
          onChange={handleInputChange}
          style={{
            width: "100%",
            padding: "8px",
            borderRadius: "4px",
            border: "1px solid #ccc",
          }}
        />
      </div>

      <button
        onClick={handleUpdateAddress}
        style={{
          width: "100%",
          padding: "10px",
          backgroundColor: "#4CAF50",
          color: "white",
          border: "none",
          borderRadius: "4px",
          cursor: "pointer",
        }}
      >
        Bilgileri Güncelle
      </button>
    </div>
  );
};

export default MyAddress;
