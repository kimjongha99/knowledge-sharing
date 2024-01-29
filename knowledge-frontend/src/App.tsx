import React from 'react';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import {AUTH_PATH, MAIN_PATH} from 'constant';
import Container from "./layouts/Container";
import Main from "./views/main";
import SignUp from "./views/SignUp";
import SignIn from "./views/SignIn";


function App() {
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
