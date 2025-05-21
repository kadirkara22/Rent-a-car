import React from 'react'

const Search = ({formData, pickupLocations,pickupLocation,pickupLocationDetails,setFormData,setPickupLocations,setPickupLocation,setPickupLocationDetails,handleSubmit}) => {
console.log(formData)
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
        <label>Ofis Seç</label>
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

export default Search