"use client"
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useAdminContext} from "../context/page";
import { getRequest } from "@/service/carservice";
import Rezervasyon from "./rezervasyon/page";
import Cars from "./Cars/page";
import Office from "./office/page";

  
  const Dashboard = () => {
   
    const [activeTab, setActiveTab] = useState("Rezervasyonlar");
    const { setIsLoggedIn} = useAdminContext();
    const [carlist,setCarList]=useState([]);
    const [rentList,setRentList]=useState([]);
     const router = useRouter();

    useEffect(() => {
    
        const fetchCars = async () => {
          const customerId=JSON.parse(localStorage.getItem("rent_token")).customerId;
          const allcars = await getRequest(`/api/customer/rented-cars/list/${customerId}`);
          //console.log(allcars);
          setCarList(allcars || []);
      
        };
        fetchCars();
      }, []);

  


useEffect(() => {
    const fetchRents = async () => {
      const allRents = await getRequest("api/customer/rented-cars/list");
     // console.log(allRents);
      setRentList(allRents || []);

    };
    fetchRents();
  }, []);
  
 
    const renderContent = () => {
      switch (activeTab) {
        case "Rezervasyonlar":
          return <Rezervasyon rentList={rentList}/>;
        case "Arabalar":
          return <Cars />;
        case "Ofisler":
          return <Office />;
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
      router.push("/admin-login"); 

    }
   
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
        <h1 style={styles.title}>Dashboard</h1>
        <div style={styles.content}>
        
          <div style={styles.sidebar}>
            <ul style={styles.menu}>
              {["Rezervasyonlar", "Arabalar", "Ofisler"].map(
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
  
  export default Dashboard;
  