import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";
import {useCookies} from "react-cookie";
import {useUserStore} from "../../../../stores/user.store";

interface CommentResponse {
    code: string;
    message: string;
    comments: {
        content: CommentItem[];
        pageable: {
            pageNumber: number;
            pageSize: number;
            // Add other pageable properties if needed
        };
        last: boolean;
        totalElements: number;
        totalPages: number;
        size: number;
        number: number;
        first: boolean;
        numberOfElements: number;
        empty: boolean;
    };
}

interface CommentItem {
    id: number;
    content: string;
    userId: string;
}

function Comments() {
    const { articleId } = useParams();
    const [commentData, setCommentData] = useState<CommentResponse | null>(null);
    const [page, setPage] = useState<number>(0); // Initialize page to 0
    const [pageNumberInput, setPageNumberInput] = useState<string>("1"); // Input field for page number
    const [commentInput, setCommentInput] = useState<string>(""); // State for the comment input field
    const [cookies] = useCookies(["accessToken"]); // Assuming you're using react-cookie for managing cookies
    const accessToken = cookies.accessToken; // Retrieving the access token from cookies
    const { user } = useUserStore(); // Access the current user from the store
    const [editingCommentId, setEditingCommentId] = useState<number | null>(null); // State to manage which comment is being edited
    const [editCommentContent, setEditCommentContent] = useState<string>(""); // State to manage the input of the comment being edited
    const startEditing = (comment: CommentItem) => {
        setEditingCommentId(comment.id);
        setEditCommentContent(comment.content); // Pre-fill the input with the current comment content
    };
    const handleDeleteComment = async (commentId: number) => {
        if (!accessToken) {
            console.error("No access token available.");
            return;
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${accessToken}` },
            };
            await axios.delete(`http://localhost:4040/api/v1/comments/${commentId}/comment`, config);
            fetchComments(); // Refresh the comments list after deletion
        } catch (error) {
            console.error("Error deleting comment:", error);
        }
    };

    const submitEditComment = async (commentId: number) => {
        if (!accessToken) {
            return; // Early return if no token
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${accessToken}` },
            };
            await axios.patch(
                `http://localhost:4040/api/v1/comments/${commentId}/comment`,
                { content: editCommentContent },
                config
            );
            setEditingCommentId(null); // Reset editing state
            fetchComments(); // Re-fetch comments to show the updated comment
        } catch (error) {
            console.error("Error updating comment:", error);
        }
    };
    const handleEditCommentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEditCommentContent(e.target.value);
    };
    const fetchComments = async () => {
        try {
            const response: AxiosResponse<CommentResponse> = await axios.get(
                `http://localhost:4040/api/v1/comments/${articleId}/comment?page=${page}&size=5`
            );
            setCommentData(response.data);
        } catch (error) {
            console.error("Error fetching comments:", error);
        }
    };



    useEffect(() => {
        fetchComments(); // Initial fetch
    }, [articleId, page]); // Update when articleId or page changes

    const handlePageNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPageNumberInput(e.target.value);
    };

    const navigateToPage = () => {
        const newPage = parseInt(pageNumberInput) - 1; // Convert to 0-based page
        if (
            commentData &&
            commentData.comments &&
            !isNaN(newPage) &&
            newPage >= 0 &&
            newPage < commentData.comments.totalPages
        ) {
            setPage(newPage); // Update the page
        }    };

    if (!commentData) {
        return <div>Loading comments...</div>;
    }

    const { comments } = commentData;
    const handleCommentInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCommentInput(e.target.value);
    };
    const handleCommentSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!commentInput.trim() || !accessToken || !articleId) {
            return; // Early return if input is empty, no token, or no articleId
        }

        try {
            await axios.post(
                `http://localhost:4040/api/v1/comments/${articleId}/comment`,
                { content: commentInput },
                { headers: { Authorization: `Bearer ${accessToken}` } }
            );
            setCommentInput(""); // Clear the input field after successful submission
            fetchComments(); // Re-fetch comments to show the newly added comment
        } catch (error) {
            console.error("Error posting comment:", error);
        }
    };
    return (
        <div>
            {accessToken && (
                <form onSubmit={handleCommentSubmit}>
                    <input
                        type="text"
                        value={commentInput}
                        onChange={handleCommentInputChange}
                        placeholder="Write a comment..."
                    />
                    <button type="submit">Post Comment</button>
                </form>
            )}

            {comments.content.map((comment: CommentItem) => (
                <div key={comment.id}>
                    {editingCommentId === comment.id ? (
                        <div>
                            <input
                                type="text"
                                value={editCommentContent}
                                onChange={handleEditCommentChange}
                            />
                            <button onClick={() => submitEditComment(comment.id)}>Save</button>
                            <button onClick={() => setEditingCommentId(null)}>Cancel</button>
                        </div>
                    ) : (
                        <div>
                            <p>Comment ID: {comment.id}</p>
                            <p>Content: {comment.content}</p>
                            <p>User ID: {comment.userId}</p>
                            {user?.userId === comment.userId && (
                                <>
                                    <button onClick={() => startEditing(comment)}>Edit</button>
                                    <button onClick={() => handleDeleteComment(comment.id)}>Delete</button>
                                </>
                            )}

                        </div>
                    )}
                </div>
            ))}

            <button
                onClick={() => {
                    if (!comments.last) {
                        setPage(page + 1);
                    }
                }}
            >
                Load More
            </button>

            <div>
                <input
                    type="number"
                    value={pageNumberInput}
                    onChange={handlePageNumberChange}
                    min="1"
                    max={commentData.comments.totalPages}
                />
                <button onClick={navigateToPage}>Go to Page</button>
            </div>
        </div>
    );
}


export default Comments;