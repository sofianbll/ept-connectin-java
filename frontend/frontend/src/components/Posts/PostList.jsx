import React from "react";
import PostItem from "./PostItem";

const PostList = ({ posts, onDelete, onUpdate }) => {
  if (posts.length === 0)
    return (
      <p className="text-center text-gray-500">
        Aucune publication pour le moment.
      </p>
    );

  return (
    <div className="mt-6">
      {posts.map((post) => (
        <PostItem
          key={post.id}
          post={post}
          onDelete={onDelete}
          onUpdate={onUpdate}
        />
      ))}
    </div>
  );
};

export default PostList;
