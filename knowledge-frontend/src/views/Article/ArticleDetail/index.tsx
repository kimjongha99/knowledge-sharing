import {useNavigate, useParams} from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Comments from "./Comment";
import {useCookies} from "react-cookie";
import {useUserStore} from "../../../stores/user.store";

// Define an interface to represent the response data structure
interface ArticleResponse {
    id: number;
    title: string;
    content: string;
    writer: string;
    favoriteCount: number;
    viewCount: number;
    articleHashtags: string[];
    imageUrls: string[];
}

function ArticleDetail() {
    const { articleId } = useParams();
    const [articleDetail, setArticleDetail] = useState<ArticleResponse | null>(null); // Annotate with the interface
    const [cookies] = useCookies();
    const [isUpdatingFavorite, setIsUpdatingFavorite] = useState(false); // New state to track request status
    const [isEditing, setIsEditing] = useState(false); // Initialize state for managing edit form visibility
    const navigate = useNavigate(); // Initialize useNavigate hook

    const accessToken = cookies.accessToken; // Retrieving the access token from cookies
    const { user } = useUserStore(); // Access the current user from the store
    const showEditForm = user?.userId === articleDetail?.writer; // Condition to show edit button


    const [editFormData, setEditFormData] = useState({
        title: '',
        content: '',
        imageUrls: [''], // Assuming imageUrls is an array of strings
        hashtags: [''], // Assuming hashtags is an array of strings
    }); // Initialize state for edit form data







    const handleFollow = async () => {
        // Check if there's a logged-in user and an article writer to follow
        if (!accessToken || !user?.userId || user?.userId === articleDetail?.writer) {
            return; // Early return if no access token, user ID, or if the user is the writer
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${accessToken}` },
            };
            // Replace followId and followingId with appropriate IDs
            const response = await axios.post(`http://localhost:4040/api/v1/follows/${user.userId}/${articleDetail?.writer}`, {}, config);

            // Handle response
            if (response.data.checkFollowEnum === "following") {
                alert("Followed successfully.");
            } else if (response.data.checkFollowEnum === "unFollowing") {
                alert("Unfollowed successfully.");
            }
        } catch (error) {
            console.error('Error following/unfollowing:', error);
        }
    };
    const handleDelete = async () => {
        if (!accessToken || !articleId) {
            return; // Early return if no token or article ID
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${accessToken}` },
            };
            await axios.delete(`http://localhost:4040/api/v1/articles/${articleId}`, config);
            navigate('/articles'); // Navigate to the articles page after successful deletion
        } catch (error) {
            console.error('Error deleting article:', error);
        }
    };


    const handleEditInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, field: string) => {
        const value = e.target.value;
        if (field === 'hashtags' || field === 'imageUrls') {
            // Split the string by commas and remove whitespace from each element
            const arrayValue = value.split(',').map((item) => item.trim());
            setEditFormData({ ...editFormData, [field]: arrayValue });
        } else {
            setEditFormData({ ...editFormData, [field]: value });
        }
    };

    const initializeEditFormData = () => {
        if (articleDetail) {
            setEditFormData({
                title: articleDetail.title,
                content: articleDetail.content,
                imageUrls: articleDetail.imageUrls, // Assuming this is an array of strings
                hashtags: articleDetail.articleHashtags, // Assuming this is an array of strings
            });
        }
    };
    const handleEditFormSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!accessToken || !articleId) {
            return; // Early return if no token or article ID
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${accessToken}` },
            };
            await axios.patch(`http://localhost:4040/api/v1/articles/${articleId}`, editFormData, config);
            // Re-fetch article details to show the updated article
            const response = await axios.get<ArticleResponse>(`http://localhost:4040/api/v1/articles/${articleId}`);
            setArticleDetail(response.data);
            setIsEditing(false); // Hide the edit form after successful submission
        } catch (error) {
            console.error('Error updating article:', error);
        }
    };


    const handleFavoriteChange = async (actionType: 'INCREMENT' | 'DECREMENT') => {
        if (isUpdatingFavorite || !accessToken || !articleId) {
            return;// 이미 요청을 처리 중이거나 토큰이나 기사 ID가 없는 경우 조기 반환
        }

        setIsUpdatingFavorite(true); // 처리하는 동안 버튼을 비활성화합니다.


        try {
            const config = {
                headers: { Authorization: `Bearer ${accessToken}` },
            };
            const requestBody = {
                actionType: actionType,
            };
            await axios.put(`http://localhost:4040/api/v1/articles/${articleId}/favorite`, requestBody, config);
            // 성공하면 기사 세부정보를 다시 가져와서 업데이트된 즐겨찾기 개수를 가져옵니다.
            const response = await axios.get<ArticleResponse>(`http://localhost:4040/api/v1/articles/${articleId}`);
            setArticleDetail(response.data);
        } catch (error) {
            console.error(`Error ${actionType === 'INCREMENT' ? 'incrementing' : 'decrementing'} favorite count:`, error);
        }

        setIsUpdatingFavorite(false); /// 처리 후 버튼을 다시 활성화합니다.


    };







    useEffect(() => {
        const fetchArticleDetail = async () => {
            try {
                const response = await axios.get<ArticleResponse>(`http://localhost:4040/api/v1/articles/${articleId}`);
                setArticleDetail(response.data);
            } catch (error) {
                console.error('Error fetching article details:', error);
            }
        };

        fetchArticleDetail();
    }, [articleId]);



    // New useEffect for incrementing view count
    useEffect(() => {
        const incrementViewCount = async () => {
            try {
                if (accessToken) { // Check if the token exists
                    const config = {
                        headers: { Authorization: `Bearer ${accessToken}` }
                    };
                    await axios.get(`http://localhost:4040/api/v1/articles/${articleId}/increase-view`, config);
                }
            } catch (error) {
                console.error('Error incrementing view count:', error);
            }
        };

        if (articleId) {
            incrementViewCount();
        }
    }, [articleId, accessToken]); // Add tok
    if (!articleDetail) {
        return <div>Loading...</div>;
    }



    // Render the article details using the articleDetail object
    return (


        <div>
            <div>
                <div>
                    {/* 현재 사용자가 작성자가 아닌 경우 팔로우 버튼 표시 */}
                    {user?.userId !== articleDetail?.writer && (
                        <button onClick={handleFollow}>Follow</button>
                    )}
                </div>

                <div>
                    {showEditForm && (
                        <button onClick={handleDelete}>Delete Article</button>
                    )}
                </div>
                {showEditForm && !isEditing && (
                    <button onClick={() => {
                        setIsEditing(true);
                        initializeEditFormData(); // Initialize form data when entering edit mode
                    }}>Edit Article</button>
                )}                {isEditing && (
                    <form onSubmit={handleEditFormSubmit}>
                        <input type="text" value={editFormData.title} onChange={(e) => handleEditInputChange(e, 'title')} placeholder="Title" />
                        <textarea value={editFormData.content} onChange={(e) => handleEditInputChange(e, 'content')} placeholder="Content" />
                        <input type="text" value={editFormData.hashtags.join(", ")} onChange={(e) => handleEditInputChange(e, 'hashtags')} placeholder="Hashtags (comma separated)"/>
                        <input type="text" value={editFormData.imageUrls.join(", ")} onChange={(e) => handleEditInputChange(e, 'imageUrls')} placeholder="Image URLs (comma separated)"/>

                        <button type="submit">Update Article</button>
                        <button onClick={() => setIsEditing(false)}>Cancel</button>
                    </form>
                )}
            </div>
            <h1>{articleDetail.title}</h1>
            <p>{articleDetail.content}</p>
            <p>Writer: {articleDetail.writer}</p>
            <p>Favorite Count: {articleDetail.favoriteCount}</p>
            <p>View Count: {articleDetail.viewCount}</p>
            <ul>
                {articleDetail.articleHashtags.map((hashtag, index) => (
                    <li key={index}>{hashtag}</li>
                ))}
            </ul>
            <div>
                {articleDetail.imageUrls.map((imageUrl, index) => (
                    <img key={index} src={imageUrl} alt={`Image ${index + 1}`} />
                ))}
            </div>

            <div>
                <div>
                    <button onClick={() => handleFavoriteChange('INCREMENT')} disabled={isUpdatingFavorite}>Like</button>
                    <button onClick={() => handleFavoriteChange('DECREMENT')} disabled={isUpdatingFavorite}>Unlike</button>
                </div>
                <br/><br/><br/><br/><br/><br/>
                <Comments/>
            </div>
        </div>
    );
}

export default ArticleDetail;