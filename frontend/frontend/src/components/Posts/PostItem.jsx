import { useState } from "react";
import api from "../../api/axios";
import CommentSection from "./CommentSection";

const PostItem = ({ post, onDelete, onUpdate }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editedCaptions, setEditedCaptions] = useState(post.captions);

  const [isLiked, setIsLiked] = useState(post.is_liked);
  const [likesCount, setLikesCount] = useState(post.likes_count || 0);

  // État pour le compteur de commentaires (pour la réactivité)
  const [commentsCount, setCommentsCount] = useState(
    post.comments_count || post.comments?.length || 0
  );

  const STORAGE_URL = "http://localhost:8080/storage/";

  const handleDelete = async () => {
    if (window.confirm("Voulez-vous vraiment supprimer ce post ?")) {
      try {
        await api.delete(`/posts/${post.id}`);
        onDelete(post.id);
      } catch (error) {
        console.error("Erreur lors de la suppression", error);
        alert("Impossible de supprimer ce post.");
      }
    }
  };

  const handleUpdate = async () => {
    try {
      const response = await api.put(`/posts/${post.id}`, {
        captions: editedCaptions,
      });
      onUpdate(response.data);
      setIsEditing(false);
    } catch (error) {
      console.error("Erreur update", error);
      alert("Erreur lors de la modification");
    }
  };

  const handleLike = async () => {
    try {
      const prevIsLiked = isLiked;
      setIsLiked(!isLiked);
      setLikesCount(prevIsLiked ? likesCount - 1 : likesCount + 1);
      const response = await api.post(`/posts/${post.id}/like`);
      setIsLiked(response.data.is_liked);
      setLikesCount(response.data.likes_count);
    } catch (error) {
      console.error("Erreur like", error);
      setIsLiked(post.is_liked);
      setLikesCount(post.likes_count || 0);
    }
  };

  const handleCommentAdded = () => setCommentsCount(prev => prev + 1);
  const handleCommentDeleted = () => setCommentsCount(prev => prev - 1);

  return (
    <div className="bg-white/5 backdrop-blur-sm p-6 mb-6 rounded-3xl border border-white/10 hover:border-purple-500/30 transition-all duration-300 shadow-xl group">
      {/* Header du Post */}
      <div className="flex justify-between items-start mb-4">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-purple-500 to-pink-500 p-[2px]">
            <div className="w-full h-full rounded-full bg-slate-900 flex items-center justify-center text-xs font-bold text-white overflow-hidden">
              {post.user?.profil_pic ? (
                <img
                  src={post.user.profil_pic.startsWith('http') 
                    ? post.user.profil_pic 
                    : `${STORAGE_URL}${post.user.profil_pic}`}
                  alt="avatar"
                  className="object-cover w-full h-full"
                />
              ) : (
                `${post.user?.firstName?.charAt(0)}${post.user?.lastName?.charAt(0)}`
              )}
            </div>
          </div>
          <div>
            <h3 className="text-white font-bold text-sm leading-none">
              {post.user ? `${post.user.firstName} ${post.user.lastName}` : "Anonyme"}
            </h3>
            <span className="text-purple-300/40 text-[10px] uppercase tracking-wider">
              {new Date(post.created_at).toLocaleDateString()}
            </span>
          </div>
        </div>

        {/* Boutons Actions */}
        <div className="flex gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
          <button onClick={() => setIsEditing(!isEditing)} className="p-2 rounded-lg bg-white/5 text-purple-300 hover:bg-purple-500/20 transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
            </svg>
          </button>
          <button onClick={handleDelete} className="p-2 rounded-lg bg-white/5 text-pink-400 hover:bg-pink-500/20 transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
            </svg>
          </button>
        </div>
      </div>

      {/* --- LE CONTENU DU POST (CAPTIONS) --- */}
      {isEditing ? (
        <div className="space-y-3 mb-4">
          <textarea
            className="w-full bg-slate-900/50 border border-purple-500/30 rounded-xl p-3 text-white focus:outline-none focus:ring-2 focus:ring-purple-500/50"
            value={editedCaptions}
            onChange={(e) => setEditedCaptions(e.target.value)}
            rows="3"
          />
          <div className="flex gap-2 justify-end">
            <button onClick={() => setIsEditing(false)} className="text-xs text-gray-400 hover:text-white px-3 py-1">Annuler</button>
            <button onClick={handleUpdate} className="text-xs bg-purple-600 hover:bg-purple-500 text-white px-4 py-1 rounded-full">Enregistrer</button>
          </div>
        </div>
      ) : (
        <p className="text-gray-200 mb-4 leading-relaxed">{post.captions}</p>
      )}

      {/* --- L'IMAGE DU POST --- */}
      {post.image_path && (
        <div className="rounded-2xl overflow-hidden border border-white/5 mb-4 shadow-2xl">
          <img
            src={`${STORAGE_URL}${post.image_path}`}
            className="w-full object-cover max-h-[450px] hover:scale-[1.01] transition-transform duration-500"
            alt="Post"
          />
        </div>
      )}

      {/* Footer Interactions */}
      <div className="flex items-center gap-6 pt-4 border-t border-white/5 mt-4">
        <button onClick={handleLike} className={`flex items-center gap-2 transition-all ${isLiked ? "text-pink-500" : "text-gray-400 hover:text-white"}`}>
          <div className={`p-2 rounded-full ${isLiked ? "bg-pink-500/10" : "bg-white/5 hover:bg-white/10"}`}>
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill={isLiked ? "currentColor" : "none"} viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
            </svg>
          </div>
          <span className="text-sm font-medium">{likesCount}</span>
        </button>

        <button className="flex items-center gap-2 text-gray-400 hover:text-purple-400" onClick={() => document.getElementById(`comment-input-${post.id}`)?.focus()}>
          <div className="p-2 rounded-full bg-white/5 hover:bg-purple-500/10">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
            </svg>
          </div>
          <span className="text-sm font-medium">{commentsCount}</span>
        </button>
      </div>

      <CommentSection 
        postId={post.id} 
        initialComments={post.comments} 
        onCommentAdded={handleCommentAdded}
        onCommentDeleted={handleCommentDeleted}
      />
    </div>
  );
};

export default PostItem;