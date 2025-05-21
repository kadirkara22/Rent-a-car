import { getRequest } from '@/service/carservice';
import React, { useEffect, useState } from 'react'

const Rezervasyon = ({carlist}) => {

 

      const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString("tr-TR", {
          year: "numeric",
          month: "long",
          day: "numeric",
          hour: "2-digit",
          minute: "2-digit",
        });
      };
    
      const getStatus = (startDate, endDate) => {
        const now = new Date();
        const start = new Date(startDate);
        const end = new Date(endDate);
    
        if (now < start) {
          return { text: "Rezerve Edildi", style: styles.reserved };
        } else if (now >= start && now <= end) {
          return { text: "Kullanımda", style: styles.inUse };
        } else {
          return { text: "Tamamlandı", style: styles.completed };
        }
      };
      const styles = {
        container: { display: "flex", flexDirection: "column", gap: "10px" },
        carItem: { display: "flex", alignItems: "center", border: "1px solid #ccc", padding: "10px", borderRadius: "5px" },
        image: { width: "200px", height: "100px", marginRight: "10px", objectFit: "cover" },
        details: { flex: 1, display: "flex", flexDirection: "column", gap: "5px" },
        status: { fontWeight: "bold", padding: "5px", borderRadius: "5px" },
    reserved: { backgroundColor: "#ffc107", color: "#fff" },
    inUse: { backgroundColor: "#007bff", color: "#fff" },
    completed: { backgroundColor: "#28a745", color: "#fff" },
      };
  return (
    <div style={styles.container}>
    {carlist.map((car, index) => {
      const status = getStatus(car.startDate, car.endDate);

      return (
        <div key={index} style={styles.carItem}>
         
          <div>
            <img src={car.car.image} alt={car.brand} style={styles.image} />
          </div>

         
          <div style={styles.details}>
            <div style={{ fontWeight:"bold" }}>{car.car.brand} {car.car.model}</div>
            <div><span style={{ fontWeight:"bold" }}>Başlangıç Tarihi: </span>{formatDate(car.startDate)}</div>
            <div><span style={{ fontWeight:"bold" }}>Bitiş Tarihi: </span>{formatDate(car.endDate)}</div>
          </div>

         
          <div style={{ ...styles.status, ...status.style }}>{status.text}</div>
        </div>
      );
    })}
  </div>
  );
}

export default Rezervasyon