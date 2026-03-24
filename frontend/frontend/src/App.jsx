import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from "./pages/Register";
import Login from "./pages/Login";
import Feed from "./pages/Feed";
import Profile from "./pages/Profile";
import Teams from "./components/teams/Teams"; 
import TeamDetails from "./components/teams/TeamDetails"; // Import du nouveau composant
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";

function App() {
  return (
    <Router>
      <Routes>
        {/* Routes publiques */}
        <Route path="/register" element={<Register />} />
        <Route path="/login" index element={<Login />} />

        {/* Routes protégées avec Navbar */}
        <Route
          path="/*" // On utilise /* pour permettre les sous-routes internes
          element={
            <ProtectedRoute>
              <div className="min-h-screen bg-[#0f172a] text-white flex flex-col">
                <Navbar />
                <main className="flex-1 bg-gradient-to-b from-slate-900 to-black pt-8 px-4">
                  <div className="max-w-4xl mx-auto">
                    <Routes>
                      <Route path="/" element={<Feed />} />
                      <Route path="/teams" element={<Teams />} />
                      <Route path="/teams/:id" element={<TeamDetails />} /> 
                      
                    </Routes>
                  </div>
                </main>
              </div>
            </ProtectedRoute>
          }
        />
        
        <Route path="/profile" element={<ProtectedRoute> <Profile /></ProtectedRoute>} />

      </Routes>
    </Router>
  );
}

export default App;