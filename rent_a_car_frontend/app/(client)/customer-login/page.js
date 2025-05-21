"use client"
import { postRequest } from '@/service/carservice'
import React, { useContext, useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useCarContext } from '../context/page';

export default function LoginPage() {
  const {setIsLoggedIn} = useCarContext();
  const [state,setState]=useState({
    email:"",
    password:""
  })
  const [registerData,setRegisterData]=useState({
    email: "",
    password: "",
    customer: {
        firstName: "",
        lastName: "",
        phoneNumber: "",
        registrationDate: "",
        dateOfBirth: "",
        licenseType: "",
        tckn:"",
        licenseIssueDate: "",
        account: {
            balance: "",
            accountNo: "",
            currencyType: "",
            iban:""
        },
        address: {
            street: "",
            city: "",
            district: ""
        }
    }
  })
  
 
  function handleChange(e){
const copy={...state}
copy[e.target.name]=e.target.value
setState(copy)
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target;
  
    const keys = name.split('.'); 
    setRegisterData((prevState) => {
      let updatedState = { ...prevState };
  
      
      keys.reduce((acc, key, index) => {
        if (index === keys.length - 1) {
          acc[key] = value; 
        } else {
          acc[key] = { ...acc[key] }; 
        }
        return acc[key];
      }, updatedState);
  
      return updatedState;
    });
  };
  
  
  const router = useRouter();
  async function handleSubmit(e) {
      e.preventDefault();
      const res=await fetch("http://localhost:8080/login",{
     method:"POST",
     body:JSON.stringify(state),
     headers:{
      "Content-Type":"application/json"
     }
      });
    
      if(res.ok){
        const json= await res.json()
        //console.log(json.payload.accessToken)
        localStorage.setItem("rent_token", JSON.stringify(json.payload));
        setIsLoggedIn(true)
        router.push("/"); 
      } else {
        alert("Login failed");
      }
    }
    async function handleRegister(e) {
      e.preventDefault();
      const res=await fetch("http://localhost:8080/register",{
     method: "POST", 
     body: JSON.stringify(registerData),
     headers:{
      "Content-Type":"application/json",
     }
     
      });
    
      if(res.ok){
        const json= await res.json()
        localStorage.setItem("rent_token", JSON.stringify(json.payload));
        setIsLoggedIn(true)
        router.push("/"); 
      } else {
        const error = await res.json(); 
        alert(`Başarısız: ${error.exception?.message}`);
        
      }
    }
  

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
      <div className="w-full max-w-4xl bg-white p-6 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center mb-6">Üye Girişi</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        
          <div className="p-4 border-r border-gray-200">
            <h2 className="text-xl font-semibold mb-4">Rent A Car Üyesiyim</h2>
            <form className="space-y-4">
              <input
                type="text"
                name='email'
                value={state.email}
                onChange={handleChange}
                placeholder="Email Adresinizi Giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
              <input
                type="password"
                name='password'
                value={state.password}
                onChange={handleChange}
                placeholder="Şifre"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
              <button
                type="submit"
                className="w-full bg-green-500 text-white py-3 rounded-lg hover:bg-green-600 transition"
                onClick={handleSubmit}
              >
                Giriş
              </button>
            </form>
            
          </div>

      <div className="p-4">
            <h2 className="text-xl font-semibold mb-4">
              Üye Değilim | Yeni Üye
            </h2>
            <form className="space-y-4">
              <input
                type="email"
                name="email" required
                value={registerData.email}
                onChange={handleInputChange} 
                placeholder="Email giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
               <input
                type="password"
                name="password" required 
                value={registerData.password}
                onChange={handleInputChange} 
                placeholder="Şifre  giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />

                 <input
                type="text"
                name="customer.firstName" required
                value={registerData.customer.firstName}
                onChange={handleInputChange} 
                placeholder="Adınızı  giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
              <input
                type="text"
                name="customer.lastName" required
                value={registerData.customer.lastName}
                onChange={handleInputChange} 
                placeholder="Soyadınızı  giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
               <input
                type="text"
                name="customer.phoneNumber" required 
                value={registerData.customer.phoneNumber}
                onChange={handleInputChange} 
                placeholder="Telefonunuzu  giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
              <label style={{position: "relative",top: "15px"}}>Doğum Tarihi</label>
               <input
                type="date"
                name="customer.dateOfBirth" required
                value={registerData.customer.dateOfBirth}
                onChange={handleInputChange} 
                placeholder="Doğum Tarihinizi  giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
                 <input
                type="text"
                name="customer.tckn" required 
                value={registerData.customer.tckn}
                onChange={handleInputChange} 
                placeholder="Tc kimlik no giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
                <input
                type="text"
                name="customer.licenseType" required
                value={registerData.customer.licenseType}
                onChange={handleInputChange} 
                placeholder="Ehliyet türünüzü giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
              <label style={{position: "relative",top: "15px"}}>Kayıt Tarihi</label>
                <input
                type="date"
                name="customer.registrationDate" required
                value={registerData.customer.registrationDate}
                onChange={handleInputChange} 
                placeholder="Kayıt Tarihini giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
              <label style={{position: "relative",top: "15px"}}>Ehliyet verilme tarihi</label>
                <input
                type="date"
                name="customer.licenseIssueDate" required
                value={registerData.customer.licenseIssueDate}
                onChange={handleInputChange} 
                placeholder="Ehliyet verilme tarihini giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
                <input
                type="number"
                step="0.01"
                name="customer.account.balance" required
                value={registerData.customer.account.balance}
                onChange={handleInputChange} 
                placeholder="Bakiye miktarını giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
               <input
                type="text"
                name="customer.account.accountNo" required
                value={registerData.customer.account.accountNo}
                onChange={handleInputChange} 
                placeholder="Hesap numarası giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
              <input
                type="text"
                name="customer.account.currencyType" required
                value={registerData.customer.account.currencyType}
                onChange={handleInputChange} 
                placeholder="Para birimini giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              /> 
              <input
                type="text"
                name="customer.account.iban" required
                value={registerData.customer.account.iban}
                onChange={handleInputChange} 
                placeholder="iban giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
                <input
                type="text"
                name="customer.address.city" required
                value={registerData.customer.address.city}
                onChange={handleInputChange} 
                placeholder="Şehir giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
                  <input
                type="text"
                name="customer.address.district" required
                value={registerData.customer.address.district}
                onChange={handleInputChange} 
                placeholder="İlçe giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
               <input
                type="text"
                name="customer.address.street" required
                value={registerData.customer.address.street}
                onChange={handleInputChange} 
                placeholder="cadde bilginizi giriniz"
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:ring-green-400"
              />
              
               
              <button
                type="submit"
                onClick={handleRegister}
                className="w-full bg-green-500 text-white py-3 rounded-lg hover:bg-green-600 transition"
              >
               Kayıt Ol
              </button>
            </form>
          </div> 
        </div>
      </div>
    </div>
  );
}
