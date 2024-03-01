import './style.css';
import {useEffect, useState} from "react";
import Pagination from "../../../components/Pagination";
import {Link} from "react-router-dom";
import axiosInstance from "../../../api/axios";


interface Article {
    id: number;
    title: string;
    content: string;
}

interface FlashcardSet {
    id: number;
    title: string;
    content: string;
}

interface ApiResponse {
    statusCode: number;
    data: {
        articles: Article[];
        flashcardSets: FlashcardSet[];
        page: number;
        size: number;
        totalElements: number;
    };
}

function SearchHashtag() {
    const [data, setData] = useState<{ articles: Article[]; flashcardSets: FlashcardSet[] }>({
        articles: [],
        flashcardSets: [],
    });
    const [tagName, setTagName] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    // Initialize totalPages state
    const [totalPages, setTotalPages] = useState(0);


    const fetchData = async (searchTag: string, page: number) => {
        try {
            const response = await axiosInstance.get<ApiResponse>(`/api/v1/hashtag/${searchTag}?page=${page}&size=2`);
            setData(response.data.data);
            // Calculate total pages using totalElements and size
            const totalPagesCalc = Math.ceil(response.data.data.totalElements / response.data.data.size);
            setTotalPages(totalPagesCalc);
        } catch (error) {
            console.error('Failed to fetch data:', error);
        }
    };

    // 검색 버튼 클릭 또는 페이지 변경 시 fetchData 호출
    useEffect(() => {
        if (tagName) {
            fetchData(tagName, currentPage);
        }
    }, [currentPage]);


    const handleSearch = () => {
        setCurrentPage(0); // 검색 시 항상 첫 페이지부터 시작
        fetchData(tagName, 0);
    };

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };


    return (
      <div>

          <div className="container mx-auto p-4">

              <div>
                  <h1 className="text-3xl font-bold text-center text-blue-500 my-8">
                      해시태그를 검색하여 내용을 찾으세요
                  </h1>
              </div>
              <div className="mb-8 flex">
                  <input
                      className="w-full p-4 border-2 border-r-0 border-gray-300 rounded-l-lg focus:border-blue-500 shadow-sm transition duration-300 ease-in-out focus:outline-none focus:shadow-outline"
                      placeholder="Search..."
                      value={tagName}
                      onChange={(e) => setTagName(e.target.value)}
                  />
                  <button
                      className="bg-blue-500 text-white p-4 rounded-r-lg border-2 border-blue-500 hover:bg-blue-600 focus:outline-none focus:shadow-outline transition duration-300 ease-in-out shadow-sm"
                      onClick={handleSearch}
                  >
                      검색
                  </button>
              {/* Render articles and flashcardSets here */}

              </div>

              <div className="flex flex-wrap -mx-2">
                  <div className="w-full md:w-1/2 px-2 mb-4">
                      <div className="border rounded shadow p-4">
                          <h2 className="text-lg font-bold mb-2">Articles</h2>
                          <div className="border-t pt-2">



                              {data.articles.map((article) => (
                                  <div key={article.id} className="mb-2">
                                      <span className="text-sm font-semibold">title: {article.title}</span>
                                      <p className="text-md mb-3">content: {article.content}</p>


                                      <Link to={`/articles/${article.id}`} className="inline-block bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition-colors duration-150 ease-in-out">
                                          게시글 바로보기
                                      </Link>
                                </div>
                              ))}



                          </div>
                      </div>
                  </div>

                  <div className="w-full md:w-1/2 px-2 mb-4">
                      <div className="border rounded shadow p-4">
                          <h2 className="text-lg font-bold mb-2">Flashcard Sets</h2>
                          <div className="border-t pt-2">


                              {data.flashcardSets.map((flashcardSet) => (
                                  <div key={flashcardSet.id} className="mb-2">
                                      <span className="text-sm font-semibold">title: {flashcardSet.title}</span>
                                      <p className="text-md mb-3">content: {flashcardSet.content}</p>
                                      <Link to={`/card-set/${flashcardSet.id}`} className="inline-block bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition-colors duration-150 ease-in-out">
                                          바로 학습하기
                                      </Link>
                                      <Link to={`/quiz/${flashcardSet.id}`} className="ml-3 inline-block bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition-colors duration-150 ease-in-out">
                                          바로 시험보기
                                      </Link>
                                  </div>
                              ))}


                          </div>
                      </div>

                  </div>
              </div>
              <Pagination
                  currentPage={currentPage}
                  totalPages={totalPages}
                  onPageChange={handlePageChange}
              />
          </div>
      </div>
    );
}

export default SearchHashtag ;