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

  useEffect(() => {
    // In a real app, this would fetch medications for the selected day
    // For now, we'll use mock data
    setMedications([
      { id: 1, name: "Aspirin", dosage: "100mg", quantity: 1, frequency: "1x/day", timeSlot: "MORN", taken: false },
      { id: 2, name: "Vitamin D", dosage: "1000 IU", quantity: 1, frequency: "1x/day", timeSlot: "MORN", taken: true },
      { id: 3, name: "Lisinopril", dosage: "10mg", quantity: 1, frequency: "1x/day", timeSlot: "NOON", taken: false },
    ])
    setLoading(false)
  }, [day])

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

  if (loading) {
    return <div className="flex justify-center items-center h-screen">Loading...</div>
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />

      <main className="container mx-auto px-4 py-6">
        <div className="flex items-center mb-6">
          <button onClick={() => navigate("/")} className="text-gray-600 hover:text-gray-900">
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
          </div>
        </div>
      </main>
    </div>
  )
}

export default DayView
