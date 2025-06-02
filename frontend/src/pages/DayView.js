"use client"

import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom"
import Header from "../components/Header"

function DayView() {
  const { day } = useParams()
  const navigate = useNavigate()

  const [activeTab, setActiveTab] = useState("MORN")
  const [medications, setMedications] = useState([])
  const [loading, setLoading] = useState(true)

  const timeSlots = ["MORN", "NOON", "NIGHT", "BACKUP"]

  const dayColors = {
    Sun: "bg-red-500 text-red-500",
    Mon: "bg-orange-500 text-orange-500",
    Tue: "bg-yellow-500 text-yellow-500",
    Wed: "bg-green-500 text-green-500",
    Thu: "bg-sky-500 text-sky-500",
    Fri: "bg-blue-500 text-blue-500",
    Sat: "bg-pink-500 text-pink-500",
  }

  // Example mapping of day names to IDs
  const dayNameToIdMap = {
    "Sun": 1,
    "Mon": 2,
    "Tue": 3,
    "Wed": 4,
    "Thu": 5,
    "Fri": 6,
    "Sat": 7
  };

  // Define fetchMedications outside of useEffect
  const fetchMedications = async () => {
    try {
      const dayId = dayNameToIdMap[day];
      const response = await fetch(`/medications/day/${dayId}`);
      if (!response.ok) throw new Error("Failed to fetch medications");
      const data = await response.json();
      setMedications(data);
      setLoading(false);
    } catch (err) {
      console.error("Error fetching medications:", err);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMedications();
  }, [day]);

  const handleAddMedication = () => {
    const newMedication = {
      id: Date.now(), // Temporary ID
      name: "",
      dosage: "",
      quantity: 1,
      frequency: "1x/day",
      timeSlot: activeTab,
      taken: false,
    }

    setMedications([...medications, newMedication])
  }

  const handleUpdateMedication = (id, field, value) => {
    setMedications(medications.map((med) => (med.id === id ? { ...med, [field]: value } : med)))
  }

  const handleDeleteMedication = (id) => {
    setMedications(medications.filter((med) => med.id !== id))
  }

  const handleToggleTaken = (id) => {
    setMedications(medications.map((med) => (med.id === id ? { ...med, taken: !med.taken } : med)))
  }

  const handleSaveMeds = async () => {
    const medsToSave = medications.filter((med) => med.timeSlot === activeTab);

    const hasEmptyName = medsToSave.some((med) => med.name.trim() === "");
    if (hasEmptyName) {
      alert("Please provide a name for all medications before saving.");
      return;
    }

    try {
      const response = await fetch(`/api/medications/${day}/${activeTab}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(medsToSave),
      });
      if (!response.ok) throw new Error("Failed to save medications");
      alert("Medications saved!");

      const responseData = await response.json();
      console.log("API Response:", responseData); // Log API response

      fetchMedications(); // Refetch medications to update the state
    } catch (err) {
      alert("Error saving medications: " + err.message);
    }
  };

  if (loading) {
    return <div className="flex justify-center items-center h-screen">Loading...</div>
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />

      <main className="container mx-auto px-4 py-6">
        <div className="flex items-center mb-6">
          <button onClick={() => navigate("/dashboard")} className="text-gray-600 hover:text-gray-900">
            &larr; Back
          </button>
          <h2 className={`text-2xl font-bold flex-1 text-center ${dayColors[day]}`}>{day}</h2>
        </div>

        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          <div className="flex border-b">
            {timeSlots.map((timeSlot) => (
              <button
                key={timeSlot}
                className={`flex-1 py-3 font-medium ${activeTab === timeSlot ? "bg-gray-100 border-b-2 border-red-500" : ""}`}
                onClick={() => setActiveTab(timeSlot)}
              >
                {timeSlot}
              </button>
            ))}
          </div>

          <div className="p-4">
            <h3 className="font-semibold text-lg mb-4">Medications</h3>

            {medications
              .filter((med) => med.timeSlot === activeTab)
              .map((medication) => (
                <div key={medication.id} className="mb-4 p-4 border rounded-md">
                  <div className="flex items-center mb-3">
                    <input
                      type="checkbox"
                      checked={medication.taken}
                      onChange={() => handleToggleTaken(medication.id)}
                      className="h-5 w-5 text-red-500 rounded"
                    />
                    <input
                      type="text"
                      value={medication.name}
                      onChange={(e) => handleUpdateMedication(medication.id, "name", e.target.value)}
                      placeholder="Medication name"
                      className="ml-3 flex-1 p-2 border rounded-md"
                      required
                    />
                    <button
                      onClick={() => handleDeleteMedication(medication.id)}
                      className="ml-2 text-gray-500 hover:text-red-500"
                    >
                      &times;
                    </button>
                  </div>

                  <div className="grid grid-cols-3 gap-2">
                    <div>
                      <label className="block text-sm text-gray-600">Dosage</label>
                      <input
                        type="text"
                        value={medication.dosage}
                        onChange={(e) => handleUpdateMedication(medication.id, "dosage", e.target.value)}
                        placeholder="Dosage"
                        className="w-full p-2 border rounded-md"
                      />
                    </div>
                    <div>
                      <label className="block text-sm text-gray-600">Quantity</label>
                      <input
                        type="number"
                        value={medication.quantity}
                        onChange={(e) =>
                          handleUpdateMedication(medication.id, "quantity", Number.parseInt(e.target.value))
                        }
                        min="1"
                        className="w-full p-2 border rounded-md"
                      />
                    </div>
                    <div>
                      <label className="block text-sm text-gray-600">Frequency</label>
                      <select
                        value={medication.frequency}
                        onChange={(e) => handleUpdateMedication(medication.id, "frequency", e.target.value)}
                        className="w-full p-2 border rounded-md"
                      >
                        <option value="1x/day">1x per day</option>
                        <option value="2x/day">2x per day</option>
                        <option value="3x/day">3x per day</option>
                        <option value="as-needed">As needed</option>
                      </select>
                    </div>
                  </div>
                </div>
              ))}

            <button
              onClick={handleAddMedication}
              className="w-full py-2 bg-red-500 hover:bg-red-600 text-white rounded-md mt-4"
            >
              + Add Medication
            </button>
            <button
              onClick={handleSaveMeds}
              className="w-full py-2 bg-green-600 hover:bg-green-700 text-white rounded-md mt-2"
              aria-label="Save meds"
            >
              Save meds
            </button>
          </div>
        </div>
      </main>
    </div>
  )
}

export default DayView
