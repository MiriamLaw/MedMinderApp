import { useNavigate } from "react-router-dom"

const Welcome = () => {
  const navigate = useNavigate()
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <h1 className="text-3xl font-bold mb-4">Welcome to MedMinder</h1>
      <p className="mb-6">Your medication management made easy.</p>
      <button
        className="px-6 py-2 bg-red-600 text-white rounded hover:bg-red-700"
        onClick={() => navigate("/dashboard")}
        tabIndex={0}
        aria-label="Go to Dashboard"
      >
        Go to Dashboard
      </button>
    </div>
  )
}

export default Welcome 