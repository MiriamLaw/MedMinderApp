"use client"
import { Link } from "react-router-dom"
import { useAuth } from "../context/AuthContext"

function Header() {
  const { user, logout } = useAuth()

  return (
    <header className="bg-white shadow">
      <div className="container mx-auto px-4 py-4 flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold text-red-500">
          MedMinder
        </Link>

        <div className="flex items-center">
          {user?.role === "ADMIN" && (
            <Link to="/admin" className="mr-4 text-gray-600 hover:text-gray-900">
              Admin
            </Link>
          )}

          <span className="mr-4 text-gray-600">{user?.email}</span>

          <button onClick={logout} className="text-red-500 hover:text-red-700">
            Logout
          </button>
        </div>
      </div>
    </header>
  )
}

export default Header
