import { useState, useEffect } from "react";
import api from "../../api/axios";

const MemberManager = ({ teamId, isOwner, currentMembers, onMemberAdded }) => {
  const [search, setSearch] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  // Recherche d'utilisateurs à ajouter
  useEffect(() => {
    if (search.length < 2) {
      setResults([]);
      return;
    }

    const delayDebounce = setTimeout(async () => {
      setLoading(true);
      try {
        const response = await api.get(`/users/search?q=${search}`);
        // On filtre pour ne pas proposer des gens déjà membres
        const filtered = response.data.filter(
          user => !currentMembers.some(m => m.id === user.id)
        );
        setResults(filtered);
      } catch (err) {
        console.error("Erreur recherche", err);
      } finally {
        setLoading(false);
      }
    }, 500);

    return () => clearTimeout(delayDebounce);
  }, [search, currentMembers]);

  const addMember = async (userId) => {
    try {
      await api.post(`/teams/${teamId}/members`, { user_id: userId });
      onMemberAdded(); // Rafraîchit la liste dans le parent
      setSearch("");
      setResults([]);
    } catch (err) {
      alert("Erreur lors de l'ajout du membre");
    }
  };

  if (!isOwner) return null; // Seul l'owner voit ce gestionnaire

  return (
    <div className="bg-white/5 border border-white/10 rounded-3xl p-6 mt-6">
      <h3 className="text-white font-bold mb-4 flex items-center gap-2">
        <span className="text-purple-500">+</span> Ajouter un membre
      </h3>
      
      <div className="relative">
        <input
          type="text"
          placeholder="Rechercher un nom..."
          className="w-full bg-slate-900/50 border border-white/10 rounded-xl px-4 py-2 text-white focus:ring-2 focus:ring-purple-500 outline-none"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />

        {results.length > 0 && (
          <div className="absolute z-10 w-full mt-2 bg-slate-800 border border-white/10 rounded-xl shadow-2xl overflow-hidden">
            {results.map(user => (
              <div 
                key={user.id} 
                className="p-3 hover:bg-white/5 flex justify-between items-center border-b border-white/5 last:border-0"
              >
                <span className="text-white text-sm">{user.firstName} {user.lastName}</span>
                <button 
                  onClick={() => addMember(user.id)}
                  className="bg-purple-600 hover:bg-purple-500 text-white text-[10px] font-bold px-3 py-1 rounded-lg transition-colors"
                >
                  Ajouter
                </button>
              </div>
            ))}
          </div>
        )}
        
        {loading && <p className="text-[10px] text-gray-500 mt-2 animate-pulse">Recherche en cours...</p>}
      </div>
    </div>
  );
};

export default MemberManager;