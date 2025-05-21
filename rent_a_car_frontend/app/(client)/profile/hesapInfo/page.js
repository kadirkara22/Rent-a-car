import { getRequest } from "@/service/carservice";
import React, { useEffect, useState } from "react";

const HesapInfo = () => {
  const [formData, setFormData] = useState({});

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };
console.log(formData)
  useEffect(() => {
      
          const fetchCars = async () => {
            const customerId=JSON.parse(localStorage.getItem("rent_token")).customerId;
            const customer = await getRequest(`api/customer/hesabim/bakiye/${customerId}`);
            console.log(customer);
            setFormData(customer || []);
           
          };
          fetchCars();
        }, []);

  const handleUpdateBalance  = async (e) => {
    e.preventDefault();
    const rent_token=JSON.parse(localStorage.getItem("rent_token"))
    const requestData = {
      "firstName": formData.firstName,
      "lastName": formData.lastName,
      "phoneNumber": formData.phoneNumber,
      "registrationDate": formData.registrationDate,
      "dateOfBirth": formData.dateOfBirth,
      "licenseType": formData.licenseType,
      "tckn":formData.tckn,
      "licenseIssueDate": formData.licenseIssueDate,
      "email":formData.email,
      "account":formData.account,
      "address":formData.address,
    };
    const res=await fetch(`http://localhost:8080/api/customer/hesabim/${formData.id}/update`,{
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
      <div
        style={{
          maxWidth: "600px",
          margin: "20px auto",
          padding: "20px",
          border: "1px solid #ccc",
          borderRadius: "8px",
          fontFamily: "Arial, sans-serif",
          backgroundColor: "#f9f9f9",
        }}
      >
        <h2 style={{ textAlign: "center", marginBottom: "20px", color: "#333" }}>
          Üyelik Bilgileri
        </h2>
        <form>
      
          <div style={{ marginBottom: "10px" }}>
            <label style={{ display: "block", marginBottom: "5px" }}>Adınız*</label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName ? formData.firstName : ""}
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
            <label style={{ display: "block", marginBottom: "5px" }}>Soyadınız*</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName ? formData.lastName :""}
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
            <label style={{ display: "block", marginBottom: "5px" }}>
              Doğum Tarihiniz*
            </label>
            <input
              type="date"
              name="dateOfBirth"
              value={formData.dateOfBirth ? formData.dateOfBirth.split("T")[0] : ""}
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
            <label style={{ display: "block", marginBottom: "5px" }}>
              T.C. Kimlik No*
            </label>
            <input
              type="text"
              name="tckn"
              value={formData.tckn ? formData.tckn : ""}
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
            <label style={{ display: "block", marginBottom: "5px" }}>Email*</label>
            <input
              type="email"
              name="email"
              value={formData.email ? formData.email : ""}
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
            <label style={{ display: "block", marginBottom: "5px" }}>
              Telefon Numaranız*
            </label>
            <input
              type="text"
              name="phoneNumber"
              value={formData.phoneNumber ? formData.phoneNumber :""}
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
            <label style={{ display: "block", marginBottom: "5px" }}>
              Ehliyet Veriliş Tarihi*
            </label>
            <input
              type="date"
              name="licenseIssueDate"
              value={formData.licenseIssueDate ? formData.licenseIssueDate.split("T")[0] :""}
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
  <label style={{ display: "block", marginBottom: "5px" }}>Ehliyet Türü*</label>
  <select
    name="licenseType"
    value={formData.licenseType || ""}
    onChange={handleInputChange}
    style={{
      width: "100%",
      padding: "8px",
      borderRadius: "4px",
      border: "1px solid #ccc",
    }}
  >
    <option value="" disabled>
      Ehliyet Türü Seçin
    </option>
    <option value="A">A</option>
    <option value="A1">A1</option>
    <option value="A2">A2</option>
    <option value="B">B</option>
    <option value="C">C</option>
    <option value="D">D</option>
    <option value="E">E</option>
    <option value="F">F</option>
    <option value="G">G</option>
  </select>
</div>
  
          <button
            type="submit"
            onClick={handleUpdateBalance}
            style={{
              display: "block",
              width: "100%",
              padding: "10px",
              borderRadius: "4px",
              border: "none",
              backgroundColor: "#4CAF50",
              color: "#fff",
              fontWeight: "bold",
              cursor: "pointer",
            }}
          >
            Bilgileri Güncelle
          </button>
        </form>
      </div>
    );
  };
  
  export default HesapInfo;
  