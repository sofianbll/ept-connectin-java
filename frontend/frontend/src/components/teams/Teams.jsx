import { useState, useEffect } from "react";
import api from "../../api/axios";
import { Link } from "react-router-dom";

const Teams = () => {
  const [teams, setTeams] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newTeam, setNewTeam] = useState({ name: "", description: "" });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTeams();
  }, []);

  const fetchTeams = async () => {
    try {
      const response = await api.get("/teams");
      setTeams(response.data);
    } catch (err) {
      console.error("Erreur chargement teams", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTeam = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/teams", newTeam);
      setTeams([...teams, response.data]);
      setShowModal(false);
      setNewTeam({ name: "", description: "" });
    } catch (err) {
      alert("Erreur lors de la création");
    }
  };

  return (
    <div className="max-w-6xl mx-auto p-6">
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-black text-white bg-clip-text text-transparent bg-gradient-to-r from-purple-400 to-pink-500">
            Mes Équipes
          </h1>
          <p className="text-gray-400 text-sm">Gérez vos espaces de travail collaboratifs</p>
        </div>
        
        <button 
          onClick={() => setShowModal(true)}
          className="bg-purple-600 hover:bg-purple-500 text-white px-6 py-3 rounded-2xl font-bold shadow-lg shadow-purple-500/20 transition-all flex items-center gap-2"
        >
          <span>+</span> Créer une Team
        </button>
      </div>

      {loading ? (
        <div className="text-center py-20 text-gray-500">Chargement des dossiers secrets...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {teams.map((team) => (
            <Link 
              to={`/teams/${team.id}`} 
              key={team.id}
              className="group relative bg-white/5 backdrop-blur-md border border-white/10 p-6 rounded-3xl hover:border-purple-500/50 transition-all duration-300"
            >
              <div className="flex justify-between items-start mb-4">
                <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center text-xl shadow-lg">
                  {team.name.charAt(0).toUpperCase()}
                </div>
                {/* Badge de rôle */}
                <span className={`text-[10px] uppercase font-bold px-2 py-1 rounded-md ${
                  team.pivot?.role === 'admin' ? 'bg-pink-500/20 text-pink-400' : 'bg-blue-500/20 text-blue-400'
                }`}>
                  {team.pivot?.role || 'Member'}
                </span>
              </div>

              <h3 className="text-xl font-bold text-white mb-2 group-hover:text-purple-400 transition-colors">
                {team.name}
              </h3>
              <p className="text-gray-400 text-sm line-clamp-2 mb-6">
                {team.description || "Aucune description fournie."}
              </p>

              <div className="flex items-center justify-between pt-4 border-t border-white/5">
                <span className="text-[10px] text-gray-500 uppercase tracking-widest font-bold">
                  Propriétaire : {team.owner?.firstName || "Anonyme"}
                </span>
                <div className="text-purple-400 group-hover:translate-x-1 transition-transform">
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7l5 5m0 0l-5 5m5-5H6" />
                  </svg>
                </div>
              </div>
            </Link>
          ))}
        </div>
      )}

      {/* Modal de création */}
      {showModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-950/80 backdrop-blur-sm">
          <div className="bg-slate-900 border border-white/10 p-8 rounded-3xl w-full max-w-md shadow-2xl">
            <h2 className="text-2xl font-bold text-white mb-6">Nouvelle Team</h2>
            <form onSubmit={handleCreateTeam} className="space-y-4">
              <div>
                <label className="block text-gray-400 text-xs font-bold mb-2 uppercase">Nom du groupe</label>
                <input 
                  type="text" 
                  required
                  className="w-full bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white focus:outline-none focus:ring-2 focus:ring-purple-500"
                  value={newTeam.name}
                  onChange={(e) => setNewTeam({...newTeam, name: e.target.value})}
                />
              </div>
              <div>
                <label className="block text-gray-400 text-xs font-bold mb-2 uppercase">Description</label>
                <textarea 
                  className="w-full bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white focus:outline-none focus:ring-2 focus:ring-purple-500 h-24 resize-none"
                  value={newTeam.description}
                  onChange={(e) => setNewTeam({...newTeam, description: e.target.value})}
                />
              </div>
              <div className="flex gap-3 pt-4">
                <button 
                  type="button" 
                  onClick={() => setShowModal(false)}
                  className="flex-1 px-4 py-3 rounded-xl text-gray-400 hover:bg-white/5 font-bold transition-all"
                >
                  Annuler
                </button>
                <button 
                  type="submit"
                  className="flex-1 bg-gradient-to-r from-purple-600 to-pink-600 px-4 py-3 rounded-xl text-white font-bold hover:opacity-90 transition-all"
                >
                  Créer
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Teams;