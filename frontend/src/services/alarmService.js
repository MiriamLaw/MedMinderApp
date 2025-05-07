// This is a simplified service for alarm management
// In a real app, these would make actual API calls

export const getAlarmsForMedication = async (medicationId) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return [
    { id: 1, time: "08:00", status: "SET" },
    { id: 2, time: "20:00", status: "TAKEN" },
  ]
}

export const createAlarm = async (medicationId, time) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return {
    id: Date.now(),
    time,
    status: "SET",
  }
}

export const updateAlarm = async (id, time) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return {
    id,
    time,
    status: "SET",
  }
}

export const updateAlarmStatus = async (id, status) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return {
    id,
    status,
  }
}

export const deleteAlarm = async (id) => {
  // In a real app, this would call the backend API
  // For now, we'll just return success
  return { success: true }
}
