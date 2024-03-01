import React, { useEffect, useState } from 'react';
import './style.css';
import {useCookies} from "react-cookie";
import {useNavigate, useParams} from "react-router-dom";
import {useUserStore} from "../../../stores/userStore";
import axiosInstance from "../../../api/axios";

interface Flashcard {
    term: string;
    definition: string;
}
interface FlashcardSetDetail {
    flashCardSetId: number;
    title: string;
    description: string;
    hashtags: string[];
    flashcards: Flashcard[];
}

function QuizEdit() {
    const [cookies] = useCookies(['accessToken']); // 'accessToken' 쿠키를 읽어옵니다.
    const { cardSetId } = useParams<{ cardSetId: string }>(); // Ensure the type matches the expected parameter

    const [title, setTitle] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const [hashtags, setHashtags] = useState<string[]>([]);
    const [tempHashtag, setTempHashtag] = useState<string>('');
    const [flashcards, setFlashcards] = useState<Flashcard[]>([]);
    const navigator = useNavigate();
    const { user, setUser } = useUserStore();

    const handleFlashcardChange = (index: number, key: keyof Flashcard, value: string) => {
        const newFlashcards = [...flashcards];
        newFlashcards[index][key] = value;
        setFlashcards(newFlashcards);
    };


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // 폼 제출의 기본 동작을 방지합니다.

        try {
            const response = await axiosInstance.put(`/api/v1/card-set/${cardSetId}`, {
                title,
                description,
                hashtags,
                flashcards,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${cookies.accessToken}`, // accessToken 쿠키 값을 사용하여 Authorization 헤더를 설정합니다.
                }
            });

            if (response.status === 200) {
                // 요청이 성공적으로 처리되었을 때
                alert('수정이 완료되었습니다.');
                navigator(`/user/${user?.userId}`)

            } else {
                // 서버에서 오류 응답을 반환했을 때
                alert('수정에 실패했습니다.');
            }
        } catch (error) {
            console.error('수정 요청 중 오류가 발생했습니다.', error);
            alert('수정 요청 중 오류가 발생했습니다.');
        }
    };
    useEffect(() => {
        const fetchCardSetDetail = async () => {
            try {
                const response = await axiosInstance.get(`/api/v1/card-set/detail/${cardSetId}`);
                const { title, description, hashtags, flashcards } = response.data.data;
                setTitle(title);
                setDescription(description);
                setHashtags(hashtags);
                setFlashcards(flashcards);
            } catch (error) {
                console.error('Error fetching card set detail:', error);
            }
        };

        fetchCardSetDetail();
    }, []);

    const addFlashcard = () => {
        setFlashcards([...flashcards, { term: '', definition: '' }]);
    };

    const handleHashtagChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setTempHashtag(e.target.value);
    };
    const addHashtag = () => {
        if (tempHashtag && !hashtags.includes(tempHashtag)) {
            setHashtags([...hashtags, tempHashtag]);
            setTempHashtag('');
        }
    };
    const removeHashtag = (index: number) => {
        setHashtags(hashtags.filter((_, i) => i !== index));
    };
    const removeFlashcard = (index: number) => {
        setFlashcards(flashcards.filter((_, i) => i !== index));
    };
    return (
        <div>
            <div>
                <form className="container mx-auto mt-5 p-5 border-2 border-gray-800" onSubmit={handleSubmit}>
                    <div className="mb-5">
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            placeholder="제목"
                            className="input text-lg p-3 mb-3 w-full border-2 border-gray-300"
                        />
                    </div>

                    <div className="mb-5">
                        <input
                            type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            placeholder="내용"
                            className="input text-lg p-3 w-full border-2 border-gray-300"
                        />
                    </div>
                    <div>
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
                                {hashtags.map((tag, index) => (
                                    <div key={index} className="inline-flex items-center mr-2">
                                        <span className="text-sm font-medium text-blue-500">#{tag}</span>

                                        <button
                                            type="button"
                                            onClick={() => removeHashtag(index)}
                                            className="ml-1 text-red-500 hover:text-red-700"
                                        >
                                            &times;
                                            Remove
                                        </button>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>


                    {flashcards.map((card, index) => (
                        <div key={index} className="flex items-center mb-3">
                            <input
                                type="text"
                                value={card.term}
                                onChange={(e) => handleFlashcardChange(index, 'term', e.target.value)}
                                className="flex-1 text-md p-2 mr-3 border-2 border-gray-300"
                                placeholder="문제의 질문을 입력하세요"
                            />
                            <input
                                type="text"
                                value={card.definition}
                                onChange={(e) => handleFlashcardChange(index, 'definition', e.target.value)}
                                placeholder="문제의 정답을 입력하세요"

                                className="flex-1 text-md p-2 border-2 border-gray-300"
                            />
                            <button
                                type="button"
                                onClick={() => removeFlashcard(index)}
                                className="ml-3 bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"

                            >
                                &times; Remove
                            </button>
                        </div>
                    ))}


                    <button type="button" onClick={addFlashcard} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">플래시카드 추가</button>
                    <div>
                        <button type="submit" className="mt-5 w-full  bg-primary hover:bg-primary-dark text-white font-bold py-3 px-6 rounded">퀴즈 세트 수정</button>
                    </div>
                </form>
            </div>



        </div>
    );
}

export default QuizEdit;