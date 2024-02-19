import {useCookies} from "react-cookie";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import Pagination from "../../../components/Pagination";
import {useUserStore} from "../../../stores/userStore";
interface CommentItem {
    id: number;
    content: string;
    userId: string;
}


function Comments() {
    const { articleId } = useParams();
    const [comments, setComments] = useState<CommentItem[]>([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [commentInput, setCommentInput] = useState<string>('');
    const [cookies] = useCookies(['accessToken']);
    const { user } = useUserStore();
    const [editingCommentId, setEditingCommentId] = useState<number | null>(null);
    const [editCommentContent, setEditCommentContent] = useState<string>('');


    useEffect(() => {
        // This function is now inside useEffect to capture currentPage changes
        const fetchComments = async () => {
            try {
                const response = await axios.get(`http://localhost:4040/api/v1/comments/${articleId}/comment?page=${currentPage}&size=3`, {
                    headers: { Authorization: `Bearer ${cookies.accessToken}` },
                });
                setComments(response.data.data.content);
                setTotalPages(response.data.data.totalPages);
                // It's better to update the current page based on the response to stay in sync with backend
                setCurrentPage(response.data.data.pageable.pageNumber);
            } catch (error) {
                console.error('Error fetching comments:', error);
            }
        };

        fetchComments();
    }, [articleId, currentPage, cookies.accessToken]);

    const fetchComments = async (page: number) => {
        try {
            const response = await axios.get(`http://localhost:4040/api/v1/comments/${articleId}/comment?page=${page}&size=3`, {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            });
            setComments(response.data.data.content);
            setTotalPages(response.data.data.totalPages);
            setCurrentPage(response.data.data.pageable.pageNumber);
        } catch (error) {
            console.error('Error fetching comments:', error);
        }
    };

    const handlePageChange = (newPage: number) => {
        setCurrentPage(newPage);
    };

    const handleCommentInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCommentInput(e.target.value);
    };

    const handleCommentSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!commentInput.trim()) return;

        try {
            await axios.post(`http://localhost:4040/api/v1/comments/${articleId}/comment`, { content: commentInput }, {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            });
            setCommentInput('');
            fetchComments(currentPage); // Refresh comments
        } catch (error) {
            console.error('Error posting comment:', error);
        }
    };

    const handleDeleteComment = async (commentId: number) => {
        try {
            await axios.delete(`http://localhost:4040/api/v1/comments/${commentId}/comment`, {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            });
            fetchComments(currentPage); // Refresh comments

        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };

    const startEditing = (comment: CommentItem) => {
        setEditingCommentId(comment.id);
        setEditCommentContent(comment.content);
    };

    const handleEditChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEditCommentContent(e.target.value);
    };

    const handleEditSubmit = async (e: React.FormEvent, commentId: number) => {
        e.preventDefault();
        if (!editCommentContent.trim()) return;

        try {
            await axios.patch(`http://localhost:4040/api/v1/comments/${commentId}/comment`, { content: editCommentContent }, {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            });
            setEditingCommentId(null);
            setEditCommentContent('');
        } catch (error) {
            console.error('Error updating comment:', error);
        }
    };

    return (
        <div>
            <div className="flex flex-col gap-4">
                {cookies.accessToken && (
                    <form onSubmit={handleCommentSubmit} className="flex justify-end gap-2">
                        <input
                            type="text"
                            value={commentInput}
                            onChange={handleCommentInputChange}
                            placeholder="Write a comment..."
                            className="rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring focus:ring-blue-500 focus:ring-opacity-50"
                        />
                        <button type="submit" className="bg-blue-500 text-white px-3 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring focus:ring-blue-500 focus:ring-opacity-50">
                            Post Comment
                        </button>
                    </form>
                )}

                {comments.map((comment) => (
                    <div key={comment.id} className="flex items-center gap-2 border border-gray-300 rounded-md p-3">
                        {editingCommentId === comment.id ? (
                            <form onSubmit={(e) => handleEditSubmit(e, comment.id)} className="flex items-center gap-2">
                                <input
                                    type="text"
                                    value={editCommentContent}
                                    onChange={handleEditChange}
                                    placeholder="Edit your comment..."
                                    className="rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring focus:ring-blue-500 focus:ring-opacity-50"
                                />
                                <button type="submit" className="bg-blue-500 text-white px-3 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring focus:ring-blue-500 focus:ring-opacity-50">
                                    Submit Edit
                                </button>
                                <button onClick={() => setEditingCommentId(null)} className="text-gray-500 hover:text-gray-700 focus:outline-none focus:ring focus:ring-gray-300 focus:ring-opacity-50">
                                    Cancel
                                </button>
                            </form>
                        ) : (
                            <>
                                <div className="border-gray-200 w-full dark:border-gray-800 my-4">
                                    <div className="grid gap-4 py-4">
                                        <div className="flex items-center justify-between">

                                    <p className="text-gray-700 w-1/4">id: {comment.id}</p>
                                    <p className="text-gray-700 flex-1">내용: {comment.content}</p>
                                    <p className="text-gray-700 w-1/4">작성자: {comment.userId}</p>
                                </div>
                                    </div>
                                </div>
                                {user?.userId === comment.userId && (
                                    <>
                                        <button onClick={() => startEditing(comment)} className="text-blue-500 hover:text-blue-700 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-50">
                                            Edit
                                        </button>
                                        <button onClick={() => handleDeleteComment(comment.id)} className="text-red-500 hover:text-red-700 focus:outline-none focus:ring focus:ring-red-300 focus:ring-opacity-50">
                                            Delete
                                        </button>
                                    </>
                                )}
                            </>
                        )}
                    </div>
                ))}
            </div>
            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />

        </div>
    );
}

export default Comments;