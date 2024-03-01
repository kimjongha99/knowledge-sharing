import React, { useState } from 'react';
import { useCookies } from 'react-cookie';
import {useNavigate} from "react-router-dom";
import axiosInstance from "../../../api/axios";

interface ArticleForm {
    title: string;
    content: string;
    hashtags: string[];
    imageUrls: string[];
}

const ArticlePost: React.FC = () => {
    const [cookies] = useCookies(['accessToken']);
    const [articleForm, setArticleForm] = useState<ArticleForm>({
        title: '',
        content: '',
        hashtags: [],
        imageUrls: [],
    });
    const [submitStatus, setSubmitStatus] = useState<string>('');
    const [tempHashtag, setTempHashtag] = useState<string>('');
    const navigate = useNavigate();

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setArticleForm({ ...articleForm, [e.target.name]: e.target.value });
    };

    const handleHashtagChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setTempHashtag(e.target.value);
    };
    const addHashtag = () => {
        if (articleForm.hashtags.length < 3 && tempHashtag && !articleForm.hashtags.includes(tempHashtag)) {
            setArticleForm({ ...articleForm, hashtags: [...articleForm.hashtags, tempHashtag] });
            setTempHashtag('');
        }
    };
    const removeHashtag = (index: number) => {
        setArticleForm(prevState => ({
            ...prevState,
            hashtags: prevState.hashtags.filter((_, i) => i !== index),
        }));
    };
    const uploadImage = async (file: File) => {
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axiosInstance.post('/api/files/upload/article', formData, {
                headers: {
                    'Authorization': `Bearer ${cookies.accessToken}`,
                },
            });
            return response.data; // Assuming this is the URL or an object containing the URL
        } catch (error) {
            console.error('Error uploading image:', error);
            throw error;
        }
    };

    const handleImageChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            try {
                const imageUploadPromises = Array.from(e.target.files).map(file => uploadImage(file));
                const imageUrls = await Promise.all(imageUploadPromises);
                setArticleForm(prev => ({ ...prev, imageUrls: prev.imageUrls.concat(imageUrls.map(url => url)) })); // Adjust based on your API response
            } catch (error) {
                console.error('Error uploading files:', error);
            }
        }
    };
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await axiosInstance.post('/api/v1/articles', articleForm, {
                headers: {
                    'Authorization': `Bearer ${cookies.accessToken}`,
                    'Content-Type': 'application/json',
                },
            });
            navigate('/articles'); // Redirect to articles page on success
        } catch (error) {
            console.error('Error posting article:', error);
            setSubmitStatus('Failed to post the article');
        }
    };
    return (
        <div className="mt-20 max-w-lg mx-auto">
            <form onSubmit={handleSubmit}>

                <div className="mb-4">
                    <label htmlFor="title" className="block text-sm font-medium text-gray-700">Title</label>
                    <input type="text"
                           name="title"
                           value={articleForm.title}
                           onChange={handleInputChange}
                           placeholder="Title" className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"  />
                </div>


                <div className="mb-4">
                    <label htmlFor="content" className="block text-sm font-medium text-gray-700">Content</label>
                    <textarea name="content"
                              value={articleForm.content}
                              onChange={handleInputChange}
                              placeholder="Content" rows={4} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50" defaultValue={""} />
                </div>



                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700">Hashtags</label>
                    <div className="hashtag-input-container mt-1 flex">
                        <input
                            type="text"
                            value={tempHashtag}
                            onChange={handleHashtagChange}
                            placeholder="Add a hashtag"
                            className="rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50 flex-grow"
                        />
                        <button
                            type="button"
                            onClick={addHashtag}
                            className="ml-2 rounded-md bg-indigo-600 text-white px-3 py-1.5 text-sm shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50"
                        >
                            Add
                        </button>
                    </div>
                    <div className="mt-2">
                        {articleForm.hashtags.map((tag, index) => (
                            <div key={index} className="inline-flex items-center mr-2">
                                <span className="text-sm font-medium text-blue-500">#{tag}</span>
                                <button
                                    type="button"
                                    onClick={() => removeHashtag(index)}
                                    className="ml-1 text-red-500 hover:text-red-700"
                                    aria-label="Remove hashtag"
                                >
                                    &times;
                                </button>
                            </div>
                        ))}
                    </div>
                </div>


                <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700">Images</label>
                    <div className="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                        <div className="space-y-1 text-center">
                            <svg className="mx-auto h-12 w-12 text-gray-400" stroke="currentColor" fill="none" viewBox="0 0 48 48" aria-hidden="true">
                                <path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12m32-32H12m32 0a4 4 0 014 4v8m0-12V8a4 4 0 00-4-4h-4m4 32h4a4 4 0 004-4v-4m0 0H28" strokeWidth={2} strokeLinecap="round" strokeLinejoin="round" />
                            </svg>
                            <div className="flex text-sm text-gray-600">
                                <label htmlFor="file-upload" className="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-500 focus-within:ring-opacity-50">
                                    <span>Upload a file</span>
                                    <input   type="file"
                                             name="image"
                                             onChange={handleImageChange}
                                             multiple />
                                </label>
                                <p className="pl-1">or drag and drop</p>
                            </div>
                            <p className="text-xs text-gray-500">PNG, JPG, GIF up to 10MB</p>
                        </div>
                    </div>
                </div>



                <button type="submit"
                        className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 ml-4">Submit</button>
            </form>
            {submitStatus && <p>{submitStatus}</p>}
        </div>
    );
};

export default ArticlePost;
