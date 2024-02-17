import './style.css';
import {useCookies} from "react-cookie";
import {useEffect, useState} from "react";
import axios from "axios";
import {useUserStore} from "../../../stores/userStore";
interface FollowingUser {
    userId: string;
    email: string;
}
export default function Follow() {
    const [cookies] = useCookies(['accessToken']);
    const [followingList, setFollowingList] = useState<FollowingUser[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const {user, setUser} = useUserStore();

    const userId = user?.userId; // This needs to be dynamically set, e.g., from user state, params, or context

    useEffect(() => {
        const fetchFollowing = async () => {
            try {
                const response = await axios.get(`http://localhost:4040/api/v1/follows/${userId}/following?pageable=`, {
                    headers: {
                        Authorization: `Bearer ${cookies.accessToken}`,
                        'Accept': '*/*',
                    },
                });

                if (response.status === 200 && response.data.statusCode === 200) {
                    setFollowingList(response.data.data.content);
                } else {
                    setError('Failed to fetch following list');
                }
            } catch (err) {
                console.error('Error fetching following list:', err);
                setError('Error occurred while fetching following list');
            } finally {
                setLoading(false);
            }
        };

        fetchFollowing();
    }, [userId, cookies.accessToken]); // Re-fetch when userId or accessToken changes

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;
    return (
        <section id="main-right-three">
            <h2>Following</h2>
            {followingList.length > 0 ? (
                <ul>
                    {followingList.map((user) => (
                        <li key={user.userId}>
                            {user.email} ({user.userId})
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No following users found.</p>
            )}
        </section>
    );


}