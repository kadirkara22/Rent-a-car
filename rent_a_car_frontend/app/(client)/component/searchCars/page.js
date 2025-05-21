import React, { useEffect } from 'react'

const SearchCars = ({handleSubmit,setPickupLocations,setPickupLocation,setPickupDate,setPickupTime,setReturnDate,setReturnTime,pickupDate,pickupTime,returnDate,pickupLocations,pickupLocation,returnTime,pickupLocationDetails, setPickupLocationDetails}) => {
      
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

  
  useEffect(() => {
    const today = new Date();
    const tomorrow = new Date();
    tomorrow.setDate(today.getDate() + 1);
 
    const formatDate = (date) =>
      date.toISOString().split("T")[0]; 

    const formatTime = (date) => {
      const hours = date.getHours().toString().padStart(2, "0");
      const minutes = Math.floor(date.getMinutes() / 30) * 30; 
      return `${hours}:${minutes === 0 ? "00" : "30"}`;
    };

    setPickupDate(formatDate(today));
    setPickupTime(formatTime(tomorrow));
    setReturnDate(formatDate(tomorrow));
    setReturnTime(formatTime(tomorrow));
  }, []);


  useEffect(() => {
    if (pickupDate) {
      const newReturnDate = new Date(pickupDate);
      newReturnDate.setDate(newReturnDate.getDate() + 1);
      setReturnDate(newReturnDate.toISOString().split("T")[0]);
    }
  }, [pickupDate]);


  useEffect(() => {
    setReturnTime(pickupTime);
  }, [pickupTime]);

 
function generateTimeOptions() {
    const times = [];
    for (let hour = 0; hour < 24; hour++) {
      times.push(`${hour.toString().padStart(2, "0")}:00`);
      times.push(`${hour.toString().padStart(2, "0")}:30`);
    }
    return times.map((time) => (
      <option key={time} value={time}>
        {time}
      </option>
    ));
  }

  const handleLocationChange = (e) => {
  const selectedId = e.target.value;
  setPickupLocation(selectedId);
console.log(selectedId)

  const selectedLocation = pickupLocations.find(
    (location) => location.id.toString() === selectedId
  );
  console.log(selectedLocation)
  if (selectedLocation) {
    setPickupLocationDetails({
        locationName: selectedLocation.locationName,
        city: selectedLocation.city,
      });
  } else {
    setPickupLocationDetails({
        locationName: "",
        city: "",
      });
  }
};
  return (
    <div style={{ padding: "20px", maxWidth: "850px", margin: "auto" }}>
    <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column" }}>
      <div style={{ marginBottom: "15px" }}>
        <label>Alış Yeri</label>
        <select
          value={pickupLocation}
          onChange={handleLocationChange}
          required
          style={{
            display: "block",
            width: "100%",
            padding: "10px",
            marginTop: "5px",
            border: "2px solid #EFEFEF",
          }}
        >
          {pickupLocations.map((location) => (
            <option key={location.id} value={location.id}>
              {location.locationName}, {location.city}
            </option>
          ))}
        </select>
      </div>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <div style={{ marginBottom: "15px", flex: "2" }}>
          <label>Alış Tarihi</label>
          <input
            type="date"
            value={pickupDate}
            onChange={(e) => setPickupDate(e.target.value)}
            required
            style={{
              display: "block",
              width: "100%",
              padding: "10px",
              marginTop: "5px",
              border: "2px solid #EFEFEF",
            }}
          />
        </div>

        <div style={{ marginBottom: "15px", flex: "1" }}>
          <label>Alış Saati</label>
          <select
            value={pickupTime}
            onChange={(e) => setPickupTime(e.target.value)}
            required
            style={{
              display: "block",
              width: "100%",
              padding: "13px",
              marginTop: "5px",
              border: "2px solid #EFEFEF",
            }}
          >
            {generateTimeOptions()}
          </select>
        </div>

        <div style={{ marginBottom: "15px", flex: "2" }}>
          <label>Dönüş Tarihi</label>
          <input
            type="date"
            value={returnDate}
            onChange={(e) => setReturnDate(e.target.value)}
            required
            style={{
              display: "block",
              width: "100%",
              padding: "10px",
              marginTop: "5px",
              border: "2px solid #EFEFEF",
            }}
          />
        </div>

        <div style={{ marginBottom: "15px", flex: "1" }}>
          <label>Dönüş Saati</label>
          <select
            value={returnTime}
            onChange={(e) => setReturnTime(e.target.value)}
            required
            style={{
              display: "block",
              width: "100%",
              padding: "13px",
              marginTop: "5px",
              border: "2px solid #EFEFEF",
            }}
          >
            {generateTimeOptions()}
          </select>
        </div>
      </div>
      <button
        type="submit"
        style={{
          padding: "10px 20px",
          backgroundColor: "green",
          color: "white",
          border: "none",
          width: "50%",
          alignSelf: "center",
        }}
      >
        Araçları Göster
      </button>
    </form>
  </div>
  )
}

export default SearchCars