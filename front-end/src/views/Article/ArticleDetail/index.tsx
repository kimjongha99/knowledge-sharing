import './style.css';
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {useUserStore} from "../../../stores/userStore";
import {useCookies} from "react-cookie";


interface Article {
    id: number;
    title: string;
    content: string;
    writer: string;
    favoriteCount: number;
    viewCount: number;
    articleHashtags: string[];
    imageUrls: string[];
}

export default function ArticleDetail(){
    const { articleId } = useParams<{ articleId: string }>(); // Ensure the type matches the expected parameter
    const [article, setArticle] = useState<Article | null>(null);
    const [error, setError] = useState('');
    const { user } = useUserStore();
    const [cookies] = useCookies();
    const navigate = useNavigate(); // Initialize useNavigate hook

    const showEditForm = user?.userId === article?.writer; // Condition to show edit button

    const [isEditMode, setIsEditMode] = useState(false); // To toggle edit mode
    const [formData, setFormData] = useState({
        title: '',
        content: '',
        imageUrls: [''],
        hashtags: ['']
    });

    const handleEditToggle = () => setIsEditMode(!isEditMode);
    const handleChange = (e: { target: { name: string; value: string; }; }) => {
        // Update form fields for title, content, imageUrls, and hashtags
        if (e.target.name === 'imageUrls' || e.target.name === 'hashtags') {
            setFormData({ ...formData, [e.target.name]: e.target.value.split(',') });
        } else {
            setFormData({ ...formData, [e.target.name]: e.target.value });
        }
    };
    const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            try {
                // 이미지 파일 업로드 로직
                const imageUploadPromises = Array.from(e.target.files).map(file => uploadImage(file));
                const imageUrls = await Promise.all(imageUploadPromises);

                // 기존 이미지 URL 배열 대신 새로운 이미지 URL 배열로 설정
                // 여기서는 간단히 모든 새 이미지를 기사에 추가하도록 설정
                // 필요에 따라 기존 이미지를 유지하고 싶다면, 이 부분을 조정
                setFormData(prev => ({
                    ...prev,
                    imageUrls: imageUrls.map(url => url) // API 응답에 따라 조정
                }));
            } catch (error) {
                console.error('Error uploading files:', error);
            }
        }
    };

    const uploadImage = async (file: File) => {
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post('http://localhost:4040/api/files/upload/article', formData, {
                headers: {
                    'Authorization': `Bearer ${cookies.accessToken}`,
                },
            });
            return response.data; // URL 또는 객체가 포함된 응답을 가정
        } catch (error) {
            console.error('Error uploading image:', error);
            throw error;
        }
    };

    const handleUpdate = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        try {
            await axios.patch(`http://localhost:4040/api/v1/articles/${articleId}`, formData, {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            });
            navigate('/articles'); // Redirect to articles list on success
        } catch (error) {
            console.error('Error updating article:', error);
            // Handle error (e.g., display error message)
        }
    };
    const handleFollow = async () => {
        // Check if there's a logged-in user and an article writer to follow
        if (!cookies.accessToken || !user?.userId || user?.userId === article?.writer) {
            return; // Early return if no access token, user ID, or if the user is the writer
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            };
            // Replace followId and followingId with appropriate IDs
            const response = await axios.post(`http://localhost:4040/api/v1/follows/${user.userId}/${article?.writer}`, {}, config);

            // Correctly accessing the nested checkFollowEnum value
            const checkFollowEnum = response.data.data.checkFollowEnum;

            // Handle response based on the checkFollowEnum value
            if (checkFollowEnum === "following") {
                alert("팔로우 성공.");
            } else if (checkFollowEnum === "unFollowing") {
                alert("언팔로우 성공.");
            }
        } catch (error) {
            console.error('Error following/unfollowing:', error);
        }
    };
    const handleDelete = async () => {
        if (!cookies.accessToken || !articleId) {
            return; // Early return if no token or article ID
        }

        try {
            const config = {
                headers: { Authorization: `Bearer ${cookies.accessToken}` },
            };
            await axios.delete(`http://localhost:4040/api/v1/articles/${articleId}`, config,
        );
            navigate('/articles'); // Navigate to the articles page after successful deletion
        } catch (error) {
            console.error('Error deleting article:', error);
        }
    };

    useEffect(() => {
        const fetchArticle = async () => {
            // Fetch article logic...
            const response = await axios.get(`http://localhost:4040/api/v1/articles/${articleId}`);
            setArticle(response.data.data);
            // Populate form data with the fetched article
            setFormData({
                title: response.data.data.title,
                content: response.data.data.content,
                imageUrls: response.data.data.imageUrls,
                hashtags: response.data.data.articleHashtags,
            });
        };
        fetchArticle();
    }, [articleId]);

    if (error) return <p>Error: {error}</p>;
    if (!article) return <p>Loading...</p>;


    return(
        <div>
            <div className="max-w-2xl mx-auto p-6">
                <div className="bg-white shadow-lg rounded-lg p-6">
                    <div className="flex justify-between items-center mb-4">
                        {isEditMode ? (
                            <input
                                type="text"
                                name="title"
                                value={formData.title}
                                onChange={handleChange}
                                placeholder="Title"
                                className="text-2xl font-bold mb-2"
                            />
                        ) : (
                            <h1 className="text-2xl font-bold">{article.title}</h1>
                        )}
                        <div>
                            {showEditForm && !isEditMode && (
                                <button onClick={handleDelete} className="text-red-500 hover:text-red-700 mr-2 mb-3">Delete</button>
                            )}

                            {showEditForm && !isEditMode ? (
                                <button onClick={handleEditToggle} className="text-blue-500 hover:text-blue-700 mb-3">Edit</button>
                            ) : null}

                            {showEditForm && isEditMode ? (
                                <>
                                    <button onClick={() => setIsEditMode(false)} className="text-red-500 hover:text-red-700 mb-3 mr-2">Cancel</button>
                                    <button onClick={handleUpdate} className="text-green-500 hover:text-green-700 mb-3">Save</button>
                                </>
                            ) : null}



                            {user?.userId !== article?.writer && (
                                <button onClick={handleFollow} className="text-yellow-500 hover:text-yellow-700 mr-2 mb-3" >Follow</button>
                            )}

                        </div>
                    </div>

                    {isEditMode ? (
                        <form onSubmit={handleUpdate} className="space-y-4">
                    <textarea
                        name="content"
                        value={formData.content}
                        onChange={handleChange}
                        placeholder="Content"
                        rows={4}
                        className="w-full p-2"
                    ></textarea>

                            <input
                                type="text"
                                name="hashtags"
                                value={formData.hashtags.join(',')}
                                onChange={handleChange}
                                placeholder="Hashtags (comma-separated)"
                                className="w-full p-2"
                            />

                            <div className="mb-4">
                                <label className="block text-sm font-medium text-gray-700">Images</label>
                                <div className="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                                    <div className="space-y-1 text-center">
                                        <div className="flex text-sm text-gray-600">
                                            <label htmlFor="file-upload" className="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-500 focus-within:ring-opacity-50">
                                                <span>Upload a file</span>
                                                <input   type="file"
                                                         name="imageUrls"
                                                         onChange={handleImageChange}
                                                         multiple />
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>




                        </form>
                    ) : (
                        <>
                            <div className="mb-4">
                                <p className="text-gray-700">{article.content}</p>
                            </div>
                            <div className="flex justify-between items-center mb-4">
                                <span className="text-gray-500">작성자: {article.writer}</span>
                                <div className="flex items-center">
                                    <span className="text-gray-500 mr-2">{article.viewCount} Views</span>
                                    <span className="text-gray-500">{article.favoriteCount} Favorites</span>
                                </div>
                            </div>
                            <div className="flex flex-wrap gap-2 mb-4">
                                {article.articleHashtags.map((hashtag, index) => (
                                    <span key={index} className="bg-gray-200 text-gray-700 px-3 py-1 rounded-full">#{hashtag}</span>
                                ))}
                            </div>
                            <div className="grid grid-cols-2 gap-4">
                                {article.imageUrls.map((imageUrl, index) => (
                                    <img key={index} src={imageUrl} alt={`Article Image ${index + 1}`} className="w-full h-auto rounded-lg" />
                                ))}
                            </div>
                        </>
                    )}
                </div>
            </div>
        </div>
    )
}


