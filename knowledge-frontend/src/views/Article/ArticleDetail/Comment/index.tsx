import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";
import {useCookies} from "react-cookie";

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


            </div>


            {comments.content.map((comment: CommentItem) => (
                <div key={comment.id}>
                    {/* Render individual comment details */}
                    <p>{comment.id}</p>
                    <p>Author: {comment.content}</p>
                    <p>Created At: {comment.userId}</p>
                </div>
            ))}

            {/* Pagination controls */}
            <button
                onClick={() => {
                    if (!comments.last) {
                        // Load the next page of comments
                        setPage(page + 1); // Increment the page number
                    }
                }}
            >
                Load More
            </button>

            {/* Page number input */}
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