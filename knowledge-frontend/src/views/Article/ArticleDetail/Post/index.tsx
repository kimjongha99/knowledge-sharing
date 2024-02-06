import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {Cookies, useCookies} from "react-cookie";

function ArticlePost() {
    const [formData, setFormData] = useState({
        title: "",
        content: "",
        hashtags: [],
        imageUrls: [],
    });

    const [cookies] = useCookies();
    const navigate = useNavigate();

    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        const { name, value } = e.target;

        if (name === "hashtags" || name === "imageUrls") {
            // For lists (hashtags and imageUrls), split the value by commas and store as an array
            setFormData({
                ...formData,
                [name]: value.split(",").map((item: string) => item.trim()), // Split by commas and trim spaces
            });
        } else {
            setFormData({
                ...formData,
                [name]: value,
            });
        }
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();

        const accessToken = cookies.accessToken; // Retrieving the access token from cookies

        try {
            const response = await axios.post(
                "http://localhost:4040/api/v1/articles",
                formData,
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                }
            );

            if (response.status === 200) {
                navigate("/articles");
            }
        } catch (error) {
            console.error("Error posting data:", error);
        }
    };

    return (
        <div>
            <h2>Create a New Post</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Title:</label>
                    <input
                        type="text"
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Content:</label>
                    <textarea
                        name="content"
                        value={formData.content}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Hashtags (comma-separated):</label>
                    <input
                        type="text"
                        name="hashtags"
                        value={formData.hashtags.join(", ")} // Join array with commas for display
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Image URLs (comma-separated):</label>
                    <input
                        type="text"
                        name="imageUrls"
                        value={formData.imageUrls.join(", ")} // Join array with commas for display
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <button type="submit">Submit Post</button>
                </div>
            </form>
        </div>
    );
}

export default ArticlePost;