"use client"
import { getRequest } from "@/service/carservice";
import React, { useEffect, useState } from "react";
import Rezervasyon from "./rezervasyon/page";
import HesapInfo from "./hesapInfo/page";
import Account from "./account/page";
import MyAddress from "./myAddress/page";
import { useRouter } from "next/navigation";
import { useCarContext } from "../context/page";


  
  const Profilim = () => {
    
    const [activeTab, setActiveTab] = useState("Rezervasyonlarım");
    const { setIsLoggedIn} = useCarContext();
    const [carlist,setCarList]=useState([]);

     const router = useRouter();

    useEffect(() => {
    
        const fetchCars = async () => {
          const customerId=JSON.parse(localStorage.getItem("rent_token")).customerId;
          const allcars = await getRequest(`/api/customer/rented-cars/list/${customerId}`);
          console.log(allcars);
          setCarList(allcars || []);
      
        };
        fetchCars();
      }, []);
  
   
    const renderContent = () => {
      switch (activeTab) {
        case "Rezervasyonlarım":
          return <Rezervasyon carlist={carlist}/>;
        case "Bakiye Bilgisi":
          return <Account carlist={carlist}/>;
        case "Profil Bilgilerim":
          return <HesapInfo carlist={carlist}/>;
        case "Adreslerim":
          return <MyAddress carlist={carlist}/>;
       /*  case "Şifre Değiştirme":
          return <ChangePassword />; */
        default:
          return <div>Lütfen bir seçenek seçin.</div>;
      }
    };
    
    const removeCustomer=(e)=>{
      e.preventDefault();
      setIsLoggedIn(false)
      localStorage.removeItem("rent_token");
      router.push("/"); 

    }
    // Stil objesi
    const styles = {
      container: { display: "flex", flexDirection: "column", padding: "20px" },
      title: { fontSize: "24px", fontWeight: "bold", marginBottom: "20px" },
      content: { display: "flex" },
      sidebar: { width: "200px", marginRight: "20px" },
      menu: { listStyle: "none", padding: "0" },
      menuItem: {
        padding: "10px",
        cursor: "pointer",
        backgroundColor: "#f0f0f0",
        marginBottom: "10px",
        borderRadius: "5px",
      },
      activeMenuItem: {
        backgroundColor: "#ddd",
        fontWeight: "bold",
      },
      mainContent: { flex: 1, padding: "10px", border: "1px solid #ccc" },
    };
  
    return (
      <div style={styles.container}>
        <h1 style={styles.title}>Profilim</h1>
        <div style={styles.content}>
         
          <div style={styles.sidebar}>
            <ul style={styles.menu}>
              {["Rezervasyonlarım", "Bakiye Bilgisi", "Profil Bilgilerim", "Adreslerim"].map(
                (item) => (
                  <li
                    key={item}
                    style={{
                      ...styles.menuItem,
                      ...(activeTab === item ? styles.activeMenuItem : {}),
                    }}
                    onClick={() => setActiveTab(item)}
                  >
                    {item}
                  </li>
                )
              )}
            </ul>
            <button style={{background:"#FF571D" ,color:"#fff", width:"100%",padding: "10px",borderRadius: "5px",}}
            onClick={removeCustomer}
            >Çıkış Yap</button>
          </div>
  
        
          <div style={styles.mainContent}>{renderContent()}</div>
        </div>
      </div>
    );
  };
  
  export default Profilim;
  