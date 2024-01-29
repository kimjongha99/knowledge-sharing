import React, {useEffect} from 'react';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import {AUTH_PATH, MAIN_PATH} from 'constant';
import Container from "./layouts/Container";
import Main from "./views/main";
import SignUp from "./views/SignUp";
import SignIn from "./views/SignIn";
import {User, useUserStore} from "./stores/user.store";
import {useCookies} from "react-cookie";
import axios from "axios";


function App() {

    const {user, setUser} = useUserStore();
    const [cookies, setCookie] = useCookies();


    // Fetch user info when the app component is mounted or accessToken changes
    useEffect(() => {
        // If there's no accessToken, don't attempt the request
        if (!cookies.accessToken) {
            console.log("No access token present.");
            return;
        }

        axios.get('http://localhost:4040/api/v1/user', {
            headers: {
                'Authorization': `Bearer ${cookies.accessToken}`
            }
        })
            .then(response => {
                const { userId, email, profileImageUrl } = response.data;
                setUser({ userId, email, profileImageUrl }); // Update the user state in Zustand store
            })
            .catch(error => {
                console.error("Error fetching user data: ", error);
                // Handle error, e.g. reset user state or show error message
            });
    }, [cookies.accessToken, setUser]); // Add setUser to dependency array to avoid exhaustive-deps warning


    return (
        <Routes>
            <Route element={<Container />}>
                <Route path={MAIN_PATH} element={<Main />} />
                <Route path={AUTH_PATH}>
                    <Route path='sign-up' element={<SignUp/>} />
                    <Route path='sign-in' element={<SignIn/>} />
                </Route>

                </Route>

        </Routes>
    );
}

export default App;
