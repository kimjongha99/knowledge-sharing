import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import Main from "./views/Main";
import Container from "./layouts/Container";

function App() {
  return (

      <Routes>
          <Route path="/" element={<Container />}>
          <Route index element={<Main/>}/>



      </Route>
      </Routes>


  );
}

export default App;
