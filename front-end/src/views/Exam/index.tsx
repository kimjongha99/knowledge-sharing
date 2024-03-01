import './style.css';
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../../components/Pagination";
import {Link, useNavigate} from "react-router-dom";
import {useCookies} from "react-cookie";


interface FlashcardSet {
    flashCardSetId: number;
    title: string;
    description: string;
    hashtags: string[];
    color?: string; // 색상 상태를 추가합니다.

}

 export default  function Exam (){

     const [searchTerm, setSearchTerm] = useState<string>('');
     const [searchType, setSearchType] = useState<'title' | 'description'>('title');
     const [flashcardSets, setFlashcardSets] = useState<FlashcardSet[]>([]);
     const [currentPage, setCurrentPage] = useState<number>(0); // 현재 페이지 상태
     const [totalPages, setTotalPages] = useState<number>(0); // 총 페이지 수 상태
     const [cookies] = useCookies(['accessToken']);
     const navigator = useNavigate();


     const isLoggedIn = cookies.accessToken;


     const handleArticlePostPage  = () => {
         navigator('/quiz/post');
     };




     const handleSearch = async (page: number = currentPage) => {
         const params = new URLSearchParams({
             page: String(page ), // 페이지 인덱스는 0부터 시작하므로 1을 빼줍니다.
             size: '9',
         });

         if (searchTerm.trim() !== '') {
             params.append(searchType, searchTerm);
         }

         try {
             const response = await axios.get('http://localhost:4040/api/v1/card-set', { params });
             setFlashcardSets(response.data.data.flashcardSets);
             setTotalPages(response.data.data.totalPages); // 총 페이지 수를 설정합니다.
             console.log(response.data);
         } catch (error) {
             if (axios.isAxiosError(error)) {
                 console.error('Error fetching data: ', error.message);
             } else {
                 console.error('Unexpected error: ', error);
             }
         }
     };

     useEffect(() => {
         handleSearch();
         setFlashcardSets(currentSets => currentSets.map(set => ({
             ...set,
             color: set.color // 이미 색상이 있으면 그대로 사용하고, 없으면 새로운 색상을 생성합니다.
         })));

     }, [currentPage]); // currentPage가 변경될 때마다 handleSearch를 호출합니다.

     const handlePageChange = (page: number) => {
         setCurrentPage(page);
     };

     return(
         <div>
             {isLoggedIn &&(
                 <div id="post-button">
                     <button onClick={handleArticlePostPage}>퀴즈 작성하기</button>
                 </div>
             )}
             <div className="right-fixed-container">
                 <div className="search-container">

                     <div className="toggle-buttons">
                         <button
                             onClick={() => setSearchType('title')}
                             className={searchType === 'title' ? 'toggle-button active' : 'toggle-button'}
                         >
                             제목
                         </button>
                         <button
                             onClick={() => setSearchType('description')}
                             className={searchType === 'description' ? 'toggle-button active' : 'toggle-button'}
                         >
                             내용
                         </button>
                     </div>
                     <input
                         type="text"
                         value={searchTerm}
                         onChange={(e) => setSearchTerm(e.target.value)}
                         placeholder={`Enter ${searchType}`}
                         className="search-input"
                     />
                     <button onClick={() => handleSearch()} className="search-button">검색</button>
                 </div>
             </div>
             <div className="grid-container">

                 {flashcardSets.map((set) => (
                     <Link to={`/quiz/${set.flashCardSetId}`} key={set.flashCardSetId}  id='grid-item'  style={{width: '400px', height: '200px'}}>

                         <div className="p-6">

                             <h2 className="text-lg font-semibold">{set.title}</h2>
                             <p className="text-lg opacity-75">{set.description}</p>
                             <div className="flex items-center mt-4">
                             </div>
                             <div >
                                 #{set.hashtags.join('#'+' ')}
                             </div>
                         </div>
                     </Link>

                 ))}
             </div>

             <Pagination
                 currentPage={currentPage}
                 totalPages={totalPages}
                 onPageChange={handlePageChange}
             />
         </div>
     )
 }


