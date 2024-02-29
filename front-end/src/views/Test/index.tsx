import React, { useEffect, useState } from 'react';
import './style.css';


function Test() {



    return (
        <div>
            <div>
                <form  className="container mx-auto mt-5 p-5 border-2 border-gray-800">
                    <div className="mb-5">
                        <input
                            type="text"
                            placeholder="제목"

                            className="input text-lg p-3 mb-3 w-full border-2 border-gray-300"
                        />
                    </div>

                    <div className="mb-5">
                        <input

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

                                    placeholder="Add a hashtag"
                                    className="rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50 flex-grow"
                                />
                                <button
                                    type="button"

                                    className="ml-2 rounded-md bg-indigo-600 text-white px-3 py-1.5 text-sm shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50"
                                >
                                    Add
                                </button>
                            </div>


                            <div className="mt-2">
                                    <div  className="inline-flex items-center mr-2">
                                        <span className="text-sm font-medium text-blue-500">#sadasd</span>

                                        <button
                                            type="button"
                                            className="ml-1 text-red-500 hover:text-red-700"
                                        >
                                            &times;
                                            Remove
                                        </button>
                                    </div>
                            </div>
                        </div>
                    </div>


                        <div  className="flex items-center mb-3">
                            <input
                                type="text"
                                className="flex-1 text-md p-2 mr-3 border-2 border-gray-300"
                                placeholder="문제의 질문을 입력하세요"
                            />
                            <input
                                type="text"
                                placeholder="문제의 정답을 입력하세요"

                                className="flex-1 text-md p-2 border-2 border-gray-300"
                            />
                            <button
                                type="button"
                                className="ml-3 bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"

                            >
                                &times; Remove
                            </button>
                        </div>

                    <button type="button"  className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">플래시카드 추가</button>
                    <div>
                        <button type="submit" className="mt-5 w-full bg-primary hover:bg-primary-dark text-white font-bold py-3 px-6 rounded">퀴즈 세트 생성</button>
                    </div>
                </form>
            </div>



        </div>
    );
}

export default Test;