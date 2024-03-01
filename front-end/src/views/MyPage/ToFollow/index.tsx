import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import axios from 'axios';
import { useUserStore } from '../../../stores/userStore';
import './style.css';

interface FollowingUser {
    userId: string;
    email: string;
}

export default function ToFollow() {

    const [cookies] = useCookies(['accessToken']);
    const { user } = useUserStore();
    const [followingList, setFollowingList] = useState<FollowingUser[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [currentPage, setCurrentPage] = useState(0); // Current page starts from 0
    const [totalPages, setTotalPages] = useState(0); // Total number of pages

    useEffect(() => {
        const fetchFollowing = async () => {
            if (!user?.userId) return;
            setLoading(true);
            try {
                const response = await axios.get(`http://localhost:4040/api/v1/follows/${user.userId}/followers?page=${currentPage}&size=3`, {
                    headers: {
                        Authorization: `Bearer ${cookies.accessToken}`,
                        'Accept': '*/*',
                    },
                });

                if (response.status === 200) {
                    setFollowingList(response.data.data.content);
                    setTotalPages(response.data.data.totalPages);
                } else {
                    setError('Failed to fetch following list.');
                }
            } catch (err) {
                console.error('Error fetching following list:', err);
                setError('Error occurred while fetching following list.');
            } finally {
                setLoading(false);
            }
        };

        fetchFollowing();
    }, [user?.userId, cookies.accessToken, currentPage]);

    const handlePageChange = (pageNumber: number) => {
        setCurrentPage(pageNumber);
    };

    const renderPageNumbers = () => {
        const pages = [];
        for (let i = 0; i < totalPages; i++) {
            pages.push(
                <button
                    className={`text-xs px-2 py-1 border border-gray-300 rounded ${i === currentPage ? 'bg-blue-500 text-white' : 'bg-white hover:bg-gray-100'}`}
                    key={i}
                    onClick={() => handlePageChange(i)}
                    disabled={i === currentPage}
                >
                    {i + 1}
                </button>
            );
        }
        return pages;
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <section id="main-right-three">
            <h2 className="flex justify-center">나를 팔로잉하는 사람들</h2>
            {followingList.length > 0 ? (
                <ul className="following-list">
                    {followingList.map((user) => (
                        <li key={user.userId}>
                            {user.email} ({user.userId})
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No following users found.</p>
            )}
            <div className="flex justify-center space-x-2 mt-4">
                <button
                    className={`px-3 py-1.5 text-sm font-medium rounded-md ${currentPage === 0 ? 'bg-gray-300 text-gray-500 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-600 text-white'}`}
                    onClick={() => handlePageChange(Math.max(currentPage - 1, 0))}
                    disabled={currentPage === 0}
                >
                    Previous
                </button>
                {renderPageNumbers()}
                <button
                    className={`px-3 py-1.5 text-sm font-medium rounded-md ${currentPage + 1 >= totalPages ? 'bg-gray-300 text-gray-500 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-600 text-white'}`}
                    onClick={() => handlePageChange(Math.min(currentPage + 1, totalPages - 1))}
                    disabled={currentPage + 1 >= totalPages}
                >
                    Next
                </button>
            </div>
        </section>
    );
}