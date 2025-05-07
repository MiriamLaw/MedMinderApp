// This is a simplified service for admin functionality
// In a real app, these would make actual API calls

export const getAllUsers = async () => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return [
    { id: 1, email: "user@example.com", role: "USER" },
    { id: 2, email: "admin@example.com", role: "ADMIN" },
    { id: 3, email: "test@example.com", role: "USER" },
  ]
}

export const deleteUser = async (id) => {
  // In a real app, this would call the backend API
  // For now, we'll just return success
  return { success: true }
}

export const promoteUser = async (id) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return { id, email: "user@example.com", role: "ADMIN" }
}

export const demoteUser = async (id) => {
  // In a real app, this would call the backend API
  // For now, we'll return mock data
  return { id, email: "admin@example.com", role: "USER" }
}
