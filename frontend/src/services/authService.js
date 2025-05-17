// This service communicates with the backend API for authentication

export const register = async (email, password) => {
  const response = await fetch("/api/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
  })

  if (!response.ok) {
    const error = await response.text()
    throw new Error(error || "Failed to register")
  }

  return await response.json()
}

export const getCurrentUser = async () => {
  const response = await fetch('/api/auth/me', {
    credentials: 'include',
  });
  if (response.ok) {
    return await response.json();
  }
  throw new Error('Not authenticated');
}

export const login = async (email, password) => {
  // In a real app, this would call the backend API
  // For now, we'll simulate a successful login
  return { id: 1, email, role: "USER" }
}
