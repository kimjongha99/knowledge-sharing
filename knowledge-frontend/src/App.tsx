import React from 'react';
import './App.css';
import { Route, Routes, useLocation } from 'react-router-dom';
import Index from "./views/main"
import {AUTH_PATH, MAIN_PATH} from 'constant';
import Container from "./layouts/Container";
import Main from "./views/main";
import Authentication from "./views/Authentication";


function App() {
    return (
        <Routes>
            <Route element={<Container />}>
                <Route path={MAIN_PATH} element={<Main />} />
                <Route path={AUTH_PATH} element={<Authentication />} />

            </Route>

        </Routes>
    );
}

export default App;
