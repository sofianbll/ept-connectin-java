import { useEffect, useState } from "react";
import api from "../api/axios";
import PostForm from "../components/Posts/PostForm";
import PostList from "../components/Posts/PostList";

const Feed = () => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await api.get("/posts");
        console.log("DONNÉES REÇUES DU SERVEUR :", response.data);
        setPosts(response.data);
      } catch (error) {
        console.error("Erreur lors du chargement des posts", error);
      }
    };
    fetchPosts();
  }, []);

  const handlePostCreated = (newPost) => {
    setPosts([newPost, ...posts]);
  };
  const handlePostDeleted = (deletedPostId) => {
    setPosts(posts.filter((post) => post.id !== deletedPostId));
  };
  const handlePostUpdated = (updatedPost) => {
    setPosts(posts.map((p) => (p.id === updatedPost.id ? updatedPost : p)));
  };

  return (
    <div className="min-h-screen bg-[#0f172a] bg-gradient-to-b from-slate-900 to-black py-12 px-4">
      <div className="max-w-2xl mx-auto">
        <div className="flex justify-between items-center mb-10">
          <div>
            <h1 className="text-3xl font-black text-white tracking-tight">
              Fil d'actualité
            </h1>
            <div className="h-1 w-12 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full mt-1"></div>
          </div>
        </div>

        <div className="space-y-8">
          <PostForm onPostCreated={handlePostCreated} />
          <PostList
            posts={posts}
            onDelete={handlePostDeleted}
            onUpdate={handlePostUpdated}
          />
        </div>
      </div>
    </div>
  );
};

export default Feed;
