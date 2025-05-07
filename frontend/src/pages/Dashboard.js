"use client"

import { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import { useAuth } from "../context/AuthContext"
import { getWeeks, createWeek } from "../services/weekService"
import Header from "../components/Header"

function Dashboard() {
  const { user } = useAuth()
  const [weeks, setWeeks] = useState([])
  const [selectedWeek, setSelectedWeek] = useState(null)
  const [loading, setLoading] = useState(true)

  const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]
  const timeSlots = ["MORN", "NOON", "NIGHT", "BACKUP"]

  const dayColors = {
    Sun: "bg-red-500 hover:bg-red-600",
    Mon: "bg-orange-500 hover:bg-orange-600",
    Tue: "bg-yellow-500 hover:bg-yellow-600",
    Wed: "bg-green-500 hover:bg-green-600",
    Thu: "bg-sky-500 hover:bg-sky-600",
    Fri: "bg-blue-500 hover:bg-blue-600",
    Sat: "bg-pink-500 hover:bg-pink-600",
  }

  const timeSlotColors = {
    MORN: {
      Sun: "bg-red-400",
      Mon: "bg-orange-400",
      Tue: "bg-yellow-400",
      Wed: "bg-green-400",
      Thu: "bg-sky-400",
      Fri: "bg-blue-400",
      Sat: "bg-pink-400",
    },
    NOON: {
      Sun: "bg-red-300",
      Mon: "bg-orange-300",
      Tue: "bg-yellow-300",
      Wed: "bg-green-300",
      Thu: "bg-sky-300",
      Fri: "bg-blue-300",
      Sat: "bg-pink-300",
    },
    NIGHT: {
      Sun: "bg-red-200",
      Mon: "bg-orange-200",
      Tue: "bg-yellow-200",
      Wed: "bg-green-200",
      Thu: "bg-sky-200",
      Fri: "bg-blue-200",
      Sat: "bg-pink-200",
    },
    BACKUP: {
      Sun: "bg-red-100",
      Mon: "bg-orange-100",
      Tue: "bg-yellow-100",
      Wed: "bg-green-100",
      Thu: "bg-sky-100",
      Fri: "bg-blue-100",
      Sat: "bg-pink-100",
    },
  }

  useEffect(() => {
    const fetchWeeks = async () => {
      try {
        const data = await getWeeks()
        setWeeks(data)
        if (data.length > 0) {
          setSelectedWeek(data[0])
        } else {
          // Create a new week if none exist
          const today = new Date()
          const startOfWeek = new Date(today)
          startOfWeek.setDate(today.getDate() - today.getDay()) // Set to Sunday

          const newWeek = await createWeek(startOfWeek.toISOString().split("T")[0])
          setWeeks([newWeek])
          setSelectedWeek(newWeek)
        }
      } catch (error) {
        console.error("Error fetching weeks:", error)
      } finally {
        setLoading(false)
      }
    }

    fetchWeeks()
  }, [])

  const handleAddWeek = async () => {
    if (weeks.length >= 4) {
      alert("Maximum 4 weeks allowed")
      return
    }

    try {
      const lastWeek = weeks[weeks.length - 1]
      const lastWeekDate = new Date(lastWeek.startDate)
      const newWeekDate = new Date(lastWeekDate)
      newWeekDate.setDate(lastWeekDate.getDate() + 7)

      const newWeek = await createWeek(newWeekDate.toISOString().split("T")[0])
      setWeeks([...weeks, newWeek])
    } catch (error) {
      console.error("Error adding week:", error)
    }
  }

  if (loading) {
    return <div className="flex justify-center items-center h-screen">Loading...</div>
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />

      <main className="container mx-auto px-4 py-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold">Weekly Pill Organizer</h2>

          <div className="flex items-center gap-2">
            <select
              className="border border-gray-300 rounded-md p-2"
              value={selectedWeek?.id || ""}
              onChange={(e) => {
                const weekId = e.target.value
                const week = weeks.find((w) => w.id.toString() === weekId)
                setSelectedWeek(week)
              }}
            >
              {weeks.map((week) => (
                <option key={week.id} value={week.id}>
                  Week of {new Date(week.startDate).toLocaleDateString()}
                </option>
              ))}
            </select>

            <button
              onClick={handleAddWeek}
              disabled={weeks.length >= 4}
              className="bg-red-500 hover:bg-red-600 text-white px-3 py-2 rounded-md disabled:opacity-50"
            >
              Add Week
            </button>
          </div>
        </div>

        {selectedWeek && (
          <div className="grid grid-cols-7 gap-2 overflow-x-auto pb-4">
            {days.map((day, index) => {
              const date = new Date(selectedWeek.startDate)
              date.setDate(date.getDate() + index)

              return (
                <div key={day} className="flex flex-col items-center">
                  <Link
                    to={`/day/${day}`}
                    className={`w-full py-2 rounded-t-lg font-bold text-white flex flex-col items-center ${dayColors[day]}`}
                  >
                    <span>{day}</span>
                    <span className="text-xs opacity-80">{date.getDate()}</span>
                  </Link>

                  {timeSlots.map((timeSlot) => (
                    <div
                      key={`${day}-${timeSlot}`}
                      className={`w-full py-2 text-xs font-medium text-center border-x border-b border-gray-300 ${timeSlotColors[timeSlot][day]}`}
                    >
                      {timeSlot}
                    </div>
                  ))}
                </div>
              )
            })}
          </div>
        )}
      </main>
    </div>
  )
}

export default Dashboard
