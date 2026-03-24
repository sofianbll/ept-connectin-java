import { useState } from "react";
import api from "../../api/axios";

// On ajoute onCommentAdded et onCommentDeleted dans les props récupérées
const CommentSection = ({ postId, initialComments, onCommentAdded, onCommentDeleted }) => {
  const [comments, setComments] = useState(initialComments || []);
  const [newComment, setNewComment] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [editValue, setEditValue] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!newComment.trim() || loading) return;
    setLoading(true);
    try {
      const response = await api.post(`/posts/${postId}/comments`, { text: newComment });
      setComments([...comments, response.data]);
      setNewComment("");

      // --- ACTION : On prévient le parent qu'un commentaire est ajouté ---
      if (onCommentAdded) onCommentAdded();

    } catch (err) { 
      alert("Erreur lors de l'envoi"); 
    } finally { 
      setLoading(false); 
    }
  };

  const handleDelete = async (commentId) => {
    if (!window.confirm("Supprimer ce commentaire ?")) return;
    try {
      await api.delete(`/comments/${commentId}`);
      setComments(comments.filter(c => c.id !== commentId));

      // --- ACTION : On prévient le parent qu'un commentaire est supprimé ---
      if (onCommentDeleted) onCommentDeleted();

    } catch (err) { 
      alert("Impossible de supprimer"); 
    }
  };

  const handleUpdate = async (commentId) => {
    try {
      const response = await api.put(`/comments/${commentId}`, { text: editValue });
      setComments(comments.map(c => c.id === commentId ? response.data : c));
      setEditingId(null);
    } catch (err) { 
      alert("Erreur lors de la modification"); 
    }
  };

  return (
    <div className="mt-4 border-t border-white/10 pt-6 bg-transparent">
      {/* Note : Ici on utilise comments.length pour le titre local, 
          mais le PostItem utilisera son propre état synchronisé */}
      <h4 className="text-sm font-bold text-white mb-4 flex items-center gap-2">
        <span className="w-1.5 h-1.5 bg-pink-500 rounded-full"></span>
        Commentaires ({comments.length})
      </h4>

      <div className="space-y-3 mb-6 max-h-60 overflow-y-auto pr-2 custom-scrollbar">
        {comments.map((comment) => (
          <div key={comment.id} className="bg-white/5 backdrop-blur-md p-3 rounded-2xl border border-white/5 group/comm transition-all hover:bg-white/10">
            <div className="flex justify-between items-start">
              <div className="flex items-center gap-2 mb-1">
                <span className="font-bold text-purple-400 text-xs">
                  {comment.user?.firstName} {comment.user?.lastName}
                </span>
              </div>
              
              <div className="flex gap-2 opacity-0 group-hover/comm:opacity-100 transition-opacity">
                <button 
                  onClick={() => { setEditingId(comment.id); setEditValue(comment.text); }}
                  className="text-gray-500 hover:text-purple-400 text-[10px] uppercase font-bold"
                >
                  Modifier
                </button>
                <button 
                  onClick={() => handleDelete(comment.id)}
                  className="text-gray-500 hover:text-pink-500 text-[10px] uppercase font-bold"
                >
                  Supprimer
                </button>
              </div>
            </div>

            {editingId === comment.id ? (
              <div className="mt-2 space-y-2">
                <input 
                  className="w-full bg-slate-900 border border-purple-500/50 rounded-lg px-3 py-1 text-sm text-white focus:outline-none"
                  value={editValue}
                  onChange={(e) => setEditValue(e.target.value)}
                  autoFocus
                />
                <div className="flex gap-2 justify-end">
                  <button onClick={() => setEditingId(null)} className="text-[10px] text-gray-400">Annuler</button>
                  <button onClick={() => handleUpdate(comment.id)} className="text-[10px] text-purple-400 font-bold">Enregistrer</button>
                </div>
              </div>
            ) : (
              <p className="text-gray-300 text-sm leading-relaxed">{comment.text}</p>
            )}
          </div>
        ))}
      </div>

      <form onSubmit={handleSubmit} className="flex gap-2 items-center">
        <input
          type="text"
          className="flex-1 bg-slate-900/60 border border-white/10 rounded-xl px-4 py-2 text-sm text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-purple-500/40"
          placeholder="Écrire un commentaire..."
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
        />
        <button type="submit" disabled={!newComment.trim() || loading} className="bg-slate-900 border border-white/10 text-white px-4 py-2 rounded-xl text-xs font-bold hover:bg-white/5">
          {loading ? "..." : "Publier"}
        </button>
      </form>
    </div>
  );
};

export default CommentSection;