import './style.css';
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../../components/Pagination";
interface FlashcardSet {
    flashCardSetId: number;
    title: string;
    description: string;
    hashtags: string[];
}
export default function Practice(){

    const [searchTerm, setSearchTerm] = useState<string>('');
    const [searchType, setSearchType] = useState<'title' | 'description'>('title');
    const [flashcardSets, setFlashcardSets] = useState<FlashcardSet[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(1); // 현재 페이지 상태
    const [totalPages, setTotalPages] = useState<number>(0); // 총 페이지 수 상태

    const handleSearch = async (page: number = currentPage) => {
        const params = new URLSearchParams({
            page: String(page - 1), // 페이지 인덱스는 0부터 시작하므로 1을 빼줍니다.
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
    }, [currentPage]); // currentPage가 변경될 때마다 handleSearch를 호출합니다.

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    return(
        <div>
            <div className="search-container">
                <div className="toggle-buttons">
                    <button
                        onClick={() => setSearchType('title')}
                        className={searchType === 'title' ? 'toggle-button active' : 'toggle-button'}
                    >
                        Title
                    </button>
                    <button
                        onClick={() => setSearchType('description')}
                        className={searchType === 'description' ? 'toggle-button active' : 'toggle-button'}
                    >
                        Description
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

            {flashcardSets.map((set) => (
                <div key={set.flashCardSetId} className="relative bg-blue-800 text-white rounded-lg shadow-lg overflow-hidden" style={{width: '400px', height: '200px'}}>
                    <div className="p-6">
                        <h2 className="text-lg font-semibold">{set.title}</h2>
                        <p className="text-sm opacity-75">{set.description}</p>
                        <div className="flex items-center mt-4">
                            <div>
                                <p className="text-sm">{/* Username placeholder */}</p>
                                <p className="text-xs opacity-75">{/* Role placeholder */}</p>
                            </div>
                        </div>
                        <div className="hashtag text-xs opacity-75">
                            {set.hashtags.join(' ')}
                        </div>
                    </div>
                </div>
            ))}
            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />
        </div>
    )
}


