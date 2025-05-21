"use client"

import React, {  useState } from 'react'
import { useRouter } from 'next/navigation'
import { useAdminContext } from '@/app/(admin)/context/page';



export default function AdminLogin() {
  const {setIsLoggedIn} = useAdminContext();
  const [state,setState]=useState({
    email:"",
    password:""
  })
 
  function handleChange(e){
const copy={...state}
copy[e.target.name]=e.target.value
setState(copy)
  }


  const router = useRouter();
  async function handleSubmit(e) {
      e.preventDefault();
      const res=await fetch("http://localhost:8080/login-admin",{
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
        router.push("/dashboard"); 
      } else {
        alert("Admin Yetkiniz bulunmamaktadır");
      }
    }
    
  

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
      <div className="w-full max-w-4xl bg-white p-6 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center mb-6">Admin Girişi</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6" style={{display:"flex",justifyContent:"center"}}>
        
          <div className="p-4">
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

     
        </div>
      </div>
    </div>
  );
}
