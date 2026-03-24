import React, { useState } from "react";
import api from "../../api/axios";

// On ajoute teamId dans les props
const PostForm = ({ onPostCreated, teamId = null }) => {
  const [captions, setCaptions] = useState("");
  const [image, setImage] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const formData = new FormData();
    formData.append("captions", captions);
    
    // --- NOUVEAU : On ajoute le team_id au formulaire s'il existe ---
    if (teamId) {
      formData.append("team_id", teamId);
    }

    if (image) {
      formData.append("image", image);
    }

    try {
      const response = await api.post("/posts", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      setCaptions("");
      setImage(null);
      e.target.reset();

      onPostCreated(response.data);
    } catch (error) {
      console.error("Erreur lors de l'envoi :", error.response?.data);
      alert(
        "Erreur : " +
          (error.response?.data?.message || "Impossible de publier"),
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="relative group mb-10">
      <div className="absolute -inset-1 bg-gradient-to-r from-purple-600 to-pink-600 rounded-3xl blur opacity-10 group-hover:opacity-20 transition duration-500"></div>

      <form
        onSubmit={handleSubmit}
        className="relative p-6 bg-white/5 backdrop-blur-xl rounded-3xl border border-white/10 shadow-2xl"
      >
        <h2 className="text-white font-bold mb-4 text-lg flex items-center gap-2">
          <span className="w-2 h-2 bg-purple-500 rounded-full animate-pulse"></span>
          {/* On adapte le titre si on est dans une team */}
          {teamId ? "Partager avec l'équipe" : "Partager quelque chose"}
        </h2>

        <textarea
          className="w-full bg-slate-900/40 border border-white/5 rounded-2xl p-4 text-white placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-purple-500/40 transition-all resize-none"
          placeholder={teamId ? "Écrivez aux membres du groupe..." : "Quoi de neuf aujourd'hui ?"}
          value={captions}
          onChange={(e) => setCaptions(e.target.value)}
          rows="3"
          required
        />

        <div className="mt-4 flex flex-col sm:flex-row items-center justify-between gap-4">
          <div className="w-full sm:w-auto">
            <label className="flex items-center justify-center gap-2 px-4 py-2 rounded-xl bg-white/5 border border-white/10 text-gray-300 hover:bg-white/10 hover:text-white cursor-pointer transition-all text-sm">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-5 w-5 text-purple-400"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"
                />
              </svg>
              <span>
                {image ? "Image sélectionnée ✅" : "Ajouter une photo"}
              </span>
              <input
                type="file"
                accept="image/*"
                onChange={(e) => setImage(e.target.files[0])}
                className="hidden"
              />
            </label>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="relative w-full sm:w-auto group/btn"
          >
            <div className="absolute -inset-0.5 bg-gradient-to-r from-purple-600 to-pink-600 rounded-xl blur opacity-40 group-hover/btn:opacity-100 transition duration-300"></div>
            <div className="relative bg-slate-900 px-8 py-2.5 rounded-xl flex items-center justify-center gap-2 transition-all group-active/btn:scale-95">
              <span className="text-white font-bold text-sm">
                {loading ? "Envoi..." : "Publier"}
              </span>
              {!loading && (
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-4 w-4 text-purple-400 group-hover/btn:translate-x-1 transition-transform"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M13 5l7 7-7 7M5 5l7 7-7 7"
                  />
                </svg>
              )}
            </div>
          </button>
        </div>
      </form>
    </div>
  );
};

export default PostForm;