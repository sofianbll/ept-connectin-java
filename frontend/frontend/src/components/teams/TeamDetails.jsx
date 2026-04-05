import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../api/axios";
import PostItem from "../Posts/PostItem";
import PostForm from "../Posts/PostForm";
import MemberManager from "./MemberManager";

const TeamDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [team, setTeam] = useState(null);
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAddMember, setShowAddMember] = useState(false);
  
  // --- LOGIQUE DE RÉCUPÉRATION DE L'UTILISATEUR MISE À JOUR ---
  const userString = localStorage.getItem("user");
  const userData = userString ? JSON.parse(userString) : null;
  
  /**
   * On cherche l'utilisateur dans toutes les structures possibles :
   * 1. Directement (userData.id)
   * 2. Dans une sous-clé user (userData.user.id)
   */
  const currentUser = userData?.user ? userData.user : userData; 

  // Debug pour vérifier que l'ID n'est plus undefined dans ta console
  useEffect(() => {
    console.log("DEBUG - Objet User complet :", currentUser);
    console.log("Mon ID (React) :", currentUser?.id);
    console.log("ID du chef (Base de données) :", team?.owner_id);
  }, [currentUser, team]);

  useEffect(() => {
    fetchTeamData();
  }, [id]);

  const fetchTeamData = async () => {
    try {
      const teamRes = await api.get(`/teams/${id}`);
      setTeam(teamRes.data);
      const postsRes = await api.get(`/posts?team_id=${id}`);
      setPosts(postsRes.data);
    } catch (err) {
      console.error("Erreur chargement team", err);
    } finally {
      setLoading(false);
    }
  };

  // Action : Supprimer un membre (Owner)
  const handleRemoveMember = async (memberId) => {
    if (!window.confirm("Supprimer ce membre de l'équipe ?")) return;
    try {
      await api.delete(`/teams/${id}/members/${memberId}`);
      fetchTeamData();
    } catch (err) {
      alert(err.response?.data?.message || "Erreur lors de la suppression.");
    }
  };

  // Action : Quitter l'équipe (Membre)
  const handleLeaveTeam = async () => {
    if (!currentUser?.id) {
      alert("Erreur d'identification : l'ID utilisateur est introuvable. Reconnectez-vous.");
      return;
    }
    
    if (!window.confirm("Voulez-vous vraiment quitter cette équipe ?")) return;
    
    try {
      // On envoie l'ID qui est maintenant bien défini
      await api.delete(`/teams/${id}/members/${currentUser.id}`);
      navigate("/teams");
    } catch (err) {
      alert(err.response?.data?.message || "Erreur lors du départ.");
    }
  };

  // Action : Supprimer l'équipe complète (Owner)
  const handleDeleteTeam = async () => {
    if (!window.confirm("DANGER : Supprimer définitivement cette équipe et tous ses posts ?")) return;
    try {
      await api.delete(`/teams/${id}`);
      navigate("/teams");
    } catch (err) {
      alert(err.response?.data?.message || "Erreur lors de la suppression de l'équipe.");
    }
  };

  if (loading) return <div className="text-center p-10 text-white">Chargement...</div>;
  if (!team) return <div className="text-center p-10 text-white">Équipe introuvable.</div>;

  /**
   * Comparaison forcée en Number pour que "1" (string) soit égal à 1 (int)
   * Cela permettra d'afficher le bouton Supprimer
   */
  const isOwner = currentUser && team ? Number(currentUser.id) === Number(team.owner_id) : false;

  return (
    <div className="space-y-8 animate-fadeIn">
      {/* Header Team */}
      <div className="bg-white/5 p-8 rounded-3xl border border-white/10 relative overflow-hidden shadow-2xl">
        <div className="relative flex flex-col md:flex-row justify-between items-start md:items-center gap-6">
          <div>
            <span className="px-3 py-1 rounded-full bg-purple-500/20 text-purple-400 text-[10px] font-black uppercase tracking-widest border border-purple-500/30">
              Team Space
            </span>
            <h1 className="text-4xl font-black text-white mt-2 tracking-tight">{team.name}</h1>
            <p className="text-gray-400 mt-1 max-w-lg">{team.description || "Aucune description."}</p>
          </div>

          {/* SECTION MEMBRES */}
          <div className="bg-slate-900/50 p-4 rounded-2xl border border-white/5 min-w-[180px]">
            <div className="flex justify-between items-center mb-3">
              <span className="text-[10px] font-bold text-gray-500 uppercase tracking-widest">Membres</span>
              {/* Le bouton quitter n'apparaît que si on n'est PAS le chef */}
              {!isOwner && (
                <button onClick={handleLeaveTeam} className="text-[10px] text-red-400 hover:text-red-300 font-bold uppercase transition-colors">
                  Quitter
                </button>
              )}
            </div>

            <div className="flex items-center justify-center">
              <div className="flex -space-x-2">
                {team.members?.map(member => (
                  <div key={member.id} className="relative group">
                    <div title={member.firstName} className="w-10 h-10 rounded-full border-2 border-slate-900 bg-gradient-to-br from-purple-500 to-pink-500 flex items-center justify-center text-xs font-bold text-white shadow-lg">
                      {member.firstName ? member.firstName.charAt(0).toUpperCase() : '?'}
                    </div>
                    {/* Suppression des membres par le chef */}
                    {isOwner && Number(member.id) !== Number(currentUser.id) && (
                      <button
                        onClick={() => handleRemoveMember(member.id)}
                        className="absolute -top-1 -right-1 w-4 h-4 bg-red-500 text-white rounded-full flex items-center justify-center text-[10px] opacity-0 group-hover:opacity-100 transition-opacity border border-slate-900 shadow-xl"
                      >
                        ×
                      </button>
                    )}
                  </div>
                ))}
              </div>
              
              {isOwner && (
                <button onClick={() => setShowAddMember(!showAddMember)} className="w-10 h-10 rounded-full border-2 border-dashed border-white/40 text-white flex items-center justify-center ml-3 bg-white/5 hover:bg-purple-500/20 transition-all cursor-pointer z-50">
                  <span className={`text-2xl ${showAddMember ? 'rotate-45' : ''} transition-transform`}>+</span>
                </button>
              )}
            </div>
          </div>
        </div>

        {showAddMember && (
          <div className="mt-6 border-t border-white/5 pt-6 animate-fadeIn">
            <MemberManager teamId={id} isOwner={true} currentMembers={team.members} onMemberAdded={() => { fetchTeamData(); setShowAddMember(false); }} />
          </div>
        )}
      </div>

      <PostForm onPostCreated={(newPost) => setPosts([newPost, ...posts])} teamId={id} />

      <div className="space-y-6">
        {posts.map(post => (
          <PostItem key={post.id} post={post} onDelete={(did) => setPosts(posts.filter(p => p.id !== did))} />
        ))}
      </div>

      {/* ZONE DANGER : Apparaîtra enfin si Mon ID === ID Chef */}
      {isOwner && (
        <div className="pt-10 flex justify-center border-t border-white/5">
          <button 
            onClick={handleDeleteTeam}
            className="px-6 py-2 bg-red-500/10 hover:bg-red-500 text-red-500 hover:text-white border border-red-500/20 rounded-xl text-xs font-bold transition-all duration-300 uppercase tracking-widest"
          >
            Supprimer définitivement l'équipe
          </button>
        </div>
      )}
    </div>
  );
};

export default TeamDetails;