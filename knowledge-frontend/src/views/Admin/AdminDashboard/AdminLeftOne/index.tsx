import React, {ChangeEvent, useEffect, useState} from 'react';
import axios from 'axios';
import { useCookies } from 'react-cookie';


interface User {
    userId: string;
    email: string;
    role: string;
    type: string;
}

interface UsersResponse {
    users: {
        content: User[];
        pageable: {
            pageNumber: number;
            pageSize: number;
            sort: {
                empty: boolean;
                sorted: boolean;
                unsorted: boolean;
            };
            offset: number;
            paged: boolean;
            unpaged: boolean;
        };
        last: boolean;
        totalElements: number;
        totalPages: number;
        size: number;
        number: number;
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        };
        first: boolean;
        numberOfElements: number;
        empty: boolean;
    };
}

function AdminLeftOne() {
    const [users, setUsers] = useState<User[]>([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [searchType, setSearchType] = useState('userId');
    const [cookies] = useCookies(['accessToken']);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetchUsers();
    }, [currentPage]);

    const fetchUsers = async (searchParam: string = '') => {
        const queryParams = searchParam ? `${searchParam}&page=${currentPage}&size=10` : `?page=${currentPage}&size=10`;
        const fullURL = `http://localhost:4040/api/v1/admin/users${queryParams}`;

        try {
            const response = await axios.get<UsersResponse>(fullURL, {
                headers: {
                    Authorization: `Bearer ${cookies.accessToken}`,
                },
                withCredentials: true,
            });
            setUsers(response.data.users.content);
            setTotalPages(response.data.users.totalPages);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    };

    const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    };

    const handleSearchTypeChange = (event: ChangeEvent<HTMLSelectElement>) => {
        setSearchType(event.target.value);
    };

    const handleSearch = () => {
        setCurrentPage(0);
        const searchParam = searchTerm ? `?${searchType}=${encodeURIComponent(searchTerm)}` : '';
        fetchUsers(searchParam);
    };

    const goToPage = (pageNumber: number) => {
        setCurrentPage(pageNumber);
    };

    const pageNumbers = Array.from({length: totalPages}, (_, index) => index).slice(Math.max(currentPage - 2, 0), Math.min(currentPage + 3, totalPages));

    return (
        <section>
            <div>
                <select onChange={handleSearchTypeChange} value={searchType}>
                    <option value="userId">User ID</option>
                    <option value="email">Email</option>
                </select>
                <input type="text" value={searchTerm} onChange={handleSearchChange} />
                <button onClick={handleSearch}>Search</button>
            </div>
            <ul>
                {users.map(user => (
                    <li key={user.userId}>{`${user.userId} (${user.email}) - ${user.role}`}</li>
                ))}
            </ul>
            <div>
                <button onClick={() => goToPage(Math.max(currentPage - 1, 0))} disabled={currentPage === 0}>Previous</button>
                {pageNumbers.map(number => (
                    <button key={number} onClick={() => goToPage(number)} disabled={number === currentPage}>
                        {number + 1}
                    </button>
                ))}
                <button onClick={() => goToPage(Math.min(currentPage + 1, totalPages - 1))} disabled={currentPage + 1 >= totalPages}>Next</button>
            </div>
        </section>
    );
}

export default AdminLeftOne;