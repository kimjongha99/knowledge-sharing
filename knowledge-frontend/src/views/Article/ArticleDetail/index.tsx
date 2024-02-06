import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Comments from "./Comment";

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

    if (!articleDetail) {
        return <div>Loading...</div>;
    }

    // Render the article details using the articleDetail object
    return (


        <div>
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

                <br/><br/><br/><br/><br/><br/>
                <Comments/>
            </div>
        </div>
    );
}

export default ArticleDetail;