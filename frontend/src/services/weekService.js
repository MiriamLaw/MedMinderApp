// This is a simplified service for week management
// In a real app, these would make actual API calls

export const getWeeks = async () => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  const today = new Date()
  const startOfWeek = new Date(today)
  startOfWeek.setDate(today.getDate() - today.getDay()) // Set to Sunday

  return [
    {
      id: 1,
      startDate: startOfWeek.toISOString().split("T")[0],
    },
  ]
}

export const createWeek = async (startDate) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return {
    id: Date.now(),
    startDate,
  }
}
